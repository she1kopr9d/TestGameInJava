package org.shelldev.engine.world.systems;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.shelldev.engine.GameConfig;
import org.shelldev.engine.graphics.Shader;
import org.shelldev.engine.math.Vector2f;
import org.shelldev.engine.world.Entity;
import org.shelldev.engine.world.System;
import org.shelldev.engine.world.SystemManager;
import org.shelldev.engine.world.World;
import org.shelldev.engine.world.components.Position;
import org.shelldev.engine.world.components.Sprite;

public class SpriteSystem extends System<Sprite> {

    public static final SpriteSystem Instance = new SpriteSystem();

    static {
        SystemManager.registerSystem(SpriteSystem.Instance);
    }

    private int vao;
    private int vertexCount;

    private int cachedProgram = -1;
    private int uHasTexture, uTexture, uOffset, uSize, uColor, uScreenSize, uTime;

    @Override
    public void init() {
        initQuad();

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glDisable(GL11.GL_DEPTH_TEST);
    }

    private void initQuad() {
        float[] vertices = {
            0f, 0f,
            1f, 0f,
            1f, 1f,
            0f, 0f,
            1f, 1f,
            0f, 1f
        };

        vertexCount = vertices.length / 2;

        vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);

        int vbo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);

        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length);
        buffer.put(vertices).flip();

        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, 0, 0);
        GL20.glEnableVertexAttribArray(0);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);
    }

    private void cacheUniforms(Shader shader) {
        int program = shader.getProgramId();

        uHasTexture = GL20.glGetUniformLocation(program, "hasTexture");
        uTexture = GL20.glGetUniformLocation(program, "uTexture");
        uOffset = GL20.glGetUniformLocation(program, "offset");
        uSize = GL20.glGetUniformLocation(program, "size");
        uColor = GL20.glGetUniformLocation(program, "color");
        uScreenSize = GL20.glGetUniformLocation(program, "screenSize");
        uTime = GL20.glGetUniformLocation(program, "time");
    }

    private static class RenderEntry {
        Entity entity;
        Vector2f absPos;

        RenderEntry(Entity e, Vector2f p) {
            entity = e;
            absPos = p;
        }
    }

    /**
     * Рекурсивно собираем все спрайты с учетом anchor.
     * parentAbsPos — абсолютная позиция родителя.
     */
    private void collect(Entity entity, Vector2f parentAbsPos, ArrayList<RenderEntry> list) {
        Position position = entity.getComponent(Position.class);
        Sprite sprite = entity.getComponent(Sprite.class);
        if (position == null || sprite == null) return;

        // Абсолютная позиция спрайта = позиция родителя + локальная позиция дочернего
        Vector2f absPos = parentAbsPos.add(position.getPosition());

        // Отнимаем anchor текущего спрайта
        absPos = absPos.sub(new Vector2f(
            sprite.getSize().x() * sprite.getAnchor().x(),
            sprite.getSize().y() * sprite.getAnchor().y()
        ));

        list.add(new RenderEntry(entity, absPos));

        // Рекурсивно для детей
        for (Entity child : entity.getChilds()) {
            collect(child, absPos.add(new Vector2f(sprite.getSize().x() * sprite.getAnchor().x(),
                                                   sprite.getSize().y() * sprite.getAnchor().y())), list);
        }
    }

    private void render(RenderEntry entry, float time) {
        Entity entity = entry.entity;
        Sprite sprite = entity.getComponent(Sprite.class);

        Shader shader = sprite.getShader();
        shader.bind();

        int programId = shader.getProgramId();
        if (cachedProgram != programId) {
            cacheUniforms(shader);
            cachedProgram = programId;
        }

        boolean hasTexture = sprite.getTexture() != null;
        GL20.glUniform1i(uHasTexture, hasTexture ? 1 : 0);

        if (hasTexture) {
            sprite.getTexture().bind(0);
            GL20.glUniform1i(uTexture, 0);
        }

        Vector2f camOffset = CameraSystem.Instance.getCameraOffset();
        Vector2f screenCenter = new Vector2f(
                GameConfig.getInstance().getScreenSize().x() / 2f,
                GameConfig.getInstance().getScreenSize().y() / 2f
        );

        Vector2f finalPos = entry.absPos.sub(camOffset).add(screenCenter);

        GL20.glUniform2f(uOffset, finalPos.x(), finalPos.y());
        GL20.glUniform2f(uSize, sprite.getSize().x(), sprite.getSize().y());

        GL20.glUniform4f(
                uColor,
                sprite.getColor().R(),
                sprite.getColor().G(),
                sprite.getColor().B(),
                sprite.getColor().A()
        );

        GL20.glUniform2f(
                uScreenSize,
                GameConfig.getInstance().getScreenSize().x(),
                GameConfig.getInstance().getScreenSize().y()
        );

        GL20.glUniform1f(uTime, time);

        GL30.glBindVertexArray(vao);
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertexCount);
        GL30.glBindVertexArray(0);

        shader.unbind();
    }

    @Override
    public void update() {
        ArrayList<RenderEntry> entries = new ArrayList<>();
        for (Entity e : World.Instance.getChilds()) {
            collect(e, new Vector2f(0, 0), entries);
        }

        entries.sort((a, b) -> {
            Sprite sa = a.entity.getComponent(Sprite.class);
            Sprite sb = b.entity.getComponent(Sprite.class);
            return Integer.compare(sa.getLayer(), sb.getLayer());
        });

        float time = TimeSystem.Instance.getTime();

        for (RenderEntry entry : entries) {
            render(entry, time);
        }
    }
}
package org.shelldev.engine.graphics;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.shelldev.engine.math.Vector2f;
import org.shelldev.engine.utils.Color;

public class Render {

    private static Render instance;
    public static Render getInstance() {
        if (instance == null) instance = new Render();
        return instance;
    }

    private Render(){
        // Включаем blending для прозрачности
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    // ----------------- VAO для спрайтов -----------------
    private int spriteVao, spriteVertexCount;
    private boolean spriteInitialized = false;

    private void initSpriteQuad(){
        if(spriteInitialized) return;

        float[] vertices = {0f,0f, 1f,0f, 1f,1f, 0f,1f};
        spriteVertexCount = vertices.length / 2;

        spriteVao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(spriteVao);

        int vbo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);

        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length);
        buffer.put(vertices).flip();
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);

        GL20.glVertexAttribPointer(0,2,GL11.GL_FLOAT,false,0,0);
        GL20.glEnableVertexAttribArray(0);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,0);
        GL30.glBindVertexArray(0);

        spriteInitialized = true;
    }

    // ----------------- Очереди задач -----------------
    private static class SpriteTask {
        Shader shader;
        Texture texture;
        Vector2f pos, size, anchor;
        Color color;

        SpriteTask(Shader s, Texture t, Vector2f p, Vector2f sz, Vector2f a, Color c){
            shader = s; texture = t; pos = p; size = sz; anchor = a; color = c;
        }
    }
    private final List<SpriteTask> spriteTasks = new ArrayList<>();

    private static class LineTask {
        Shader shader;
        Vector2f a, b;
        Color color;

        LineTask(Shader s, Vector2f start, Vector2f end, Color c){
            shader = s; a = start; b = end; color = c;
        }
    }
    private final List<LineTask> lineTasks = new ArrayList<>();

    // ----------------- Методы добавления в очередь -----------------
    public void drawSprite(Shader shader, Texture texture, Vector2f pos, Vector2f size,
                           Vector2f anchor, Color color){
        spriteTasks.add(new SpriteTask(shader, texture, pos, size, anchor, color));
    }

    public void drawLine(Shader shader, Vector2f start, Vector2f end, Color color){
        lineTasks.add(new LineTask(shader, start, end, color));
    }

    // ----------------- Метод рендера всех очередей -----------------
    public void render(Vector2f camera, Vector2f screenSize, float time){
        // Спрайты
        for(SpriteTask task : spriteTasks){
            renderSprite(task.shader, task.texture, task.pos, task.anchor, camera, screenSize, task.size, task.color, time);
        }
        spriteTasks.clear();

        // Линии
        for(LineTask line : lineTasks){
            renderLine(line.shader, line.a, line.b, line.color, camera, screenSize);
        }
        lineTasks.clear();
    }

    // ----------------- Рендер спрайта -----------------
    private void renderSprite(Shader shader, Texture texture, Vector2f pos, Vector2f anchor,
                              Vector2f camera, Vector2f screen, Vector2f size, Color color, float time){
        initSpriteQuad();

        GL20.glUseProgram(shader.getProgramId());

        // Uniform'ы
        GL20.glUniform2f(GL20.glGetUniformLocation(shader.getProgramId(), "uObjectPosition"), pos.x(), pos.y());
        GL20.glUniform2f(GL20.glGetUniformLocation(shader.getProgramId(), "uAnchor"), anchor.x(), anchor.y());
        GL20.glUniform2f(GL20.glGetUniformLocation(shader.getProgramId(), "uCameraPosition"), camera.x(), camera.y());
        GL20.glUniform2f(GL20.glGetUniformLocation(shader.getProgramId(), "uScreenSize"), screen.x(), screen.y());
        GL20.glUniform2f(GL20.glGetUniformLocation(shader.getProgramId(), "uSpriteSize"), size.x(), size.y());
        GL20.glUniform1f(GL20.glGetUniformLocation(shader.getProgramId(), "uTime"), time);
        GL20.glUniform4f(GL20.glGetUniformLocation(shader.getProgramId(), "uColor"),
                color.R(), color.G(), color.B(), color.A());

        // Текстура
        if(texture != null){
            GL20.glActiveTexture(GL20.GL_TEXTURE0);
            texture.bind(0);
            GL20.glUniform1i(GL20.glGetUniformLocation(shader.getProgramId(), "uTexture"), 0);
            GL20.glUniform1i(GL20.glGetUniformLocation(shader.getProgramId(), "hasTexture"), 1);
        } else {
            GL20.glUniform1i(GL20.glGetUniformLocation(shader.getProgramId(), "hasTexture"), 0);
        }

        // Рисуем квадрат
        GL30.glBindVertexArray(spriteVao);
        GL11.glDrawArrays(GL11.GL_TRIANGLE_FAN, 0, spriteVertexCount);
        GL30.glBindVertexArray(0);

        GL20.glUseProgram(0);
    }

    // ----------------- Рендер линии -----------------
    private void renderLine(Shader shader, Vector2f start, Vector2f end, Color color,
                            Vector2f camera, Vector2f screen){
        float[] vertices = {start.x(), start.y(), end.x(), end.y()};
        int vao = GL30.glGenVertexArrays();
        int vbo = GL15.glGenBuffers();

        GL30.glBindVertexArray(vao);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);

        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length);
        buffer.put(vertices).flip();
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_DYNAMIC_DRAW);

        GL20.glEnableVertexAttribArray(0);
        GL20.glVertexAttribPointer(0,2,GL11.GL_FLOAT,false,0,0);

        GL20.glUseProgram(shader.getProgramId());

        GL20.glUniform4f(GL20.glGetUniformLocation(shader.getProgramId(), "uColor"),
                color.R(), color.G(), color.B(), color.A());
        GL20.glUniform2f(GL20.glGetUniformLocation(shader.getProgramId(), "uCameraPosition"),
                camera.x(), camera.y());
        GL20.glUniform2f(GL20.glGetUniformLocation(shader.getProgramId(), "uScreenSize"),
                screen.x(), screen.y());

        GL11.glDrawArrays(GL11.GL_LINES, 0, 2);

        GL30.glBindVertexArray(0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,0);
        GL20.glUseProgram(0);

        // Чистим память
        GL30.glDeleteVertexArrays(vao);
        GL15.glDeleteBuffers(vbo);
    }
}
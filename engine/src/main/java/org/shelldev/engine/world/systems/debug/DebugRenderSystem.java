package org.shelldev.engine.world.systems.debug;

import java.util.ArrayList;

import org.shelldev.engine.GameConfig;
import org.shelldev.engine.graphics.Render;
import org.shelldev.engine.graphics.Shader;
import org.shelldev.engine.math.Vector2f;
import org.shelldev.engine.physics.BoxCollider;
import org.shelldev.engine.physics.Collider;
import org.shelldev.engine.utils.Color;
import org.shelldev.engine.utils.ComponentRequirements;
import org.shelldev.engine.world.Entity;
import org.shelldev.engine.world.System;
import org.shelldev.engine.world.SystemManager;
import org.shelldev.engine.world.World;
import org.shelldev.engine.world.components.Position;

public class DebugRenderSystem extends System<Collider> {

    public static final DebugRenderSystem Instance = new DebugRenderSystem();
    static { SystemManager.registerSystem(Instance); }

    private Shader _lineShader;

    @Override
    public void init(){
        try {
            _lineShader = new Shader(
                "shaders/line.vert",
                "shaders/line.frag"
            );   
        } catch (Exception e) {
        }
    }

    @Override
    public void update(){
        if(!GameConfig.getInstance().isDebug()) return;

        ArrayList<Entity> entities = World.Instance.getEntities();

        for (Entity entity : entities){
            if (!ComponentRequirements.hasIn(entity, 
                Position.class
            )){
                continue;
            }
            Vector2f worldAnchor = Position.getGlobalPosition(entity);
            Vector2f offset = Vector2f.One.mul(5.0f);
            Color color = new Color(0, 0, 0, 1.0f);
            Render.getInstance().drawLine(_lineShader, worldAnchor.add(offset.mul(new Vector2f(-1, -1))), worldAnchor.add(offset.mul(new Vector2f(-1, 1))), color);
            Render.getInstance().drawLine(_lineShader, worldAnchor.add(offset.mul(new Vector2f(-1, -1))), worldAnchor.add(offset.mul(new Vector2f(1, -1))), color);
            Render.getInstance().drawLine(_lineShader, worldAnchor.add(offset.mul(new Vector2f(1, 1))), worldAnchor.add(offset.mul(new Vector2f(1, -1))), color);
            Render.getInstance().drawLine(_lineShader, worldAnchor.add(offset.mul(new Vector2f(1, 1))), worldAnchor.add(offset.mul(new Vector2f(-1, 1))), color);

            if (!ComponentRequirements.hasIn(entity, 
                BoxCollider.class
            )){
                continue;
            }

            BoxCollider bc = entity.getComponent(BoxCollider.class);
            worldAnchor = bc.getColliderCenter();
            offset = new Vector2f(bc.getWidth()/2, bc.getHeight()/2);
            color = new Color(1f, 0f, 0f, 1f);
            Render.getInstance().drawLine(_lineShader, worldAnchor.add(offset.mul(new Vector2f(-1, -1))), worldAnchor.add(offset.mul(new Vector2f(-1, 1))), color);
            Render.getInstance().drawLine(_lineShader, worldAnchor.add(offset.mul(new Vector2f(-1, -1))), worldAnchor.add(offset.mul(new Vector2f(1, -1))), color);
            Render.getInstance().drawLine(_lineShader, worldAnchor.add(offset.mul(new Vector2f(1, 1))), worldAnchor.add(offset.mul(new Vector2f(1, -1))), color);
            Render.getInstance().drawLine(_lineShader, worldAnchor.add(offset.mul(new Vector2f(1, 1))), worldAnchor.add(offset.mul(new Vector2f(-1, 1))), color);
        }
    }
}
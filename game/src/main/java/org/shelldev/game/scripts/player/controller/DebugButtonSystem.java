package org.shelldev.game.scripts.player.controller;

import java.util.ArrayList;

import org.shelldev.engine.math.Vector2f;
import org.shelldev.engine.physics.Rigidbody;
import org.shelldev.engine.utils.ComponentRequirements;
import org.shelldev.engine.utils.ObjectFactory;
import org.shelldev.engine.world.Entity;
import org.shelldev.engine.world.System;
import org.shelldev.engine.world.SystemManager;
import org.shelldev.engine.world.World;
import org.shelldev.engine.world.components.Position;
import org.shelldev.engine.world.systems.InputSystem;

public class DebugButtonSystem extends System<DebugButtonsComponent> {
    public final static DebugButtonSystem Instance = new DebugButtonSystem();

    static {
        SystemManager.registerSystem(DebugButtonSystem.Instance);
    }

    @Override
    public void update(){
        ArrayList<Entity> entities = World.Instance.getChilds();
        for (Entity entity : entities){
            if (!ComponentRequirements.hasIn(entity, 
                DebugButtonsComponent.class,
                Rigidbody.class
            )){
                continue;
            }
            if (InputSystem.Instance.isKeyPressed(InputSystem.Key.G)){
                Rigidbody rb = entity.getComponent(Rigidbody.class);
                boolean g = rb.getUseGravity();
                if (g){
                    rb.setVelocity(Vector2f.Zero);
                }
                rb.setUseGravity(!g);
            }
            if (InputSystem.Instance.isKeyPressed(InputSystem.Key.V)){
                ControllerSystem.switchVelocity();
            }
            if (InputSystem.Instance.isKeyPressed(InputSystem.Key.I)){
                ObjectFactory.entityRigidbody(
                    1,
                    false,
                    true,
                    "shaders/fragment2.glsl",
                    null,
                    null,
                    Vector2f.One.mul(10f),
                    1,
                    Position.getGlobalPosition(entity).add(new Vector2f(0, 200f)),
                    null
                ).setParent(World.Instance.getWorldObject());
            }
        }
    }
}

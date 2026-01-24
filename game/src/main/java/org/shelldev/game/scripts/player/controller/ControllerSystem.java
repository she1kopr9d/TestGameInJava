package org.shelldev.game.scripts.player.controller;

import java.util.ArrayList;

import org.shelldev.engine.utils.MoveInput;
import org.shelldev.engine.world.Entity;
import org.shelldev.engine.world.System;
import org.shelldev.engine.world.SystemManager;
import org.shelldev.engine.world.World;
import org.shelldev.engine.world.components.Position;

public class ControllerSystem extends System<ControllerComponent> {
    public final static ControllerSystem Instance = new ControllerSystem();

    static {
        SystemManager.registerSystem(ControllerSystem.Instance);
    }

    private final float _speed = 5.0f;

    @Override
    public void update(){
        ArrayList<Entity> entities = World.Instance.getChilds();
        for (Entity entity : entities){
            if (entity.getComponent(ControllerComponent.class) == null)
                continue;
            Position position = entity.getComponent(Position.class);
            position.addVector(MoveInput.GetMoveInput().multiply(_speed));
        }
    }
}

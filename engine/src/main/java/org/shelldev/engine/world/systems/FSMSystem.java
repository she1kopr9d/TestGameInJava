package org.shelldev.engine.world.systems;

import java.util.ArrayList;

import org.shelldev.engine.utils.ComponentRequirements;
import org.shelldev.engine.world.Entity;
import org.shelldev.engine.world.System;
import org.shelldev.engine.world.SystemManager;
import org.shelldev.engine.world.World;
import org.shelldev.engine.world.components.FSMComponent;

public class FSMSystem extends System<FSMComponent> {
    public static final FSMSystem Instance = new FSMSystem();

    static {
        SystemManager.registerSystem(Instance);
    }

    @Override
    public void update() {
        ArrayList<Entity> entities = World.Instance.getEntities();
        for (Entity entity : entities) {
            if (!ComponentRequirements.hasIn(entity,
                    FSMComponent.class)) {
                continue;
            }
            FSMComponent fsm = entity.getComponent(FSMComponent.class);
            fsm.update(fsm.getEntity());
        }
    }
}
package org.shelldev.engine.world.systems;

import java.util.ArrayList;

import org.shelldev.engine.math.Vector2f;
import org.shelldev.engine.world.Entity;
import org.shelldev.engine.world.System;
import org.shelldev.engine.world.SystemManager;
import org.shelldev.engine.world.World;
import org.shelldev.engine.world.components.Camera;
import org.shelldev.engine.world.components.Position;

public class CameraSystem extends System<Camera> {

    public static final CameraSystem Instance = new CameraSystem();

    static {
        SystemManager.registerSystem(Instance);
    }

    private Vector2f cameraOffset = new Vector2f(0, 0);

    public Vector2f getCameraOffset() {
        return cameraOffset;
    }

    @Override
    public void update() {
        ArrayList<Entity> entities = World.Instance.getEntities();
        for (Entity target : entities) {
            Camera cam = target.getComponent(Camera.class);
            if (cam != null) {
                Position pos = target.getComponent(Position.class);
                if (pos != null) {

                    Vector2f targetPos = pos.getLocalPosition();
                    cameraOffset = cameraOffset.lerp(
                        targetPos,
                        cam.getSmoothness() * TimeSystem.Instance.getDeltaTime()
                    );
                }
            }
        }
    }
}
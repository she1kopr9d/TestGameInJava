package org.shelldev.game.scripts.player.controller;

import java.util.ArrayList;

import org.shelldev.engine.math.Vector2f;
import org.shelldev.engine.physics.Rigidbody;
import org.shelldev.engine.utils.MoveInput;
import org.shelldev.engine.world.Entity;
import org.shelldev.engine.world.System;
import org.shelldev.engine.world.SystemManager;
import org.shelldev.engine.world.World;
import org.shelldev.engine.world.components.Position;

public class ControllerSystem extends System<ControllerComponent> {

    public final static ControllerSystem Instance = new ControllerSystem();
    private static boolean velocityMode = false;

    static {
        SystemManager.registerSystem(ControllerSystem.Instance);
    }

    private final float _speed = 5.0f; // горизонтальная скорость

    public static void switchVelocity() {
        velocityMode = !velocityMode;
    }

    @Override
    public void update() {
        float dt = 0.016f; // можно заменить на TimeSystem.Instance.getDeltaTime()
        MoveInput.Update(dt); // обновляем состояние кнопки прыжка

        ArrayList<Entity> entities = World.Instance.getChilds();

        for (Entity entity : entities) {
            ControllerComponent ctrl = entity.getComponent(ControllerComponent.class);
            if (ctrl == null) continue;

            Rigidbody rb = entity.getComponent(Rigidbody.class);
            Position pos = entity.getComponent(Position.class);

            Vector2f input = MoveInput.GetMoveInput();

            // ============================
            // Горизонтальное движение
            // ============================
            if (velocityMode && rb != null) {
                rb.addForce(input.mul(_speed * 200f));
            } else if (pos != null) {
                pos.addVector(input.mul(_speed));
            }

            // ============================
            // Прыжок — срабатывает при отпускании пробела
            // ============================
            if (rb != null && rb.isGrounded() && MoveInput.IsJumpReleased()) {
                float jumpStrength = MoveInput.GetJumpStrength() * 100;
                rb.addForce(new Vector2f(0, rb.getMass() * jumpStrength / dt));
                MoveInput.ConsumeJump();
            }
        }
    }
}
package org.shelldev.engine.utils;

import org.shelldev.engine.math.Vector2f;
import org.shelldev.engine.world.systems.InputSystem;;

public class MoveInput {
    public static Vector2f GetMoveInput() {
        float moveX = 0.0f;
        float moveY = 0.0f;

        if (InputSystem.Instance.isKeyPressed(InputSystem.Key.W) || InputSystem.Instance.isKeyDown(InputSystem.Key.W)) {
            moveY += 1.0f;
        }
        if (InputSystem.Instance.isKeyPressed(InputSystem.Key.S) || InputSystem.Instance.isKeyDown(InputSystem.Key.S)) {
            moveY -= 1.0f;
        }
        if (InputSystem.Instance.isKeyPressed(InputSystem.Key.A) || InputSystem.Instance.isKeyDown(InputSystem.Key.A)) {
            moveX -= 1.0f;
        }
        if (InputSystem.Instance.isKeyPressed(InputSystem.Key.D) || InputSystem.Instance.isKeyDown(InputSystem.Key.D)) {
            moveX += 1.0f;
        }

        if (moveX != 0.0f && moveY != 0.0f) {
            float invSqrt2 = 1.0f / (float) Math.sqrt(2);
            moveX *= invSqrt2;
            moveY *= invSqrt2;
        }

        return new Vector2f(moveX, moveY);
    }
}

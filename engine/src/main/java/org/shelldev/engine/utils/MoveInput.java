package org.shelldev.engine.utils;

import org.shelldev.engine.math.Vector2f;
import org.shelldev.engine.world.systems.InputSystem;

public class MoveInput {

    private static boolean jumpHeld = false;   // кнопка удерживается
    private static boolean jumpReleased = false; // отпущена
    private static float jumpHoldTime = 0f;     // время удержания пробела
    private static final float MAX_HOLD = 0.25f; // макс. время удержания (сек)
    private static final float BASE_JUMP = 5f;   // минимальная сила прыжка
    private static final float MAX_JUMP = 10f;   // максимальная сила прыжка

    /** Горизонтальное движение */
    public static Vector2f GetMoveInput() {
        float moveX = 0f;
        float moveY = 0f;

        if (InputSystem.Instance.isKeyPressed(InputSystem.Key.W) || InputSystem.Instance.isKeyDown(InputSystem.Key.W)) moveY += 1f;
        if (InputSystem.Instance.isKeyPressed(InputSystem.Key.S) || InputSystem.Instance.isKeyDown(InputSystem.Key.S)) moveY -= 1f;
        if (InputSystem.Instance.isKeyPressed(InputSystem.Key.A) || InputSystem.Instance.isKeyDown(InputSystem.Key.A)) moveX -= 1f;
        if (InputSystem.Instance.isKeyPressed(InputSystem.Key.D) || InputSystem.Instance.isKeyDown(InputSystem.Key.D)) moveX += 1f;

        // нормализация диагонали
        if (moveX != 0f && moveY != 0f) {
            float invSqrt2 = 1f / (float)Math.sqrt(2);
            moveX *= invSqrt2;
            moveY *= invSqrt2;
        }

        return new Vector2f(moveX, moveY);
    }

    /** Вызывать каждый кадр для обновления состояния кнопки */
    public static void Update(float deltaTime) {
        boolean currentlyPressed = InputSystem.Instance.isKeyDown(InputSystem.Key.SPACE);

        jumpReleased = jumpHeld && !currentlyPressed; // true, если отпустили
        jumpHeld = currentlyPressed;

        if (currentlyPressed) {
            jumpHoldTime += deltaTime;
            if (jumpHoldTime > MAX_HOLD) jumpHoldTime = MAX_HOLD;
        }
    }

    /** Проверка, отпущена ли кнопка прыжка */
    public static boolean IsJumpReleased() {
        return jumpReleased;
    }

    /** Сила прыжка пропорциональна времени удержания */
    public static float GetJumpStrength() {
        float t = jumpHoldTime / MAX_HOLD; // 0..1
        return BASE_JUMP + t * (MAX_JUMP - BASE_JUMP);
    }

    /** Сбросить заряд после прыжка */
    public static void ConsumeJump() {
        jumpHoldTime = 0f;
        jumpReleased = false;
    }
}
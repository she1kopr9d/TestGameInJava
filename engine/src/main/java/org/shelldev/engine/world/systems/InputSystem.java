package org.shelldev.engine.world.systems;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LAST;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import org.shelldev.engine.Engine;
import org.shelldev.engine.world.Component;
import org.shelldev.engine.world.System;
import org.shelldev.engine.world.SystemManager;

public class InputSystem extends System<Component> {
    public final static InputSystem Instance = new InputSystem();

    static{
        SystemManager.registerSystem(InputSystem.Instance);
    }

    public enum Key {
        W(GLFW_KEY_W),
        A(GLFW_KEY_A),
        S(GLFW_KEY_S),
        D(GLFW_KEY_D),
        UP(GLFW_KEY_UP),
        DOWN(GLFW_KEY_DOWN),
        LEFT(GLFW_KEY_LEFT),
        RIGHT(GLFW_KEY_RIGHT),
        SPACE(GLFW_KEY_SPACE),
        ESCAPE(GLFW_KEY_ESCAPE);
        public final int code;
        Key(int code) {
            this.code = code;
        }
    }

    private final boolean[] keys = new boolean[GLFW_KEY_LAST];
    private final boolean[] prevKeys = new boolean[GLFW_KEY_LAST];

    @Override
    public void init() {
        glfwSetKeyCallback(Engine.Instance.getWindow(), (w, key, scancode, action, mods) -> {
            if (key >= 0)
                keys[key] = action != GLFW_RELEASE;
        });
    }

    @Override
    public void update() {
        java.lang.System.arraycopy(keys, 0, prevKeys, 0, keys.length);
    }

    public boolean isKeyDown(Key key) {
        return keys[key.code];
    }

    public boolean isKeyPressed(Key key) {
        return keys[key.code] && !prevKeys[key.code];
    }

    public boolean isKeyReleased(Key key) {
        return !keys[key.code] && prevKeys[key.code];
    }
}
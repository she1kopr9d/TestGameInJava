package org.shelldev.engine.world.systems;

import org.shelldev.engine.world.Component;
import org.shelldev.engine.world.SystemManager;

public class TimeSystem extends org.shelldev.engine.world.System<Component> {

    public static final TimeSystem Instance = new TimeSystem();

    static {
        SystemManager.registerSystem(TimeSystem.Instance);
    }

    private double time = 0.0;
    private double deltaTime = 0.0;
    private double lastTime;

    private long tick = 0;

    public TimeSystem() {
        lastTime = now();
    }

    private double now() {
        return java.lang.System.nanoTime() / 1_000_000_000.0;
    }

    @Override
    public void update() {
        double current = now();

        deltaTime = current - lastTime;
        lastTime = current;

        time += deltaTime;
        tick++;
    }

    public float getTime() {
        return (float)time;
    }

    public float getDeltaTime() {
        return (float)deltaTime;
    }

    public long getTick() {
        return tick;
    }
}
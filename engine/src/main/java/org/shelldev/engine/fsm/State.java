package org.shelldev.engine.fsm;

import java.util.function.Consumer;

import org.shelldev.engine.world.Entity;


public class State {
    private final String name;
    private final Consumer<Entity> onEnter;
    private final Consumer<Entity> onExit;

    public State(String name, Consumer<Entity> onEnter, Consumer<Entity> onExit) {
        this.name = name;
        this.onEnter = onEnter;
        this.onExit = onExit;
    }

    public String getName() {
        return name;
    }

    public void enter(Entity entity) {
        if (onEnter != null) onEnter.accept(entity);
    }

    public void exit(Entity entity) {
        if (onExit != null) onExit.accept(entity);
    }
}
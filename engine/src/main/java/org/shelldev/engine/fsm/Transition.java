package org.shelldev.engine.fsm;

import java.util.function.Predicate;

import org.shelldev.engine.world.Entity;


public class Transition {
    private final State to;
    private final Predicate<Entity> condition; // условие перехода

    public Transition(State to, Predicate<Entity> condition) {
        this.to = to;
        this.condition = condition;
    }

    public boolean isTriggered(Entity entity) {
        return condition.test(entity);
    }

    public State getTargetState() {
        return to;
    }
}
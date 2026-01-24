package org.shelldev.engine.world.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.shelldev.engine.fsm.State;
import org.shelldev.engine.fsm.Transition;
import org.shelldev.engine.world.Component;
import org.shelldev.engine.world.Entity;


public class FSMComponent extends Component {
    private State currentState;
    private final List<State> states = new ArrayList<>();
    private final Map<State, List<Transition>> transitions = new HashMap<>();

    public void addState(State state) {
        states.add(state);
        transitions.put(state, new ArrayList<>());
    }

    public void addTransition(State from, State to, Predicate<Entity> condition) {
        transitions.get(from).add(new Transition(to, condition));
    }

    public void setInitialState(State state, Entity entity) {
        currentState = state;
        currentState.enter(entity);
    }

    public State getCurrentState() {
        return currentState;
    }

    public void update(Entity entity) {
        if (currentState == null) return;

        for (Transition t : transitions.get(currentState)) {
            if (t.isTriggered(entity)) {
                currentState.exit(entity);
                currentState = t.getTargetState();
                currentState.enter(entity);
                break;
            }
        }
    }
}
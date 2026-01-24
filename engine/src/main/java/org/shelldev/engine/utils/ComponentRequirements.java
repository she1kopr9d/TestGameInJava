package org.shelldev.engine.utils;

import org.shelldev.engine.world.Component;
import org.shelldev.engine.world.Entity;

public class ComponentRequirements {

    @SafeVarargs
    public static boolean hasIn(Entity entity, Class<? extends Component>... components) {
        for (Class<? extends Component> component : components) {
            if (entity.getComponent(component) == null) {
                return false;
            }
        }
        return true;
    }
}
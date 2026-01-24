package org.shelldev.engine.world;

import java.util.ArrayList;
import java.util.List;

public abstract class System<T extends Component> extends ESystemBase {
    protected Class<T> componentClass;
    protected List<Component> getSystemComponents(Class<? extends Component> clazz) {
        List<Component> components = new ArrayList<>();
        for (Entity entity : World.Instance.getEntities()) {
            Component component = entity.getComponent(clazz);
            if (component != null) components.add(component);
        }
        return components;
    }
    public void create(){}
    public void init(){}
    public void update(){}
    public void remove(){}
}
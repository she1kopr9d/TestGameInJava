package org.shelldev.engine.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.shelldev.engine.math.Vector2f;
import org.shelldev.engine.world.components.Position;

public class Entity {
    private final int _id;
    private final Map<Class<? extends Component>, Component> _components = new HashMap<>();
    private Entity _parent;
    private final ArrayList<Entity> _childs;

    public Entity(int id) {
        _id = id;
        _parent = null;
        _childs = new ArrayList<>();
    }

    public int getId() {
        return _id;
    }

    public <T extends Component> void addComponent(T component) {
        _components.put(component.getClass(), component);
        component.setEntity(this);
    }

    public <T extends Component> T getComponent(Class<T> class_type) {
        return class_type.cast(_components.get(class_type));
    }

    public void setParent(Entity parent){
        if (_parent != null){
            Position position = getComponent(Position.class);
            if (position != null){
                Vector2f absPos = Position.getAbsPosition(this);
                position.setPosition(absPos);
            }
            _parent.deleteChild(this);
        }
        _parent = parent;
        parent.addChild(this);
    }

    public void deleteParent(){
        if (_parent != null){
            Position position = getComponent(Position.class);
            if (position != null){
                Vector2f absPos = Position.getAbsPosition(this);
                position.setPosition(absPos);
            }
            _parent.deleteChild(this);
            _parent = World.Instance.getWorldObject();
            World.Instance.getWorldObject().addChild(this);
        }
    }

    private void addChild(Entity child){
        _childs.add(child);
    }

    private void deleteChild(Entity child){
        _childs.remove(child);
    }

    public Entity getParent(){
        return _parent;
    }

    public ArrayList<Entity> getChilds(){
        return _childs;
    }
}
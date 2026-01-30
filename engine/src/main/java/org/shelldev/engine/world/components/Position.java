package org.shelldev.engine.world.components;

import org.shelldev.engine.math.Vector2f;
import org.shelldev.engine.world.Component;
import org.shelldev.engine.world.Entity;

public class Position extends Component{
    private Vector2f _position;
    private Vector2f _anchor;

    public static Vector2f getGlobalPosition(Entity entity){
        Position position = entity.getComponent(Position.class);
        Vector2f absPosition = new Vector2f(0, 0);
        if (position != null){
            absPosition = position.getLocalPosition();
        }

        Entity parent = entity.getParent();
        if (parent != null){
            absPosition = absPosition.add(getGlobalPosition(parent));
        }

        return absPosition;
    }

    public Position(Vector2f position, Vector2f anchor){
        _position = position;
        _anchor = anchor != null ? anchor : Vector2f.One.mul(0.5f);
    }

    public Vector2f getLocalPosition(){
        return _position;
    }

    public void setPosition(Vector2f position){
        _position = position;
    }

    public void addVector(Vector2f vector){
        _position = _position.add(vector);
    }

    public Vector2f getAnchor(){
        return _anchor;
    }

    public void setAnchor(Vector2f anchor){
        _anchor = anchor;
    }
}

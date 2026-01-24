package org.shelldev.engine.world.components;

import org.shelldev.engine.math.Vector2f;
import org.shelldev.engine.world.Component;
import org.shelldev.engine.world.Entity;

public class Position extends Component{
    private Vector2f _position;

    public static Vector2f getAbsPosition(Entity entity){
        Position position = entity.getComponent(Position.class);
        Vector2f absPosition = new Vector2f(0, 0);
        if (position != null){
            absPosition = position.getPosition();
        }

        Entity parent = entity.getParent();
        if (parent != null){
            absPosition = absPosition.add(getAbsPosition(parent));
        }

        return absPosition;
    }

    public Position(Vector2f position){
        _position = position;
    }

    public Vector2f getPosition(){
        return _position;
    }

    public void setPosition(Vector2f position){
        _position = position;
    }

    public void addVector(Vector2f vector){
        _position = _position.add(vector);
    }
}

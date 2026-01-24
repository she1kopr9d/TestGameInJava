package org.shelldev.engine.world;

public class Component {
    private Entity _entity;

    public void setEntity(Entity entity){
        _entity = entity;
    }

    public Entity getEntity(){
        return _entity;
    }
}
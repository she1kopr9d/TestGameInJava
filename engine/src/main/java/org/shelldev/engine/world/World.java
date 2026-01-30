package org.shelldev.engine.world;

import java.util.ArrayList;

public class World {
    public final static World Instance = new World();

    private final ArrayList<Entity> _entities;
    private int _nextEntityId = 0;
    private final Entity _worldObject;

    public World() {
        _entities = new ArrayList<>();
        _worldObject = new Entity(generateEntityId());
    }

    public Entity createEntity() {
        Entity entity = new Entity(generateEntityId());
        _entities.add(entity);
        return entity;
    }

    public void removeEntity(Entity e) {
        _entities.remove(e);
    }

    public ArrayList<Entity> getEntities() {
        return new ArrayList<Entity>(_entities);
    }

    private int generateEntityId() {
        return _nextEntityId++;
    }

    public ArrayList<Entity> getChilds(){
        return new ArrayList<Entity>(_worldObject.getChilds());
    }

    public Entity getWorldObject(){
        return _worldObject;
    }
}
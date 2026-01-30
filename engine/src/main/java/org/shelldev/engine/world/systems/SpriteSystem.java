package org.shelldev.engine.world.systems;

import java.util.ArrayList;

import org.shelldev.engine.graphics.Render;
import org.shelldev.engine.world.Entity;
import org.shelldev.engine.world.System;
import org.shelldev.engine.world.SystemManager;
import org.shelldev.engine.world.World;
import org.shelldev.engine.world.components.Position;
import org.shelldev.engine.world.components.Sprite;

public class SpriteSystem extends System<Sprite> {

    public static final SpriteSystem Instance = new SpriteSystem();
    static { SystemManager.registerSystem(Instance); }

    private static class RenderEntry {
        Entity entity;
        Sprite sprite;
        Position position;

        RenderEntry(Entity e, Sprite s, Position p){
            entity = e;
            sprite = s;
            position = p;
        }
    }

    private void collect(Entity entity, ArrayList<RenderEntry> list){
        Sprite sprite = entity.getComponent(Sprite.class);
        Position pos = entity.getComponent(Position.class);

        if(sprite != null && pos != null){
            list.add(new RenderEntry(entity, sprite, pos));
        }

        for(Entity child : entity.getChilds()){
            collect(child, list);
        }
    }

    @Override
    public void update(){
        ArrayList<RenderEntry> entries = new ArrayList<>();
        for(Entity e : World.Instance.getEntities()){
            collect(e, entries);
        }

        // Сортировка по слоям
        entries.sort((a,b) -> Integer.compare(
                a.sprite.getLayer(),
                b.sprite.getLayer()
        ));

        for(RenderEntry entry : entries){
            Entity entity = entry.entity;
            Sprite sprite = entry.sprite;
            Position pos = entry.position;
            Render.getInstance().drawSprite(
                    sprite.getShader(),
                    sprite.getTexture(),
                    Position.getGlobalPosition(entity),
                    sprite.getSize(),
                    pos.getAnchor(),
                    sprite.getColor()
            );
        }
    }
}

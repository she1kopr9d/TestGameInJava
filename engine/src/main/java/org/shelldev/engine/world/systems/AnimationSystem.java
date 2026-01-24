package org.shelldev.engine.world.systems;

import java.util.ArrayList;

import org.shelldev.engine.utils.ComponentRequirements;
import org.shelldev.engine.world.Entity;
import org.shelldev.engine.world.SystemManager;
import org.shelldev.engine.world.World;
import org.shelldev.engine.world.components.AnimationComponent;
import org.shelldev.engine.world.components.Sprite;

public class AnimationSystem extends org.shelldev.engine.world.System<AnimationComponent> {

    public static final AnimationSystem Instance = new AnimationSystem();

    static {
        SystemManager.registerSystem(AnimationSystem.Instance);
    }

    private void updateValidEntity(
        float deltaTime,
        AnimationComponent animComp,
        Sprite sprite
    ){
        animComp.setTimer(animComp.getTimer() + deltaTime);
        int frame = animComp.getCurrentFrame();
        float frameDuration = animComp.getAnimation().frameDuration;
        if (animComp.getTimer() >= frameDuration) {
            animComp.setTimer(animComp.getTimer() - frameDuration);
            frame++;
            if (frame >= animComp.getAnimation().frames.length) {
                if (animComp.getAnimation().loop) {
                    frame = 0;
                } else {
                    frame = animComp.getAnimation().frames.length - 1;
                }
            }
            animComp.setCurrentFrame(frame);
        }
        sprite.setTexture(animComp.getAnimation().frames[animComp.getCurrentFrame()]);
    }

    @Override
    public void update() {
        ArrayList<Entity> entities = World.Instance.getEntities();
        float deltaTime = TimeSystem.Instance.getDeltaTime();
        for (Entity entity : entities) {
            if (!ComponentRequirements.hasIn(entity,
                    AnimationComponent.class, Sprite.class)) {
                continue;
            }
            Sprite sprite = entity.getComponent(Sprite.class);
            AnimationComponent animComp = entity.getComponent(AnimationComponent.class);
            updateValidEntity(deltaTime, animComp, sprite);
        }
    }
}
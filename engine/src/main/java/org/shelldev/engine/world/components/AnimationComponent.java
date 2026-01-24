package org.shelldev.engine.world.components;

import org.shelldev.engine.graphics.Animation;
import org.shelldev.engine.world.Component;

public class AnimationComponent extends Component {

    private Animation animation;
    private int currentFrame;
    private float timer;

    public AnimationComponent(Animation animation) {
        this.animation = animation;
        this.currentFrame = 0;
        this.timer = 0f;
    }

    public Animation getAnimation() {
        return animation;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public float getTimer() {
        return timer;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public void setTimer(float timer) {
        this.timer = timer;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
        this.currentFrame = 0;
        this.timer = 0f;
    }
}
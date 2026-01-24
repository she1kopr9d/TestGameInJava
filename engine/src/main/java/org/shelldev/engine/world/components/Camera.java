package org.shelldev.engine.world.components;

import org.shelldev.engine.math.Vector2f;
import org.shelldev.engine.world.Component;
import org.shelldev.engine.world.Entity;

public class Camera extends Component {

    private Vector2f position = new Vector2f(0, 0);
    private Entity followTarget;
    private float smoothness = 5f; // плавность следования

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public void follow(Entity entity) {
        this.followTarget = entity;
    }

    public Entity getFollowTarget() {
        return followTarget;
    }

    public float getSmoothness() {
        return smoothness;
    }

    public void setSmoothness(float smoothness) {
        this.smoothness = smoothness;
    }
}
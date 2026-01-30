package org.shelldev.engine.physics;

import org.shelldev.engine.math.Vector2f;

public class CircleCollider extends Collider {

    private float radius;

    public CircleCollider(float radius) {
        this.radius = radius;
    }

    public CircleCollider(float radius, Vector2f offset) {
        super(offset);
        this.radius = radius;
    }

    public float getRadius() {
        return radius;
    }

    public Vector2f getCenter() {
        return getWorldPosition();
    }
}
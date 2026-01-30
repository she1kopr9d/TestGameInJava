package org.shelldev.engine.physics;

import org.shelldev.engine.math.Vector2f;

public class BoxCollider extends Collider {

    private float width;
    private float height;

    public BoxCollider(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public BoxCollider(float width, float height, Vector2f offset) {
        super(offset);
        this.width = width;
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getLeft() {
        return getWorldPosition().x();
    }

    public float getRight() {
        return getWorldPosition().x() + width;
    }

    public float getTop() {
        return getWorldPosition().y();
    }

    public float getBottom() {
        return getWorldPosition().y() + height;
    }

    public Vector2f getCenter() {
        return new Vector2f(
            getLeft() + width * 0.5f,
            getTop() + height * 0.5f
        );
    }

    @Override
    public Vector2f getColliderCenter() {
        // позиция объекта + offset
        return getEntity().getWorldPosition().add(offset);
    }
}
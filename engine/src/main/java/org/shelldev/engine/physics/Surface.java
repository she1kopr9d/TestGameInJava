package org.shelldev.engine.physics;

public class Surface {

    private float friction; // 0 = мгновенная остановка, 1 = нет трения

    public Surface(float friction) {
        setFriction(friction);
    }

    public float getFriction() {
        return friction;
    }

    public void setFriction(float friction) {
        // ограничиваем 0..1
        this.friction = Math.max(0f, Math.min(friction, 1f));
    }
}
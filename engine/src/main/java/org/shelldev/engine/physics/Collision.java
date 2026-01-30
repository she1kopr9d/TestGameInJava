package org.shelldev.engine.physics;

import org.shelldev.engine.math.Vector2f;

public class Collision {

    private final Collider a;
    private final Collider b;
    private final Vector2f normal;      // нормаль столкновения
    private final float penetration;    // глубина перекрытия

    public Collision(Collider a, Collider b, Vector2f normal, float penetration) {
        this.a = a;
        this.b = b;
        this.normal = normal;
        this.penetration = penetration;
    }

    public Collider getA() { return a; }
    public Collider getB() { return b; }
    public Vector2f getNormal() { return normal; }
    public float getPenetration() { return penetration; }
}
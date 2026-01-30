package org.shelldev.engine.physics;

import org.shelldev.engine.math.Vector2f;
import org.shelldev.engine.world.Component;

public class Rigidbody extends Component {

    private Vector2f velocity = new Vector2f(0, 0);
    private Vector2f acceleration = new Vector2f(0, 0);
    private float mass = 1.0f;
    private boolean useGravity = true;
    private boolean isStatic = false;
    private boolean grounded = false;

    public Rigidbody(float mass, boolean isStatic) {
        this.isStatic = isStatic;
        if (isStatic) {
            this.mass = Float.POSITIVE_INFINITY;
            this.velocity = new Vector2f(0, 0);
            this.acceleration = new Vector2f(0, 0);
        } else {
            this.mass = Math.max(mass, 0.0001f);
        }
    }

    public Vector2f getVelocity() {
        return new Vector2f(velocity.x(), velocity.y());
    }


    public void setVelocity(Vector2f velocity) {
        if (isStatic) return;
        this.velocity = new Vector2f(velocity.x(), velocity.y());
    }

    /** Получить текущее ускорение */
    public Vector2f getAcceleration() {
        return new Vector2f(acceleration.x(), acceleration.y());
    }

    /** Добавить силу к объекту с учётом массы */
    public void addForce(Vector2f force) {
        if (isStatic || mass <= 0) return;
        Vector2f a = force.mul(1.0f / mass);
        acceleration = acceleration.add(a);
    }

    /** Прямое добавление ускорения (например, гравитация) */
    public void addAcceleration(Vector2f a) {
        if (isStatic) return;
        acceleration = acceleration.add(a);
    }

    /** Обновляет скорость и сбрасывает ускорение */
    public void integrate(float deltaTime) {
        if (isStatic) return;
        velocity = velocity.add(acceleration.mul(deltaTime));
        acceleration = new Vector2f(0, 0); // сброс после применения
    }

    /** Прыжок: задаём мгновенную скорость по Y, если на земле */
    public void jump(float jumpStrength) {
        if (isStatic || !grounded) return;
        velocity = new Vector2f(velocity.x(), jumpStrength);
        grounded = false;
    }

    /** Нужно ли применять гравитацию */
    public boolean isUseGravity() {
        return useGravity && !isStatic;
    }

    public boolean getUseGravity() {
        return useGravity;
    }

    public void setUseGravity(boolean useGravity) {
        this.useGravity = useGravity;
    }

    /** Статический объект */
    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean aStatic) {
        this.isStatic = aStatic;
        if (isStatic) {
            velocity = new Vector2f(0, 0);
            acceleration = new Vector2f(0, 0);
            mass = Float.POSITIVE_INFINITY;
        }
    }

    /** Масса */
    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        if (isStatic) return;
        this.mass = Math.max(mass, 0.0001f);
    }

    /** Флаг на земле для прыжка */
    public boolean isGrounded() {
        return grounded;
    }

    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }
}
package org.shelldev.engine.world.systems;

import java.util.ArrayList;
import java.util.List;

import org.shelldev.engine.GameConfig;
import org.shelldev.engine.math.Vector2f;
import org.shelldev.engine.physics.Collider;
import org.shelldev.engine.physics.Collision;
import org.shelldev.engine.physics.CollisionManager;
import org.shelldev.engine.physics.Rigidbody;
import org.shelldev.engine.world.Entity;
import org.shelldev.engine.world.System;
import org.shelldev.engine.world.SystemManager;
import org.shelldev.engine.world.World;
import org.shelldev.engine.world.components.Position;

public class PhysicsSystem extends System<Rigidbody> {

    public static final PhysicsSystem Instance = new PhysicsSystem();
    static { SystemManager.registerSystem(Instance); }

    @Override
    public void update() {
        float dt = TimeSystem.Instance.getDeltaTime();
        if (dt <= 0) return;

        List<Collider> colliders = new ArrayList<>();

        // 1️⃣ Сначала применяем ускорения и интегрируем скорость
        for (Entity e : World.Instance.getEntities()) {
            Rigidbody rb = e.getComponent(Rigidbody.class);
            Position pos = e.getComponent(Position.class);
            Collider col = e.getComponent(Collider.class);

            if (col != null) colliders.add(col);
            if (rb == null || pos == null || rb.isStatic()) continue;

            // сбрасываем grounded в начале кадра
            rb.setGrounded(false);

            // гравитация по Y
            if (rb.isUseGravity()) {
                rb.addAcceleration(new Vector2f(0, -9.8f * GameConfig.getInstance().getGravityScale()));
            }

            // интеграция ускорение → скорость
            rb.integrate(dt);

            // перемещаем объект: скорость → позиция
            pos.addVector(rb.getVelocity().mul(dt));
        }

        // 2️⃣ Проверяем коллизии и разрешаем их
        List<Collision> collisions = CollisionManager.checkAndResolveCollisions(colliders);

        // 3️⃣ После коллизий: обновляем grounded, применяем массы и трение
        for (Collision c : collisions) {
            Collider aCol = c.getA();
            Collider bCol = c.getB();
            Vector2f normal = c.getNormal();

            Rigidbody rbA = aCol.getEntity().getComponent(Rigidbody.class);
            Rigidbody rbB = bCol.getEntity().getComponent(Rigidbody.class);

            // 3a️⃣ Обновляем grounded
            if (normal.y() > 0) {
                if (rbA != null && !rbA.isStatic()) rbA.setGrounded(true);
                if (rbB != null && !rbB.isStatic()) rbB.setGrounded(true);
            }

            // 3b️⃣ Применяем массу и импульс столкновения
            applyMassCollision(rbA, rbB, normal);

            // 3c️⃣ Применяем трение только по X при контакте по Y
            if (normal.y() != 0) {
                applyFriction(aCol);
                applyFriction(bCol);
            }
        }
    }

    /** Применяем импульс с учётом массы */
    private void applyMassCollision(Rigidbody rbA, Rigidbody rbB, Vector2f normal) {
        if (rbA == null && rbB == null) return;

        // оба динамические
        if (rbA != null && rbB != null && !rbA.isStatic() && !rbB.isStatic()) {
            float mA = rbA.getMass();
            float mB = rbB.getMass();

            Vector2f vA = rbA.getVelocity();
            Vector2f vB = rbB.getVelocity();

            // 1D-упрощение по нормали
            if (normal.x() != 0) {
                float vxA = (vA.x() * (mA - mB) + 2 * mB * vB.x()) / (mA + mB);
                float vxB = (vB.x() * (mB - mA) + 2 * mA * vA.x()) / (mA + mB);
                rbA.setVelocity(new Vector2f(vxA, vA.y()));
                rbB.setVelocity(new Vector2f(vxB, vB.y()));
            }

            if (normal.y() != 0) {
                float vyA = (vA.y() * (mA - mB) + 2 * mB * vB.y()) / (mA + mB);
                float vyB = (vB.y() * (mB - mA) + 2 * mA * vA.y()) / (mA + mB);
                rbA.setVelocity(new Vector2f(rbA.getVelocity().x(), vyA));
                rbB.setVelocity(new Vector2f(rbB.getVelocity().x(), vyB));
            }
        } else {
            // один статический — другой просто гасится по нормали
            if (rbA != null && !rbA.isStatic()) {
                Vector2f v = rbA.getVelocity();
                if (normal.x() != 0) v = new Vector2f(0, v.y());
                if (normal.y() != 0) v = new Vector2f(v.x(), 0);
                rbA.setVelocity(v);
            }
            if (rbB != null && !rbB.isStatic()) {
                Vector2f v = rbB.getVelocity();
                if (normal.x() != 0) v = new Vector2f(0, v.y());
                if (normal.y() != 0) v = new Vector2f(v.x(), 0);
                rbB.setVelocity(v);
            }
        }
    }

    /** Применяем трение по X */
    private void applyFriction(Collider col) {
        Rigidbody rb = col.getEntity().getComponent(Rigidbody.class);
        if (rb == null || rb.isStatic()) return;

        float friction = 1f;
        if (col.getSurface() != null) friction = col.getSurface().getFriction();

        Vector2f v = rb.getVelocity();
        rb.setVelocity(new Vector2f(v.x() * friction, v.y()));
    }
}
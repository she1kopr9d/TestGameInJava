package org.shelldev.engine.physics;

import java.util.ArrayList;
import java.util.List;

import org.shelldev.engine.math.Vector2f;
import org.shelldev.engine.world.components.Position;

public class CollisionManager {

    /** Проверка всех коллайдеров и разрешение коллизий */
    public static List<Collision> checkAndResolveCollisions(List<Collider> colliders) {
        List<Collision> collisions = new ArrayList<>();
        int size = colliders.size();

        for (int i = 0; i < size; i++) {
            Collider a = colliders.get(i);
            if (!a.isEnabled() || a.isTrigger()) continue;

            Rigidbody rbA = a.getEntity().getComponent(Rigidbody.class);
            Position posA = a.getEntity().getComponent(Position.class);
            if (posA == null) continue;

            for (int j = i + 1; j < size; j++) {
                Collider b = colliders.get(j);
                if (!b.isEnabled() || b.isTrigger()) continue;

                Rigidbody rbB = b.getEntity().getComponent(Rigidbody.class);
                Position posB = b.getEntity().getComponent(Position.class);
                if (posB == null) continue;

                if (isStatic(rbA) && isStatic(rbB)) continue;

                if (a instanceof BoxCollider boxA && b instanceof BoxCollider boxB) {
                    Vector2f normal = checkAABBCollision(boxA, boxB);
                    if (normal != null) {
                        float penetration = computePenetration(boxA, boxB, normal);
                        resolveCollision(boxA, rbA, posA, boxB, rbB, posB, normal, penetration);
                        collisions.add(new Collision(boxA, boxB, normal, penetration));
                    }
                }
            }
        }

        return collisions;
    }

    /** Проверка AABB, возвращает нормаль столкновения */
    private static Vector2f checkAABBCollision(BoxCollider a, BoxCollider b) {
        Vector2f delta = b.getColliderCenter().sub(a.getColliderCenter());
        float overlapX = (a.getWidth() / 2 + b.getWidth() / 2) - Math.abs(delta.x());
        float overlapY = (a.getHeight() / 2 + b.getHeight() / 2) - Math.abs(delta.y());

        if (overlapX > 0 && overlapY > 0) {
            return overlapX < overlapY ? new Vector2f(Math.signum(delta.x()), 0) 
                                      : new Vector2f(0, Math.signum(delta.y()));
        }
        return null;
    }

    private static float computePenetration(BoxCollider a, BoxCollider b, Vector2f normal) {
        Vector2f delta = b.getColliderCenter().sub(a.getColliderCenter());
        return normal.x() != 0 
            ? (a.getWidth() / 2 + b.getWidth() / 2) - Math.abs(delta.x())
            : (a.getHeight() / 2 + b.getHeight() / 2) - Math.abs(delta.y());
    }

    /** Разрешение коллизии с гашением скорости и трением */
    private static void resolveCollision(BoxCollider a, Rigidbody rbA, Position posA,
                                         BoxCollider b, Rigidbody rbB, Position posB,
                                         Vector2f normal, float penetration) {

        boolean aDynamic = rbA != null && !rbA.isStatic();
        boolean bDynamic = rbB != null && !rbB.isStatic();

        float shift = penetration / ((aDynamic && bDynamic) ? 2f : 1f);

        if (aDynamic) posA.addVector(normal.mul(-shift));
        if (bDynamic) posB.addVector(normal.mul(shift));

        // гасим скорость по нормали и применяем трение по X
        if (aDynamic) {
            Vector2f v = rbA.getVelocity();
            if (normal.x() != 0) v = new Vector2f(0, v.y());
            if (normal.y() != 0) {
                v = new Vector2f(v.x() * (a.getSurface() != null ? a.getSurface().getFriction() : 1f), 0);
            }
            rbA.setVelocity(v);
        }

        if (bDynamic) {
            Vector2f v = rbB.getVelocity();
            if (normal.x() != 0) v = new Vector2f(0, v.y());
            if (normal.y() != 0) {
                v = new Vector2f(v.x() * (b.getSurface() != null ? b.getSurface().getFriction() : 1f), 0);
            }
            rbB.setVelocity(v);
        }
    }

    private static boolean isStatic(Rigidbody rb) {
        return rb == null || rb.isStatic();
    }
}
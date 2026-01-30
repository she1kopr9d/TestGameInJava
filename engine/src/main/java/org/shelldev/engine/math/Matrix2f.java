package org.shelldev.engine.math;

public class Matrix2f {

    public final float m00, m01;
    public final float m10, m11;

    public Matrix2f() {
        this(1, 0,
             0, 1);
    }

    public Matrix2f(float m00, float m01,
                    float m10, float m11) {
        this.m00 = m00; this.m01 = m01;
        this.m10 = m10; this.m11 = m11;
    }

    public static Matrix2f identity() {
        return new Matrix2f(1, 0, 0, 1);
    }

    // ===== Умножение матриц =====
    public Matrix2f mul(Matrix2f o) {
        return new Matrix2f(
            m00 * o.m00 + m01 * o.m10,
            m00 * o.m01 + m01 * o.m11,
            m10 * o.m00 + m11 * o.m10,
            m10 * o.m01 + m11 * o.m11
        );
    }

    // ===== Умножение на вектор =====
    public Vector2f mul(Vector2f v) {
        return new Vector2f(
            m00 * v.x() + m01 * v.y(),
            m10 * v.x() + m11 * v.y()
        );
    }

    // ===== Умножение на скаляр =====
    public Matrix2f mul(float scalar) {
        return new Matrix2f(
            m00 * scalar, m01 * scalar,
            m10 * scalar, m11 * scalar
        );
    }

    // ===== Детерминант =====
    public float det() {
        return m00 * m11 - m01 * m10;
    }

    // ===== Инверсия =====
    public Matrix2f invert() {
        float d = det();
        if (d == 0) throw new IllegalStateException("Matrix not invertible");

        return new Matrix2f(
             m11 / d, -m01 / d,
            -m10 / d,  m00 / d
        );
    }

    // ===== Масштаб =====
    public Matrix2f scale(float sx, float sy) {
        return new Matrix2f(
            m00 * sx, m01,
            m10,      m11 * sy
        );
    }

    // ===== Поворот (радианы) =====
    public Matrix2f rotate(float angleRad) {
        float c = (float) Math.cos(angleRad);
        float s = (float) Math.sin(angleRad);

        Matrix2f r = new Matrix2f(
            c, -s,
            s,  c
        );

        return this.mul(r);
    }

    // ===== Вектор из главной диагонали =====
    public Vector2f diagonalVector() {
        return new Vector2f(m00, m11);
    }

    @Override
    public String toString() {
        return "[" + m00 + ", " + m01 + "]\n" +
               "[" + m10 + ", " + m11 + "]";
    }
}
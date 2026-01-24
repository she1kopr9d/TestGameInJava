package org.shelldev.engine.math;

public class Vector2f {
    public final static Vector2f Zero = new Vector2f(0, 0);
    public final static Vector2f One = new Vector2f(1, 1);

    private final float _x;
    private final float _y;

    public Vector2f(float x, float y){
        _x = x;
        _y = y;
    }

    public float X(){
        return  _x;
    }

    public float Y(){
        return  _y;
    }

    public float x(){
        return  _x;
    }

    public float y(){
        return  _y;
    }

    @Override
    public String toString() {
        return "Vector2f{" +
                "x=" + _x +
                ", y=" + _y +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vector2f vector2f = (Vector2f) obj;
        return Float.compare(vector2f._x, _x) == 0 && Float.compare
                (vector2f._y, _y) == 0;
    }

    @Override
    public int hashCode() {
        int result = Float.hashCode(_x);
        result = 31 * result + Float.hashCode(_y);
        return result;
    }

    public Vector2f add(Vector2f other){
        return new Vector2f(this._x + other._x, this._y + other._y);
    }

    public Vector2f subtract(Vector2f other){
        return new Vector2f(this._x - other._x, this._y - other._y);
    }

    public Vector2f sub(Vector2f other){
        return new Vector2f(this._x - other._x, this._y - other._y);
    }

    public Vector2f multiply(float scalar){
        return new Vector2f(this._x * scalar, this._y * scalar);
    }

    public Vector2f mul(float scalar){
        return new Vector2f(this._x * scalar, this._y * scalar);
    }

    public Vector2f mul(Vector2f B){
        return new Vector2f(this._x * B.x(), this._y * B.y());
    }

    public Vector2f lerp(Vector2f B, float t){
        return B.sub(this).multiply(t).add(this);
    }
}

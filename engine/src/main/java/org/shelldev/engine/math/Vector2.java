package org.shelldev.engine.math;

public class Vector2 {
    private final int _x;
    private final int _y;

    public Vector2(int x, int y){
        _x = x;
        _y = y;
    }

    public int X(){
        return  _x;
    }

    public int Y(){
        return  _y;
    }

    public int x(){
        return  _x;
    }

    public int y(){
        return  _y;
    }

    public Vector2f f(){
        return new Vector2f(_x, _y);
    }
}

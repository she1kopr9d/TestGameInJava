package org.shelldev.engine.utils;

public class Color {
    private final float _r;
    private final float _g;
    private final float _b;
    private final float _a;

    public Color(
        float r,
        float g,
        float b,
        float a
    ){
        _r = r;
        _g = g;
        _b = b;
        _a = a;
    }

    public float R(){
        return  _r;
    }

    public float G(){
        return  _g;
    }

    public float B(){
        return  _b;
    }

    public float A(){
        return  _a;
    }
}

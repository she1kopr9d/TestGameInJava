package org.shelldev.engine.graphics;

public class Animation {
    public final Texture[] frames;
    public final float frameDuration;
    public final boolean loop;
    public final String name;

    public Animation(String name, Texture[] frames, float frameDuration, boolean loop) {
        this.name = name;
        this.frames = frames;
        this.frameDuration = frameDuration;
        this.loop = loop;
    }
}
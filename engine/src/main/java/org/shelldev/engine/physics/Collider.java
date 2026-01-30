package org.shelldev.engine.physics;

import org.shelldev.engine.math.Vector2f;
import org.shelldev.engine.utils.Color;
import org.shelldev.engine.world.Component;

public abstract class Collider extends Component {

    protected Vector2f offset = new Vector2f(0, 0);
    protected boolean isTrigger = false;
    protected boolean enabled = true;
    protected Surface surface; // трение поверхности

    public Collider() {
        this.surface = new Surface(0.8f); // дефолтное трение
    }

    public Collider(Vector2f offset) {
        this.offset = offset;
        this.surface = new Surface(0.8f);
    }

    public Collider(Vector2f offset, Surface surface) {
        this.offset = offset;
        this.surface = surface;
    }

    public Vector2f getOffset() { return offset; }
    public void setOffset(Vector2f offset) { this.offset = offset; }

    public boolean isTrigger() { return isTrigger; }
    public void setTrigger(boolean trigger) { this.isTrigger = trigger; }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public Surface getSurface() { return surface; }
    public void setSurface(Surface surface) { this.surface = surface; }

    /** Мировая позиция с учетом offset */
    public Vector2f getWorldPosition() {
        return getEntity().getWorldPosition().add(offset);
    }

    public Vector2f getColliderCenter() {
        return getEntity().getWorldPosition().add(offset);
    }

    public Color getColor() {
        return new Color(0f, 1f, 0f, 1f);
    }
}
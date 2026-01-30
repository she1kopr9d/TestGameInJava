package org.shelldev.engine.utils;

import org.shelldev.engine.fsm.State;
import org.shelldev.engine.graphics.Animation;
import org.shelldev.engine.graphics.Texture;
import org.shelldev.engine.math.Vector2f;
import org.shelldev.engine.physics.BoxCollider;
import org.shelldev.engine.physics.Rigidbody;
import org.shelldev.engine.world.Entity;
import org.shelldev.engine.world.World;
import org.shelldev.engine.world.components.AnimationComponent;
import org.shelldev.engine.world.components.FSMComponent;
import org.shelldev.engine.world.components.Position;
import org.shelldev.engine.world.components.Sprite;

public class ObjectFactory {
    private static Position createPosition(
        Vector2f position,
        Vector2f anchor
    ){
        if (position == null){
            position = Vector2f.Zero;
        }
        if (anchor == null){
            anchor = Vector2f.One.mul(0.5f);
        }
        Position p = new Position(position, anchor);
        return p;
    }

    private static Sprite createSprite(
        Color color,
        Vector2f size,
        int layer
    ){
        if (color == null){
            color = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        }
        if (size == null){
            size = Vector2f.One;
        }
        Sprite s = new Sprite(color, size, layer);
        return s;
    }

    private static BoxCollider createBoxCollider(
        Vector2f size,
        Vector2f anchor
    ){
        if (size == null){
            size = Vector2f.One;
        }
        if (anchor == null){
            anchor = Vector2f.One.mul(0.5f);
        }
        Vector2f offset = new Vector2f(
            - anchor.x() * size.x() + size.x() / 2,
            - anchor.y() * size.y() + size.y() / 2
        );
        BoxCollider bc = new BoxCollider(size.x(), size.y());
        bc.setOffset(offset);
        return bc;
    }

    private static Rigidbody createRigidbody(
        float mass,
        boolean isStatic,
        boolean useGravity
    ){
        Rigidbody rb = new Rigidbody(mass, isStatic);
        rb.setUseGravity(useGravity);
        return rb;
    }

    public class AnimationData{
        public String name;
        public String[] paths;
        public float frameDuration;
        public boolean loop;

        public AnimationData(
            String name,
            String[] paths,
            float frameDuration,
            boolean loop
        ){
            this.name = name;
            this.paths = paths;
            this.frameDuration = frameDuration;
            this.loop = loop;
        }
    }

    private static Animation createAnimation(
        String name,
        String[] paths,
        float frameDuration,
        boolean loop
    ){
        Texture[] textures = new Texture[paths.length];
        for(int i = 0; i < paths.length; i++){
            textures[i] = new Texture(paths[i]);
        }
        Animation animation = new Animation(name, textures, frameDuration, loop);
        return animation;
    }

    private static State createAnimationState(
        AnimationComponent animComp,
        Animation animation
    ){
        State s = new State(
            animation.name,
            e -> {
                animComp.setAnimation(animation);
            }, null
        );
        return s;
    }

    private static FSMComponent createAnimFSM(
        AnimationComponent animComp,
        Animation[] animations
    ){
        FSMComponent fsm = new FSMComponent();
        for (Animation animation : animations){
            fsm.addState(createAnimationState(animComp, animation));
        }
        return fsm;
    }

    public static Entity entityPos(
        Vector2f position,
        Vector2f anchor
    ){
        Entity e = World.Instance.createEntity();
        e.addComponent(createPosition(position, anchor));
        return e;
    }

    public static Entity entitySprite(
        Color color,
        Vector2f size,
        int layer,
        Vector2f position,
        Vector2f anchor
    ){
        Entity e = entityPos(position, anchor);
        e.addComponent(createSprite(color, size, layer));
        return e;
    }

    public static Entity entityWithShader(
        String fragPath,
        String vertPath,
        Color color,
        Vector2f size,
        int layer,
        Vector2f position,
        Vector2f anchor
    ){
        Entity e = entitySprite(color, size, layer, position, anchor);
        Sprite s = e.getComponent(Sprite.class);
        if (fragPath == null){
            fragPath = Sprite.DefaultFragmentShaderPath;
        }
        if (vertPath == null){
            vertPath = Sprite.DefaultVertexShaderPath;
        }
        s.loadShaderProgram(
            fragPath,
            vertPath
        );
        return e;
    }

    public static Entity entityBoxCollider(
        String fragPath,
        String vertPath,
        Color color,
        Vector2f size,
        int layer,
        Vector2f position,
        Vector2f anchor
    ){
        Entity e = entityWithShader(
            fragPath,
            vertPath,
            color,
            size,
            layer,
            position,
            anchor
        );
        BoxCollider bc = createBoxCollider(size, anchor);
        e.addComponent(bc);
        return e;
    }

    public static Entity entityRigidbody(
        float mass,
        boolean isStatic,
        boolean useGravity,
        String fragPath,
        String vertPath,
        Color color,
        Vector2f size,
        int layer,
        Vector2f position,
        Vector2f anchor
    ){
        Entity e = entityBoxCollider(
            fragPath,
            vertPath,
            color,
            size,
            layer,
            position,
            anchor
        );
        Rigidbody rb = createRigidbody(mass, isStatic, useGravity);
        e.addComponent(rb);
        return e;
    }
}

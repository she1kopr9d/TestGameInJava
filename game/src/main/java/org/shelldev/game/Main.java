package org.shelldev.game;

import org.shelldev.engine.Game;
import org.shelldev.engine.GameConfig;
import org.shelldev.engine.fsm.State;
import org.shelldev.engine.graphics.Animation;
import org.shelldev.engine.graphics.Shader;
import org.shelldev.engine.graphics.Texture;
import org.shelldev.engine.math.Vector2;
import org.shelldev.engine.math.Vector2f;
import org.shelldev.engine.physics.BoxCollider;
import org.shelldev.engine.physics.Rigidbody;
import org.shelldev.engine.utils.Color;
import org.shelldev.engine.utils.MoveInput;
import org.shelldev.engine.utils.ObjectFactory;
import org.shelldev.engine.world.Entity;
import org.shelldev.engine.world.World;
import org.shelldev.engine.world.components.AnimationComponent;
import org.shelldev.engine.world.components.Camera;
import org.shelldev.engine.world.components.FSMComponent;
import org.shelldev.engine.world.components.Position;
import org.shelldev.engine.world.components.Sprite;
import org.shelldev.game.scripts.player.controller.ControllerComponent;
import org.shelldev.game.scripts.player.controller.DebugButtonsComponent;


public class Main {
    public static Entity createBlock(
        Vector2f position,
        Color color,
        Vector2f size,
        int layer,
        Vector2f anchor,
        String fragmentShaderPath
    ){
        Entity e = World.Instance.createEntity();
        Position p = new Position(
            position != null ? position : Vector2f.Zero,
            anchor != null ? anchor : Vector2f.One.mul(0.5f)
        );
        Sprite s = new Sprite(
            color != null ? color : new Color(1.0f, 1.0f, 1.0f, 1.0f),
            size != null ? size : new Vector2f(100.0f, 100.0f),
            layer
        );
        e.addComponent(p);
        e.addComponent(s);
        if (fragmentShaderPath != null){
            try {
                Shader shader = new Shader(
                    Sprite.DefaultVertexShaderPath,
                    fragmentShaderPath
                );
                s.loadShaderProgram(shader);
            } catch (Exception error) { }
        }
        return e;
    }
    public static void main(String[] args) {
        GameConfig config = new GameConfig(
            "Game",
            new Vector2(1920, 1080),
            new Color(0.5f, 0.5f, 1.0f, 1.0f),
            true
        );
        Game.init(config);
        try {
            {
                ObjectFactory.entityRigidbody(
                    1,
                    false,
                    true,
                    "shaders/fragment2.glsl",
                    null,
                    null,
                    Vector2f.One.mul(64),
                    1,
                    null,
                    null
                ).setParent(World.Instance.getWorldObject());
            }
            {
                ObjectFactory.entityRigidbody(
                    1,
                    true,
                    true,
                    "shaders/fragment2.glsl",
                    null,
                    null,
                    new Vector2f(1024*3, 32),
                    1,
                    new Vector2f(0, -48),
                    null
                ).setParent(World.Instance.getWorldObject());
            }
            Entity entity = World.Instance.createEntity();
            entity.setParent(World.Instance.getWorldObject());
            Position position = new Position(new Vector2f(0f, 64+32f), null);
            entity.addComponent(position);
            Animation idleAnimation = new Animation(
                "Idle", new Texture[]{
                    new Texture("textures/player/Idle/CuteCatIdle1.png"),
                    new Texture("textures/player/Idle/CuteCatIdle2.png"),
                    new Texture("textures/player/Idle/CuteCatIdle3.png"),
                    new Texture("textures/player/Idle/CuteCatIdle4.png"),
                    new Texture("textures/player/Idle/CuteCatIdle5.png"),
                    new Texture("textures/player/Idle/CuteCatIdle6.png")
                }, 0.2f, true);
            Animation walkFrontAnimation = new Animation(
                "WalkFront", new Texture[]{
                    new Texture("textures/player/WalkFront/CuteCatWalkFront1.png"),
                    new Texture("textures/player/WalkFront/CuteCatWalkFront2.png"),
                    new Texture("textures/player/WalkFront/CuteCatWalkFront3.png"),
                    new Texture("textures/player/WalkFront/CuteCatWalkFront4.png"),
                    new Texture("textures/player/WalkFront/CuteCatWalkFront5.png"),
                    new Texture("textures/player/WalkFront/CuteCatWalkFront6.png"),
                    new Texture("textures/player/WalkFront/CuteCatWalkFront7.png"),
                    new Texture("textures/player/WalkFront/CuteCatWalkFront8.png")
                }, 0.1f, true);
            Animation walkBackAnimation = new Animation(
                "WalkBack", new Texture[]{
                    new Texture("textures/player/WalkBack/CuteCatWalkBack1.png"),
                    new Texture("textures/player/WalkBack/CuteCatWalkBack2.png"),
                    new Texture("textures/player/WalkBack/CuteCatWalkBack3.png"),
                    new Texture("textures/player/WalkBack/CuteCatWalkBack4.png"),
                    new Texture("textures/player/WalkBack/CuteCatWalkBack5.png"),
                    new Texture("textures/player/WalkBack/CuteCatWalkBack6.png"),
                    new Texture("textures/player/WalkBack/CuteCatWalkBack7.png"),
                    new Texture("textures/player/WalkBack/CuteCatWalkBack8.png")
                }, 0.1f, true);
            Animation walkLeftAnimation = new Animation(
                "WalkLeft", new Texture[]{
                    new Texture("textures/player/WalkLeft/CuteCatWalkLeft1.png"),
                    new Texture("textures/player/WalkLeft/CuteCatWalkLeft2.png"),
                    new Texture("textures/player/WalkLeft/CuteCatWalkLeft3.png"),
                    new Texture("textures/player/WalkLeft/CuteCatWalkLeft4.png"),
                    new Texture("textures/player/WalkLeft/CuteCatWalkLeft5.png"),
                    new Texture("textures/player/WalkLeft/CuteCatWalkLeft6.png"),
                    new Texture("textures/player/WalkLeft/CuteCatWalkLeft7.png"),
                    new Texture("textures/player/WalkLeft/CuteCatWalkLeft8.png"),
                }, 0.1f, true);
            Animation walkRightAnimation = new Animation(
                "WalkRight", new Texture[]{
                    new Texture("textures/player/WalkRight/CuteCatWalkRight1.png"),
                    new Texture("textures/player/WalkRight/CuteCatWalkRight2.png"),
                    new Texture("textures/player/WalkRight/CuteCatWalkRight3.png"),
                    new Texture("textures/player/WalkRight/CuteCatWalkRight4.png"),
                    new Texture("textures/player/WalkRight/CuteCatWalkRight5.png"),
                    new Texture("textures/player/WalkRight/CuteCatWalkRight6.png"),
                    new Texture("textures/player/WalkRight/CuteCatWalkRight7.png"),
                    new Texture("textures/player/WalkRight/CuteCatWalkRight8.png"),
                }, 0.1f, true);
            Texture texture = idleAnimation.frames[0];
            Sprite sprite = new Sprite(
                new Color(1.0f, 1.0f, 1.0f, 1.0f),
                new Vector2f(texture.width, texture.height),
                2);
            sprite.setScale(Vector2f.One.mul(4));
            entity.addComponent(sprite);
            sprite.setTexture(texture);
            AnimationComponent animComp = new AnimationComponent(idleAnimation);
            entity.addComponent(animComp);
            entity.addComponent(new ControllerComponent());
            Camera cam = new Camera();
            entity.addComponent(cam);

            FSMComponent fsm = new FSMComponent();
            entity.addComponent(fsm);
            State idle = new State(
                "Idle",
                e -> {
                    animComp.setAnimation(idleAnimation);
                }, null);
            State walkingBack = new State(
                "WalkingBack",
                e -> {
                    animComp.setAnimation(walkBackAnimation);
                }, null);
            State walkingFront = new State(
                "WalkingFront",
                e -> {
                    animComp.setAnimation(walkFrontAnimation);
                }, null);
            State walkingLeft = new State(
                "WalkingLeft",
                e -> {
                    animComp.setAnimation(walkLeftAnimation);
                }, null);
            State walkingRight = new State(
                "WalkingRight",
                e -> {
                    animComp.setAnimation(walkRightAnimation);
                }, null);

            fsm.addState(idle);
            fsm.addState(walkingBack);
            fsm.addState(walkingFront);
            fsm.addState(walkingLeft);
            fsm.addState(walkingRight);

            fsm.addTransition(idle, walkingFront, e -> {
                return MoveInput.GetMoveInput().y() < -0.8f;
            });
            fsm.addTransition(walkingFront, idle, e -> {
                return MoveInput.GetMoveInput().y() == 0f;
            });
            fsm.addTransition(idle, walkingBack, e -> {
                return MoveInput.GetMoveInput().y() > 0.8f;
            });
            fsm.addTransition(walkingBack, idle, e -> {
                return MoveInput.GetMoveInput().y() == 0f;
            });
            fsm.addTransition(idle, walkingLeft, e -> {
                return MoveInput.GetMoveInput().x() < -0.7f;
            });
            fsm.addTransition(walkingLeft, idle, e -> {
                return MoveInput.GetMoveInput().x() == 0f;
            });
            fsm.addTransition(idle, walkingRight, e -> {
                return MoveInput.GetMoveInput().x() > 0.7f;
            });
            fsm.addTransition(walkingRight, idle, e -> {
                return MoveInput.GetMoveInput().x() == 0f;
            });
            
            fsm.setInitialState(idle, entity);

            BoxCollider bCollider = new BoxCollider(14*4, 128);
            entity.addComponent(bCollider);
            Rigidbody rb = new Rigidbody(128, false);
            entity.addComponent(rb);
            rb.setUseGravity(true);
            entity.addComponent(new DebugButtonsComponent());

        } catch (Exception e) { }
        
        Game.start();
    }
}
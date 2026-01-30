package org.shelldev.engine.world.systems;

import org.shelldev.engine.GameConfig;
import org.shelldev.engine.graphics.Render;
import org.shelldev.engine.world.Component;
import org.shelldev.engine.world.System;
import org.shelldev.engine.world.SystemManager;

public class RenderSystem extends System<Component>{
    public static final RenderSystem Instance = new RenderSystem();

    static {
        SystemManager.registerSystem(Instance);
    }

    @Override
    public void render(){
        Render.getInstance().render(
            CameraSystem.Instance.getCameraOffset(),
            GameConfig.getInstance().getScreenSize().f(),
            TimeSystem.Instance.getTime()
        );
    }
}

package org.shelldev.engine;

import org.shelldev.engine.world.SystemManager;

public class Game {
    private static GameConfig _config;
    public static void init(GameConfig config){
        _config = config;
        Engine.Instance.initWindow(_config.getScreenTitle(), _config.getScreenSize().x(), _config.getScreenSize().y());
        Engine.Instance.setBGColor(_config.getBGColor());
    }
    public static void start(){
        SystemManager.loadAllSystems();
        SystemManager.printRegisteredSystems();
        SystemManager.createSystems();
        SystemManager.initSystems();
        Engine.Instance.renderLoop(() -> {
            Engine.Instance.pollEvents();
            SystemManager.updateSystems();
        });
        SystemManager.removeSystems();
    }
}

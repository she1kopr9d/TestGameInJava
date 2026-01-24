package org.shelldev.engine;

import org.shelldev.engine.math.Vector2;
import org.shelldev.engine.utils.Color;

public class GameConfig {
    private static GameConfig instance;

    private Vector2 _screenSize;
    private String _screenTitle;
    private Color _bgColor;

    public GameConfig(String screenTitle, Vector2 screenSize, Color bgColor) {
        _screenSize = screenSize;
        _screenTitle = screenTitle;
        instance = this;
        _bgColor = bgColor;
    }

    public static GameConfig getInstance() {
        return instance;
    }

    public Vector2 getScreenSize() {
        return _screenSize;
    }

    public void setScreenSize(Vector2 screenSize) {
        _screenSize = screenSize;
    }

    public String getScreenTitle() {
        return _screenTitle;
    }

    public void setScreenTitle(String screenTitle) {
        _screenTitle = screenTitle;
    }

    public Color getBGColor(){
        return _bgColor;
    }

    public void setBGColor(Color bgColor){
        _bgColor = bgColor;
    }
}
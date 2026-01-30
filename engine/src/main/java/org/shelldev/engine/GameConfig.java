package org.shelldev.engine;

import org.shelldev.engine.math.Vector2;
import org.shelldev.engine.utils.Color;

public class GameConfig {
    private static GameConfig instance;

    private Vector2 _screenSize;
    private String _screenTitle;
    private Color _bgColor;
    private boolean _isDebug = false;
    private float _gravityScale = 128f;

    public GameConfig(String screenTitle, Vector2 screenSize, Color bgColor, boolean isDebug) {
        _screenSize = screenSize;
        _screenTitle = screenTitle;
        instance = this;
        _bgColor = bgColor;
        _isDebug = isDebug;
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

    public boolean isDebug(){
        return _isDebug;
    }

    public void setDebugMode(boolean debugState){
        _isDebug = debugState;
    }

    public void setGravityScale(float scale){
        _gravityScale = scale;
    }

    public float getGravityScale(){
        return _gravityScale;
    }
}
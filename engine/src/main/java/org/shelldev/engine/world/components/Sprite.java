package org.shelldev.engine.world.components;

import java.io.IOException;

import org.shelldev.engine.graphics.Shader;
import org.shelldev.engine.graphics.Texture;
import org.shelldev.engine.math.Vector2f;
import org.shelldev.engine.utils.Color;
import org.shelldev.engine.world.Component;

public class Sprite extends Component {
    public static final String DefaultVertexShaderPath = "shaders/vertex02.glsl";
    public static final String DefaultFragmentShaderPath = "shaders/fragment02.glsl";

    private Color _color;
    private Vector2f _size;
    private Vector2f _scale;     // Множитель размера
    private Vector2f _anchor;
    private Texture _texture;
    private int _layer = 0;

    private Shader _shader;

    // Конструктор с обязательными параметрами
    public Sprite(Color color, Vector2f size, int layer, Vector2f anchor) {
        _color = color != null ? color : new Color(1f, 1f, 1f, 1f);
        _size = size != null ? size : Vector2f.One;
        _layer = layer;
        _anchor = anchor != null ? anchor : new Vector2f(0.5f, 0.5f);
        _scale = Vector2f.One; // по умолчанию 1
    }

    // Геттеры и сеттеры
    public Vector2f getAnchor() {
        return _anchor != null ? _anchor : new Vector2f(0.5f, 0.5f);
    }

    public void setAnchor(Vector2f anchor) {
        _anchor = anchor;
    }

    public Color getColor() {
        return _color != null ? _color : new Color(1f, 1f, 1f, 1f);
    }

    public void setColor(Color color) {
        _color = color;
    }

    public Vector2f getSize() {
        return _size != null ? _size.mul(_scale) : Vector2f.One.mul(_scale);
    }

    public void setSize(Vector2f size) {
        _size = size != null ? size : Vector2f.One;
    }

    public Vector2f getScale() {
        return _scale != null ? _scale : Vector2f.One;
    }

    public void setScale(Vector2f scale) {
        _scale = scale != null ? scale : Vector2f.One;
    }

    public int getLayer() {
        return _layer;
    }

    public void setLayer(int layer) {
        _layer = layer;
    }

    public Texture getTexture() {
        return _texture;
    }

    public void setTexture(Texture texture) {
        _texture = texture;
    }

    // Шейдеры
    public void loadShaderProgram(String fragmentShaderPath, String vertexShaderPath) {
        try {
            loadShaderProgram(new Shader(vertexShaderPath, fragmentShaderPath));
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize ShaderProgram", e);
        }
    }

    public void loadShaderProgram(Shader shader) {
        _shader = shader;
    }

    public Shader getShader() {
        if (_shader == null) {
            loadShaderProgram(DefaultFragmentShaderPath, DefaultVertexShaderPath);
        }
        return _shader;
    }
}
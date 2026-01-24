package org.shelldev.engine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import org.shelldev.engine.utils.Color;

public class Engine {
    public static final Engine Instance = new Engine();

    private long window;
    private int _deltaTime;
    private Color _bgColor;

    /**
     * Создание окна и инициализация OpenGL
     */
    public Engine(){
        if (Engine.Instance != null){
            throw new RuntimeException("Instance is not null");
        }
    }

    public void initWindow(String title, int width, int height) {
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("GLFW init failed");
        }

        // Настройка контекста для macOS
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);

        window = GLFW.glfwCreateWindow(width, height, title, 0, 0);
        if (window == 0) throw new RuntimeException("Failed to create window");

        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();
    }

    /**
     * Проверка, нужно ли закрыть окно
     */
    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(window);
    }

    /**
     * Очистка экрана (подготовка к новому кадру)
     */
    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(_bgColor.R(), _bgColor.G(), _bgColor.B(), _bgColor.A());
    }

    /**
     * Замена буферов
     */
    public void swapBuffers() {
        GLFW.glfwSwapBuffers(window);
    }

    /**
     * Обработка событий (клавиатура, мышь)
     */
    public void pollEvents() {
        GLFW.glfwPollEvents();
    }

    /**
     * Завершение работы движка
     */
    public void terminate() {
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }

    /**
     * Цикл рендеринга с кастомной функцией отрисовки
     */
    public void renderLoop(Runnable renderFunction) {
        while (!shouldClose()) {
            _deltaTime++;
            clear();
            renderFunction.run();
            swapBuffers();
            pollEvents();
        }
        terminate();
    }

    public int getDeltaTime() {
        return _deltaTime;
    }

    public long getWindow() {
        return window;
    }

    public Color getBGColor(){
        return _bgColor;
    }

    public void setBGColor(Color bgColor){
        _bgColor = bgColor;
    }
}
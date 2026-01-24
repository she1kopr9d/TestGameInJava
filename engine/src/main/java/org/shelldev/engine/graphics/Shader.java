package org.shelldev.engine.graphics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import org.lwjgl.opengl.GL20;

public class Shader {
    private final int programId;

    public Shader(String vertexPath, String fragmentPath) throws IOException {
        String vertexCode = readResource(vertexPath);
        String fragmentCode = readResource(fragmentPath);

        int vertexShader = compileShader(GL20.GL_VERTEX_SHADER, vertexCode);
        int fragmentShader = compileShader(GL20.GL_FRAGMENT_SHADER, fragmentCode);

        programId = GL20.glCreateProgram();
        GL20.glAttachShader(programId, vertexShader);
        GL20.glAttachShader(programId, fragmentShader);
        GL20.glLinkProgram(programId);

        if (GL20.glGetProgrami(programId, GL20.GL_LINK_STATUS) == 0) {
            throw new RuntimeException("Error linking shader program: " + GL20.glGetProgramInfoLog(programId));
        }

        GL20.glDetachShader(programId, vertexShader);
        GL20.glDetachShader(programId, fragmentShader);
        GL20.glDeleteShader(vertexShader);
        GL20.glDeleteShader(fragmentShader);
    }

    private String readResource(String path) throws IOException {
        InputStream in = getClass().getClassLoader().getResourceAsStream(path);
        if (in == null) throw new IOException("Resource not found: " + path);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

    private int compileShader(int type, String code) {
        int shader = GL20.glCreateShader(type);
        GL20.glShaderSource(shader, code);
        GL20.glCompileShader(shader);

        if (GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) == 0) {
            throw new RuntimeException("Error compiling shader: " + GL20.glGetShaderInfoLog(shader));
        }

        return shader;
    }

    public void bind() {
        GL20.glUseProgram(programId);
    }

    public void unbind() {
        GL20.glUseProgram(0);
    }

    public void cleanup() {
        GL20.glDeleteProgram(programId);
    }
    
    public int getProgramId() {
        return programId;
    }
}

#version 150

in vec2 position;

uniform vec2 uCameraPosition;
uniform vec2 uScreenSize;

void main() {
    // Переводим мировые координаты в OpenGL координаты (-1..1)
    vec2 pos = (position - uCameraPosition) / uScreenSize * 2.0;
    gl_Position = vec4(pos, 0.0, 1.0);
}
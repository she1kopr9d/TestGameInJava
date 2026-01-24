#version 150

in vec2 position;
out vec2 vUV;

uniform vec2 offset;
uniform vec2 size;
uniform vec2 screenSize;

void main() {
    vec2 normalizedOffset = offset / screenSize;
    vec2 normalizedSize   = size / screenSize;

    vec2 pos = position * normalizedSize + normalizedOffset;

    // position ожидается в диапазоне 0..1
    vUV = position;

    gl_Position = vec4(pos * 2.0 - 1.0, 0.0, 1.0);
}
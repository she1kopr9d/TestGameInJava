#version 150

in vec2 vUV;
in vec4 fColor;
in float fTime;

out vec4 fragColor;

uniform sampler2D uTexture;
uniform int hasTexture;

void main() {
    vec2 uv = vUV;
    // Умножаем на цвет спрайта
    fragColor = vec4(uv.x, uv.y, 0.0, 1.0);
}
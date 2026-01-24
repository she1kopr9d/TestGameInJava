#version 150

in vec2 vUV;
out vec4 fragColor;

uniform vec4 color;        // теперь vec4, а не vec3
uniform sampler2D uTexture;
uniform int hasTexture;

void main() {
    if (hasTexture == 1) {
        vec4 tex = texture(uTexture, vUV);

        // Умножаем ВСЁ, включая альфу
        fragColor = tex * color;
    } else {
        fragColor = color;
    }

    // Очень важно для прозрачности!
    if (fragColor.a < 0.01)
        discard;
}
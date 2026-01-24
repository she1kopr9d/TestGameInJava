#version 150

in vec2 vUV;
out vec4 fragColor;

uniform vec4 color;
uniform sampler2D uTexture;
uniform int hasTexture;
uniform vec2 offset;
uniform vec2 size;
uniform vec2 screenSize;
uniform float time;

void main() {
    vec2 uv = vUV;

    // Жидкое дрожание по волнам
    float waveX = sin(uv.y * 10.0 + time * 3.0) * 0.03;
    float waveY = cos(uv.x * 12.0 - time * 2.5) * 0.03;
    uv += vec2(waveX, waveY);

    // Легкая пульсация альфы
    float alphaPulse = 0.7 + 0.3 * sin(time * 5.0 + uv.x * 5.0 + uv.y * 5.0);

    // Немного шума для «живости»
    float noise = sin((uv.x + uv.y + time * 4.0) * 20.0) * 0.02;
    uv += vec2(noise);

    // Цвет текстуры или белый, если текстуры нет
    vec4 texColor = hasTexture == 1 ? texture(uTexture, uv) : vec4(1.0);

    // Финальный цвет: текстура * основной цвет * пульс альфы
    fragColor = texColor * color;
    fragColor.a *= alphaPulse;

    // Альфа-тест
    if (fragColor.a < 0.01) discard;
}
#version 150

in vec2 vUV;
out vec4 fragColor;

uniform vec4 color;        // основной цвет
uniform sampler2D uTexture;
uniform int hasTexture;
uniform vec2 offset;       // позиция спрайта на экране
uniform vec2 size;         // размер спрайта
uniform vec2 screenSize;   // размер экрана
uniform float time;        // время для анимации

void main() {
    // Нормализуем координаты на спрайте 0..1
    vec2 uv = vUV;

    // Сдвиг UV для пульсации волной
    float wave = sin((uv.x + uv.y + time) * 10.0) * 0.03;
    uv.y += wave;

    // Радужный цвет по координатам и времени
    vec3 rainbow = 0.5 + 0.5 * cos(time + uv.xyx * 6.2831 + vec3(0, 2, 4));

    // Берем текстуру если есть
    vec4 texColor = hasTexture == 1 ? texture(uTexture, uv) : vec4(1.0);

    // Смешиваем текстуру с радужной анимацией и основным цветом
    fragColor = texColor * vec4(rainbow, 1.0) * color;

    // Альфа-тест для прозрачности
    if (fragColor.a < 0.01)
        discard;
}
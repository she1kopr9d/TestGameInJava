#version 150

in vec2 position;       // локальные координаты квадрата (0..1)
out vec2 vPosition;
uniform vec2 offset;    // в пикселях
uniform vec2 size;      // в пикселях
uniform vec2 screenSize; // ширина и высота окна в пикселях

void main() {
    // нормализуем пиксели в диапазон 0..1
    vec2 normalizedOffset = offset / screenSize;
    vec2 normalizedSize   = size / screenSize;

    // позиция квадрата в нормализованных координатах
    vec2 pos = position * normalizedSize + normalizedOffset;
    vPosition = pos;
    gl_Position = vec4(pos, 0.0, 1.0);
}
#version 150

// Данные от вершинного шейдера
in vec2 vUV;
in vec2 fObjectPosition;
in vec2 fAnchor;
in vec2 fCameraPosition;
in vec2 fScreenSize;
in vec2 fSpriteSize;
in float fTime;
in vec4 fColor;

out vec4 fragColor;

// Текстура
uniform sampler2D uTexture;
uniform int hasTexture;

void main() {
    vec4 texColor = vec4(1.0); // если текстуры нет, белый
    if(hasTexture == 1){
        texColor = texture(uTexture, vUV);
    }

    // Просто возвращаем все данные через цвет для проверки
    // fColor * texColor показывает, что шейдер получил uniform'ы
    fragColor = texColor * fColor;
}
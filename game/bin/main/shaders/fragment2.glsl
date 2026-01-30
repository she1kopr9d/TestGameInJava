#version 150

in vec2 vUV;
in vec2 fObjectPosition;
in vec2 fAnchor;
in vec2 fCameraPosition;
in vec2 fScreenSize;
in vec2 fSpriteSize;
in float fTime;
in vec4 fColor;

out vec4 fragColor;

uniform sampler2D uTexture;
uniform int hasTexture;

void main() {
    // Текстура
    vec4 texColor = vec4(1.0);
    if(hasTexture == 1){
        texColor = texture(uTexture, vUV);
    }

    // --- ВОЛНЫ ---
    vec2 uv = vUV;

    // смещаем координаты по времени для анимации
    float wave1 = sin((uv.x + fTime * 0.5) * 10.0) * 0.05;
    float wave2 = cos((uv.y + fTime * 0.7) * 12.0) * 0.03;
    float wave3 = sin((uv.x + uv.y + fTime) * 8.0) * 0.02;

    // суммируем волны
    float wave = wave1 + wave2 + wave3;

    // применяем волны к UV координатам
    uv.y += wave;

    // делаем плавный градиент цвета воды
    vec3 waterColor = vec3(0.2, 0.4, 0.7) + wave * 0.3;

    // комбинируем с текстурой и uniform цветом
    fragColor = vec4(waterColor, 1.0) * texColor * fColor;
}
#version 150

out vec4 fragColor;

// uniform для цвета спрайта
uniform vec3 color;

void main() {
    fragColor = vec4(color, 1.0);
}
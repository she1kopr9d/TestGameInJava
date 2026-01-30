#version 150

in vec2 position;

uniform vec2 uObjectPosition;
uniform vec2 uAnchor;
uniform vec2 uCameraPosition;
uniform vec2 uScreenSize;
uniform vec2 uSpriteSize;
uniform float uTime;
uniform vec4 uColor;
out vec2 vUV;
out vec2 fObjectPosition;
out vec2 fAnchor;
out vec2 fCameraPosition;
out vec2 fScreenSize;
out vec2 fSpriteSize;
out float fTime;
out vec4 fColor;

void main() {
    vUV = position;
    fObjectPosition = uObjectPosition;
    fAnchor = uAnchor;
    fCameraPosition = uCameraPosition;
    fScreenSize = uScreenSize;
    fSpriteSize = uSpriteSize;
    fTime = uTime;
    fColor = uColor;
    vec2 worldPos = uObjectPosition - uSpriteSize * uAnchor + position * uSpriteSize;
    vec2 ndc = 2.0 * (worldPos - uCameraPosition) / uScreenSize;
    gl_Position = vec4(ndc, 0.0, 1.0);
}
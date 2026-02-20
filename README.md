# DrakesMotd

## Qué es
MOTD dinámico con estados (`LIVE`, `BETA`, `MAINTENANCE`) e íconos auto-resize.

## Arquitectura
- `MotdManager` maneja el ping, el estado y el icono.
- Estado definido en `motd.yml`.

## Hecho
- Listener `ServerListPingEvent`.
- MiniMessage + hex/gradientes.
- Íconos dinámicos desde `plugins/DrakesMotd/icons/`.
- Auto-resize a 64x64 usando `Graphics2D`.

## Falta
- Rotación de MOTD por tiempo dentro de un mismo estado (opcional).
- Caché por estado + timer para iconos si se cambian en caliente.

## Configuración
- `motd.yml` con comentarios in-line.

## Dependencias
- Paper 1.20.6
- Java 21

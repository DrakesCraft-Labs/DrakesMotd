# DrakesMotd

Plugin de imagen publica del servidor, extraido del modulo `drakesmotd` del antiguo `DrakesCore`.

## Objetivo
Controlar el MOTD y el icono del server list segun estado operativo del servidor.

## Que hace hoy
- Intercepta `ServerListPingEvent`.
- Renderiza MOTD con MiniMessage (incluye hex/gradients).
- Maneja estados: `LIVE`, `BETA`, `MAINTENANCE`.
- Rotacion opcional de frames MOTD por estado (`rotation.*-frames`).
- Carga iconos desde `plugins/DrakesMotd/icons/`.
- Si el PNG no es 64x64, lo redimensiona automaticamente con `Graphics2D`.
- Comando admin `/drakesmotd` para `reload` y cambio de `state` en caliente.

## Configuracion
- `src/main/resources/motd.yml`
- Estado activo en `state`.
- Textos por estado en `motd.live|beta|maintenance`.

## Dependencias
- Paper 1.20.6
- Java 21

## Pendiente real
- Integracion de placeholders de estado con otros plugins de red.

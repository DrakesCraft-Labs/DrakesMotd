# DrakesMotd

Plugin de imagen publica del servidor, extraido del modulo `drakesmotd` del antiguo `DrakesCore`.

## Objetivo
Controlar el MOTD y el icono del server list segun estado operativo del servidor.

## Que hace hoy
- Intercepta `ServerListPingEvent`.
- Renderiza MOTD con MiniMessage (incluye hex/gradients).
- Maneja estados: `LIVE`, `BETA`, `MAINTENANCE`.
- Carga iconos desde `plugins/DrakesMotd/icons/`.
- Si el PNG no es 64x64, lo redimensiona automaticamente con `Graphics2D`.

## Configuracion
- `src/main/resources/motd.yml`
- Estado activo en `state`.
- Textos por estado en `motd.live|beta|maintenance`.

## Dependencias
- Paper 1.20.6
- Java 21

## Pendiente real
- Comando `/drakesmotd reload` para recargar sin reinicio.
- Rotacion temporal de lineas MOTD dentro del mismo estado.
- Estrategia de invalidacion de cache de iconos en caliente.

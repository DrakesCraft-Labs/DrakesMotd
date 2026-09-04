<p align="center">
  <img src="https://raw.githubusercontent.com/DrakesCraft-Labs/DrakesMotd/master/banner.svg" width="100%" alt="DRAKES MOTD animated banner" />
</p>

# DrakesMotd

> ### 🏰 ¡Únete a la Comunidad Oficial de DrakesCraft!
> 
> * 🎮 **IP del Servidor**: `mc.drakescraft.cl` *(Java 1.21.11 & Bedrock)*
> * 💬 **Discord Oficial**: [discord.gg/drakescraft](https://discord.gg/rv3vtXZTk7)
> * 🌐 **Web & Guía**: [web.drakescraft.cl](https://web.drakescraft.cl) — 🛒 **Tienda**: [web.drakescraft.cl/store](https://web.drakescraft.cl/store.html)
> 
> *¡Juega con este addon y más de 80 expansiones optimizadas en vivo en nuestra network de supervivencia técnica!*

---

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


## ⚖️ Upstream Attribution & License / Licencia y Créditos

- **Original Project / Upstream**: Slimefun4 Community Addon.
- **Port & Maintenance**: DrakesCraft Labs team (Compatibility for Paper / Purpur 1.21.11).
- **License**: GPL-3.0 / MIT.
- **Source Code**: [GitHub Repository](https://github.com/DrakesCraft-Labs/DrakesMotd)
- **Support & Issues**: [GitHub Issues](https://github.com/DrakesCraft-Labs/DrakesMotd/issues) | [Discord](https://discord.gg/rv3vtXZTk7)

*This project is an open-source derivative work maintained by DrakesCraft Labs under the terms of its original license. All original assets and concepts belong to their respective creators.*

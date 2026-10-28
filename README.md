# Serious Player Animations - NeoForge 1.21.1 Port

Unofficial NeoForge 1.21.1 port of [Serious Player Animations](https://github.com/McVader34/Serious-Player-Animations) by **McVader**: smooth third-person player animations, powered by playerAnimator.

*[Español abajo](#español)*

## Requirements

- Minecraft 1.21.1
- NeoForge 21.1.x
- [playerAnimator](https://modrinth.com/mod/playeranimator) 2.0.x (NeoForge)
- [Cloth Config](https://modrinth.com/mod/cloth-config) 15.x (NeoForge)

No Fabric API, Forgified Fabric API or Sinytra Connector needed, this is a native NeoForge mod.

## Notes

- **Client-side only.** It does nothing on servers and won't block you from joining one.
- **Config:** Mods menu → Serious Player Animations → Config.
- **Resource packs** made for Serious Player Animations work. Animations are loaded when your player joins a world, so rejoin after changing packs.
- **Compatibility kept:** Not Enough Animations, Carry On, Supplementaries (flute), Immersive Melodies, Sword Blocking.
- **Compatibility removed:** Ultracraft, Old Combat Mod and Trigger Happy (Fabric-only). Paraglider has no NeoForge 1.21.1 version.
- Based on the 1.2.0 alpha for Fabric, so any issues from that alpha may carry over.

## Building

```
./gradlew build
```

The jar is written to `build/libs/`.

## Credits & license

All animations and original code by **McVader** ([original repo](https://github.com/McVader34/Serious-Player-Animations)). This port is not affiliated with or endorsed by the original author. Licensed under MIT — see [LICENSE](LICENSE).

---

## Español

Port no oficial a NeoForge 1.21.1 de [Serious Player Animations](https://github.com/McVader34/Serious-Player-Animations) de **McVader**: animaciones fluidas del jugador en tercera persona, usando playerAnimator.

### Requisitos

- Minecraft 1.21.1
- NeoForge 21.1.x
- [playerAnimator](https://modrinth.com/mod/playeranimator) 2.0.x (NeoForge)
- [Cloth Config](https://modrinth.com/mod/cloth-config) 15.x (NeoForge)

No necesita Fabric API, Forgified Fabric API ni Sinytra Connector: es un mod nativo de NeoForge.

### Notas

- **Solo cliente.** No hace nada en servidores y no te impide entrar a uno.
- **Configuración:** menú Mods → Serious Player Animations → Config.
- **Resource packs** hechos para Serious Player Animations funcionan. Las animaciones se cargan al entrar al mundo, así que vuelve a entrar después de cambiar packs.
- **Compatibilidad que se mantiene:** Not Enough Animations, Carry On, Supplementaries (flauta), Immersive Melodies, Sword Blocking.
- **Compatibilidad eliminada:** Ultracraft, Old Combat Mod y Trigger Happy (solo existen en Fabric). Paraglider no tiene versión NeoForge 1.21.1.
- Basado en la versión alpha 1.2.0 de Fabric, así que puede tener los mismos fallos que esa alpha.

### Compilar

```
./gradlew build
```

El jar queda en `build/libs/`.

### Créditos y licencia

Todas las animaciones y el código original son de **McVader** ([repo original](https://github.com/McVader34/Serious-Player-Animations)). Este port no está afiliado ni respaldado por el autor original. Licencia MIT — ver [LICENSE](LICENSE).

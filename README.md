

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

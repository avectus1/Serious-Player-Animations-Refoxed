package net.mcvader.seriousplayeranimations;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.mcvader.seriousplayeranimations.config.ClientConfig;
import net.mcvader.seriousplayeranimations.config.ConfigWrapper;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(value = SeriousPlayerAnimations.MOD_ID, dist = Dist.CLIENT)
public class SeriousPlayerAnimations {
   public static final String MOD_ID = "seriousplayeranimations";
   public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
   public static ClientConfig config;
   public static boolean PARAGLIDER_COMPAT;
   public static boolean CARRYON_COMPAT;
   public static boolean SUPPLEMENTARIES_COMPAT;
   public static boolean IMMERSIVE_MELODIES_COMPAT;
   public static boolean ULTRACRAFT_COMPAT;
   public static boolean SWORDBLOCKING_COMPAT;
   public static boolean OLDCOMBATMOD_COMPAT;
   public static boolean TRIGGERHAPPY_COMPAT;

   public SeriousPlayerAnimations(ModContainer container) {
      LOGGER.info("Loading up some Serious Animations.");
      AutoConfig.register(ConfigWrapper.class, PartitioningSerializer.wrap(JanksonConfigSerializer::new));
      config = AutoConfig.getConfigHolder(ConfigWrapper.class).getConfig().client;
      ModList mods = ModList.get();
      PARAGLIDER_COMPAT = mods.isLoaded("paraglider");
      CARRYON_COMPAT = mods.isLoaded("carryon");
      SUPPLEMENTARIES_COMPAT = mods.isLoaded("supplementaries");
      IMMERSIVE_MELODIES_COMPAT = mods.isLoaded("immersive_melodies");
      SWORDBLOCKING_COMPAT = mods.isLoaded("swordblocking");
      // Fabric-only mods, never present on NeoForge
      ULTRACRAFT_COMPAT = false;
      OLDCOMBATMOD_COMPAT = false;
      TRIGGERHAPPY_COMPAT = false;
      container.registerExtensionPoint(IConfigScreenFactory.class,
         (IConfigScreenFactory) (modContainer, parent) -> AutoConfig.getConfigScreen(ConfigWrapper.class, parent).get());
   }
}

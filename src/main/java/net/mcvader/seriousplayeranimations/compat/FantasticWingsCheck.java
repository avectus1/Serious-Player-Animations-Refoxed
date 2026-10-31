package net.mcvader.seriousplayeranimations.compat;

import fuzs.fantasticwings.init.ModRegistry;
import net.mcvader.seriousplayeranimations.SeriousPlayerAnimations;
import net.minecraft.world.entity.player.Player;

/**
 * Fantastic Wings compat: while a player is flying with wings, Serious Player Animations
 * steps aside so Fantastic Wings' own flying pose is shown instead of walking/running.
 * Only loaded when Fantastic Wings is installed.
 */
public class FantasticWingsCheck {
   private static boolean broken = false;

   public static boolean isFlying(Player player) {
      if (broken) return false;
      try {
         return ModRegistry.FLIGHT_CAPABILITY.get(player).isFlying();
      } catch (Throwable t) {
         // A future Fantastic Wings version changed its API: turn the compat off instead of crashing.
         broken = true;
         SeriousPlayerAnimations.LOGGER.warn("Fantastic Wings compat disabled, could not read flight state", t);
         return false;
      }
   }
}

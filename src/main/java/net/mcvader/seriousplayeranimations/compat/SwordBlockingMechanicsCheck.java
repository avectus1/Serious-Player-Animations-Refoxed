package net.mcvader.seriousplayeranimations.compat;

import fuzs.swordblockingmechanics.handler.SwordBlockingHandler;
import net.mcvader.seriousplayeranimations.SeriousPlayerAnimations;
import net.minecraft.world.entity.player.Player;

/** Sword Blocking Mechanics compat: lets its blocking pose show on the blocking arm. */
public class SwordBlockingMechanicsCheck {
   private static boolean broken = false;

   public static boolean isBlocking(Player player) {
      if (broken) return false;
      try {
         return SwordBlockingHandler.isActiveItemStackBlocking(player);
      } catch (Throwable t) {
         broken = true;
         SeriousPlayerAnimations.LOGGER.warn("Sword Blocking Mechanics compat disabled, could not read blocking state", t);
         return false;
      }
   }
}

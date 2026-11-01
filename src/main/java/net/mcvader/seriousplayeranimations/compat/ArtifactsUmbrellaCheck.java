package net.mcvader.seriousplayeranimations.compat;

import artifacts.item.UmbrellaItem;
import net.mcvader.seriousplayeranimations.SeriousPlayerAnimations;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;

/** Artifacts compat: the arm holding the umbrella upright keeps Artifacts' raised-arm pose. */
public class ArtifactsUmbrellaCheck {
   private static boolean broken = false;

   public static boolean isHoldingUpright(Player player, InteractionHand hand) {
      if (broken) return false;
      try {
         return UmbrellaItem.isHoldingUmbrellaUpright(player, hand);
      } catch (Throwable t) {
         broken = true;
         SeriousPlayerAnimations.LOGGER.warn("Artifacts umbrella compat disabled, could not read umbrella state", t);
         return false;
      }
   }
}

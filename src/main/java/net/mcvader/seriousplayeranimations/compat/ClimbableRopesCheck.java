package net.mcvader.seriousplayeranimations.compat;

import dev.matejhozlar.climbableropes.client.ClimbAnimationController;
import net.mcvader.seriousplayeranimations.SeriousPlayerAnimations;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

/**
 * Climbable Ropes for Create Aeronautics compat. While a player hangs on a rope the game sees
 * them as "in the air and not moving", which used to trigger the creative flying animation.
 * Only loaded when Climbable Ropes is installed.
 */
public class ClimbableRopesCheck {
   private static boolean broken = false;

   // Climbable Ropes is drawing its own climbing pose (hands and feet on the rope)
   public static boolean hasCustomPose(Player player) {
      if (broken) return false;
      try {
         return ClimbAnimationController.isCustomPoseActive(player);
      } catch (Throwable t) {
         fail(t);
         return false;
      }
   }

   // Hanging on a rope, with or without the Climbable Ropes pose (zipline, or its animation turned off)
   public static boolean isOnRope(Player player) {
      if (broken) return false;
      try {
         Player local = Minecraft.getInstance().player;
         if (local != null && local.getUUID().equals(player.getUUID())) {
            return ClimbAnimationController.isLocalPlayerAttached() || ClimbAnimationController.isCustomPoseActive(player);
         }
         return ClimbAnimationController.isCustomPoseActive(player);
      } catch (Throwable t) {
         fail(t);
         return false;
      }
   }

   private static void fail(Throwable t) {
      broken = true;
      SeriousPlayerAnimations.LOGGER.warn("Climbable Ropes compat disabled, could not read climbing state", t);
   }
}

package net.mcvader.seriousplayeranimations.compat;

import io.github.mortuusars.exposure.world.camera.CameraInHand;
import io.github.mortuusars.exposure.world.item.camera.CameraItem;
import io.github.mortuusars.exposure.world.entity.CameraHolder;
import io.github.mortuusars.exposure.world.entity.CameraOperator;
import net.mcvader.seriousplayeranimations.SeriousPlayerAnimations;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * Exposure compat: while a camera is in use (held to the eye, selfie, on a stand) or held
 * disassembled, Exposure poses the arms itself, so Serious Player Animations leaves them alone.
 */
public class ExposureCheck {
   private static boolean broken = false;

   public static boolean isPosingWithCamera(Player player) {
      if (broken) return false;
      try {
         if (player instanceof CameraOperator operator && operator.getActiveExposureCamera() != null) {
            return true;
         }
         if (player instanceof CameraHolder holder) {
            CameraInHand camera = CameraInHand.find(holder);
            if (camera != null) {
               ItemStack stack = camera.getItemStack();
               return stack.getItem() instanceof CameraItem item && item.isDisassembled(stack);
            }
         }
         return false;
      } catch (Throwable t) {
         broken = true;
         SeriousPlayerAnimations.LOGGER.warn("Exposure compat disabled, could not read camera state", t);
         return false;
      }
   }
}

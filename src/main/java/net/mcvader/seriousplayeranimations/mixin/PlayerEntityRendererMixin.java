package net.mcvader.seriousplayeranimations.mixin;

import net.mcvader.seriousplayeranimations.torsoPosGetter;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.model.HumanoidModel.ArmPose;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({PlayerRenderer.class})
public abstract class PlayerEntityRendererMixin {
   @Shadow
   private static ArmPose getArmPose(AbstractClientPlayer player, InteractionHand hand) {
      return null;
   }

   @Inject(
      method = {"setModelProperties"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/renderer/entity/player/PlayerRenderer;getArmPose(Lnet/minecraft/client/player/AbstractClientPlayer;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/client/model/HumanoidModel$ArmPose;",
         shift = Shift.BY,
         by = 2
      )}
   )
   private void setModelPose(AbstractClientPlayer player, CallbackInfo ci) {
      ((torsoPosGetter)player).armPosMain(getArmPose(player, InteractionHand.MAIN_HAND));
      ((torsoPosGetter)player).armPosOff(getArmPose(player, InteractionHand.OFF_HAND));
   }
}

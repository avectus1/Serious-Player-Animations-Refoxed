package net.mcvader.seriousplayeranimations.mixin;

import net.mcvader.seriousplayeranimations.torsoPosGetter;
import dev.kosmx.playerAnim.core.util.Vec3f;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({CapeLayer.class})
public class CapeRenderMixin {
   float x;
   float y;
   float z;
   float prevX;
   float prevY;
   float prevZ;
   Vec3f Pos;
   Vec3f Rot;

   @Inject(
      method = {"render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;FFFFFF)V"},
      at = {@At(
         value = "INVOKE",
         target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V",
         shift = Shift.AFTER
      )}
   )
   public void render(
      PoseStack matrixStack,
      MultiBufferSource vertexConsumerProvider,
      int i,
      AbstractClientPlayer abstractClientPlayerEntity,
      float f,
      float g,
      float h,
      float j,
      float k,
      float l,
      CallbackInfo ci
   ) {
      this.Pos = ((torsoPosGetter)abstractClientPlayerEntity).getTorsoPos();
      this.x = (Float)this.Pos.getX();
      this.y = (Float)this.Pos.getY();
      this.z = (Float)this.Pos.getZ();
      if (abstractClientPlayerEntity.isCrouching()) {
         this.y -= 4.1F;
      }

      if (this.Pos.getY() != null) {
         matrixStack.translate((double)this.x * 0.0625, (double)this.y * 0.0625, (double)this.z * 0.0625);
         this.prevX = this.x;
         this.prevY = this.y;
         this.prevZ = this.z;
      } else {
         if (abstractClientPlayerEntity.isCrouching()) {
            this.prevY = -4.1F;
         }

         matrixStack.translate((double)this.prevX * 0.0625, (double)this.prevY * 0.0625, (double)this.prevZ * 0.0625);
      }
   }
}

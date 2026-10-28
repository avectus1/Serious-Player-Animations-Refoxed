package net.mcvader.seriousplayeranimations.mixin;

import dev.kosmx.playerAnim.core.util.Vec3f;
import net.mcvader.seriousplayeranimations.torsoPosGetter;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.ElytraModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({ElytraModel.class})
public abstract class ElytraEntityModelMixin<T extends LivingEntity> extends AgeableListModel<T> implements torsoPosGetter {
   float prevY = 0.0F;
   float prevZ = 0.0F;
   @Shadow
   @Final
   private ModelPart rightWing;
   @Shadow
   @Final
   private ModelPart leftWing;

   @Shadow
   protected abstract Iterable<ModelPart> bodyParts();

   @Shadow
   protected abstract Iterable<ModelPart> headParts();

   @Overwrite
   public void setupAnim(T livingEntity, float f, float g, float h, float i, float j) {
      float k = (float) (Math.PI / 12);
      float l = (float) (-Math.PI / 12);
      float m = 0.0F;
      float n = 0.0F;
      float pivZ = 0.0F;
      if (livingEntity.isFallFlying()) {
         float o = 1.0F;
         Vec3 vec3d = livingEntity.getDeltaMovement();
         if (vec3d.y < 0.0) {
            Vec3 vec3d2 = vec3d.normalize();
            o = 1.0F - (float)Math.pow(-vec3d2.y, 1.5);
         }

         k = o * (float) (Math.PI / 9) + (1.0F - o) * k;
         l = o * (float) (-Math.PI / 2) + (1.0F - o) * l;
         pivZ = 0.0F;
      } else if (livingEntity.isCrouching()) {
         k = (float) (Math.PI * 2.0 / 9.0);
         l = (float) (-Math.PI / 4);
         m = 3.0F;
         n = 0.08726646F;
         pivZ = 0.0F;
      } else if (livingEntity.isSprinting() && !livingEntity.isVisuallySwimming()) {
         k = 0.9F;
         l = (float) (-Math.PI / 12);
         m = 0.0F;
         n = 0.1F;
         pivZ = -2.5F;
      }

      if (livingEntity instanceof AbstractClientPlayer abstractClientPlayerEntity) {
         abstractClientPlayerEntity.elytraRotX = abstractClientPlayerEntity.elytraRotX + (k - abstractClientPlayerEntity.elytraRotX) * 0.1F;
         abstractClientPlayerEntity.elytraRotY = abstractClientPlayerEntity.elytraRotY + (n - abstractClientPlayerEntity.elytraRotY) * 0.1F;
         abstractClientPlayerEntity.elytraRotZ = abstractClientPlayerEntity.elytraRotZ + (l - abstractClientPlayerEntity.elytraRotZ) * 0.1F;
         Vec3f Pos = ((torsoPosGetter)abstractClientPlayerEntity).getTorsoPos();
         if (!((Float)Pos.getY()).isNaN()) {
            m = (Float)Pos.getY();
            pivZ = (Float)Pos.getZ();
            this.prevY = (Float)Pos.getY();
            this.prevZ = (Float)Pos.getZ();
         } else {
            m = this.prevY;
            pivZ = this.prevZ;
         }

         this.leftWing.xRot = abstractClientPlayerEntity.elytraRotX;
         this.leftWing.yRot = abstractClientPlayerEntity.elytraRotY;
         this.leftWing.zRot = abstractClientPlayerEntity.elytraRotZ;
      } else {
         this.leftWing.xRot = k;
         this.leftWing.zRot = l;
         this.leftWing.yRot = n;
      }

      this.leftWing.y = (float)((double)m * 0.1);
      this.leftWing.z = (float)((double)pivZ * 0.1);
      this.rightWing.yRot = -this.leftWing.yRot;
      this.rightWing.y = this.leftWing.y;
      this.rightWing.z = this.leftWing.z;
      this.rightWing.xRot = this.leftWing.xRot;
      this.rightWing.zRot = -this.leftWing.zRot;
   }
}

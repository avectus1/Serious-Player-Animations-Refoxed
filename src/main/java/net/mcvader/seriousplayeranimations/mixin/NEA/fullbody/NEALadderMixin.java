package net.mcvader.seriousplayeranimations.mixin.NEA.fullbody;

import net.mcvader.seriousplayeranimations.torsoPosGetter;
import dev.tr7zw.notenoughanimations.access.PlayerData;
import dev.tr7zw.notenoughanimations.versionless.animations.BodyPart;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "dev.tr7zw.notenoughanimations.animations.fullbody.LadderAnimation")
public class NEALadderMixin {
   @Inject(
      method = {"apply"},
      at = {@At("HEAD")}
   )
   public void apply(AbstractClientPlayer entity, PlayerData data, PlayerModel<AbstractClientPlayer> model, BodyPart part, float delta, float tickCounter, CallbackInfo ci) {
      ((torsoPosGetter)entity).disableAnimationB(true);
   }
}

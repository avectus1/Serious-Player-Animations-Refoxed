package net.mcvader.seriousplayeranimations;

import dev.kosmx.playerAnim.core.util.Vec3f;
import net.minecraft.client.model.HumanoidModel.ArmPose;

public interface torsoPosGetter {
   Vec3f getTorsoPos();

   Vec3f getTorsoRotation();

   void disableArms(boolean var1);

   void disableLeftArmB(boolean var1);

   void disableRightArmB(boolean var1);

   void disableMainArmB(boolean var1);

   void disableOffArmB(boolean var1);

   void disableAnimationB(boolean var1);

   void disableOverlayB(boolean var1);

   void armPosMain(ArmPose var1);

   void armPosOff(ArmPose var1);
}

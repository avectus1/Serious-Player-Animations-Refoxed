package net.mcvader.seriousplayeranimations.compat;

import net.elidhan.triggerhappy.TriggerHappyClient;
import net.minecraft.client.model.HumanoidModel.ArmPose;

public class TriggerHappyCheck {
   public static boolean checkOneHanded(ArmPose armPose) {
      return armPose.equals(TriggerHappyClient.ONE_HANDED_GUN);
   }

   public static boolean checkTwoHanded(ArmPose armPose) {
      return armPose.equals(TriggerHappyClient.TWO_HANDED_GUN);
   }
}

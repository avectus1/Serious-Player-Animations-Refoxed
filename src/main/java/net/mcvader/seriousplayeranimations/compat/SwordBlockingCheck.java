package net.mcvader.seriousplayeranimations.compat;

import eu.midnightdust.swordblocking.SwordBlockingClient;
import net.minecraft.world.entity.LivingEntity;

public class SwordBlockingCheck {
   public static boolean check(LivingEntity player) {
      return SwordBlockingClient.isWeaponBlocking(player);
   }
}

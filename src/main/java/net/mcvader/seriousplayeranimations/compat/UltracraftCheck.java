package net.mcvader.seriousplayeranimations.compat;

import absolutelyaya.ultracraft.item.AbstractNailgunItem;
import absolutelyaya.ultracraft.item.AbstractWeaponItem;
import net.minecraft.world.item.Item;

public class UltracraftCheck {
   public static boolean isGenericGun(Item item) {
      if (item instanceof AbstractWeaponItem w && w.shouldAim()) {
         return true;
      }

      return false;
   }

   public static boolean isNailgun(Item item) {
      return item instanceof AbstractNailgunItem;
   }
}

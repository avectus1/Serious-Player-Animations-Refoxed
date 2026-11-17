package net.mcvader.seriousplayeranimations.compat;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import net.mcvader.seriousplayeranimations.SeriousPlayerAnimations;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

/**
 * Create: FireFighting Additions compat. Its extension ladders are not ladder blocks, so the
 * player looked like they were floating (creative flying animation). This finds the ladder the
 * player is holding on to, the same way FireFighting Additions does, and returns the direction
 * to face it. Uses reflection because that part of the mod is private.
 * Only used when FireFighting Additions is installed.
 */
public class ExtensionLadderCheck {
   private static final String CONTROLLER =
      "com.mikoalopex.createfirefightingadd.content.blocks.extension_ladder.ExtensionLadderClimbingController";
   private static boolean broken = false;
   private static boolean ready = false;
   private static MethodHandle findTarget;
   private static MethodHandle isTouching;
   private static MethodHandle mode;
   private static MethodHandle frame;
   private static MethodHandle normal;
   private static MethodHandle anchor;

   private static void init() throws Throwable {
      MethodHandles.Lookup lookup = MethodHandles.lookup();
      Class<?> controller = Class.forName(CONTROLLER);
      Class<?> target = Class.forName(CONTROLLER + "$ClimbTarget");
      Method find = controller.getDeclaredMethod("findTarget", Player.class);
      find.setAccessible(true);
      Method touching = controller.getDeclaredMethod("isTouchingClimbSurface", target);
      touching.setAccessible(true);
      Method modeM = target.getDeclaredMethod("mode");
      modeM.setAccessible(true);
      Method frameM = target.getDeclaredMethod("frame");
      frameM.setAccessible(true);
      Method normalM = frameM.getReturnType().getDeclaredMethod("normal");
      normalM.setAccessible(true);
      Method anchorM = frameM.getReturnType().getDeclaredMethod("anchor");
      anchorM.setAccessible(true);
      anchor = lookup.unreflect(anchorM);
      findTarget = lookup.unreflect(find);
      isTouching = lookup.unreflect(touching);
      mode = lookup.unreflect(modeM);
      frame = lookup.unreflect(frameM);
      normal = lookup.unreflect(normalM);
      ready = true;
      SeriousPlayerAnimations.LOGGER.info("FireFighting Additions compat ready");
   }

   private static boolean loggedActive = false;

   /** Player is held up by an extension ladder without climbing it (on top, at its ends, walking on a flat one). */
   public static final Float TOUCHING_ONLY = Float.NaN;

   /**
    * Returns the body yaw that faces the ladder while the player is climbing an extension ladder,
    * TOUCHING_ONLY (NaN) when the ladder holds the player up some other way (standing on top of it,
    * at its bottom end, walking on a ladder that lies flat or leans a lot), or null when no extension ladder is involved.
    */
   public static Float ladderState(Player player) {
      if (broken) return null;
      try {
         if (!ready) init();
         Object target = findTarget.invoke(player);
         if (target == null) return null;
         if (!loggedActive) {
            loggedActive = true;
            SeriousPlayerAnimations.LOGGER.info("FireFighting Additions compat: extension ladder detected");
         }
         String contactMode = ((Enum<?>)mode.invoke(target)).name();
         if (!"ACTIVE_CLIMB".equals(contactMode) || !(boolean)isTouching.invoke(target)) {
            return TOUCHING_ONLY;
         }
         Object f = frame.invoke(target);
         Vec3 n = (Vec3)normal.invoke(f);
         // Ladder leaning more than about 45 degrees, or lying flat: the player walks on it instead of climbing
         if (Math.abs(n.y) > 0.7) return TOUCHING_ONLY;
         // Face the ladder from whichever side the player is on
         double side = player.position().subtract((Vec3)anchor.invoke(f)).dot(n) >= 0.0 ? -1.0 : 1.0;
         double x = n.x * side;
         double z = n.z * side;
         if (x * x + z * z < 1.0E-6) return player.getVisualRotationYInDegrees();
         return (float)Math.toDegrees(Math.atan2(-x, z));
      } catch (Throwable t) {
         broken = true;
         SeriousPlayerAnimations.LOGGER.warn("FireFighting Additions compat disabled, could not read extension ladder state", t);
         return null;
      }
   }
}

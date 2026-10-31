package net.mcvader.seriousplayeranimations.compat;

import io.github.kosmx.emotes.executor.emotePlayer.IEmotePlayerEntity;
import net.mcvader.seriousplayeranimations.SeriousPlayerAnimations;
import net.minecraft.world.entity.player.Player;

/** Emotecraft compat: Serious Player Animations steps aside while an emote is playing. */
public class EmotecraftCheck {
   private static boolean broken = false;

   public static boolean isPlayingEmote(Player player) {
      if (broken) return false;
      try {
         return player instanceof IEmotePlayerEntity emotePlayer && emotePlayer.isPlayingEmote();
      } catch (Throwable t) {
         broken = true;
         SeriousPlayerAnimations.LOGGER.warn("Emotecraft compat disabled, could not read emote state", t);
         return false;
      }
   }
}

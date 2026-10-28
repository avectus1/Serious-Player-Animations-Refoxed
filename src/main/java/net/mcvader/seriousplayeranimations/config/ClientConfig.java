package net.mcvader.seriousplayeranimations.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Category;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.CollapsibleObject;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.Tooltip;

@Config(
   name = "client"
)
public class ClientConfig implements ConfigData {
   @Tooltip
   public float animationSpeedMultiplier = 1.0F;
   @Category("Animations")
   @CollapsibleObject
   public ClientConfig.AnimationConfig idleStanding = new ClientConfig.AnimationConfig();
   @CollapsibleObject
   public ClientConfig.AnimationConfig walking = new ClientConfig.AnimationConfig();
   @CollapsibleObject
   public ClientConfig.AnimationConfig walkingBackwards = new ClientConfig.AnimationConfig();
   @CollapsibleObject
   public ClientConfig.AnimationConfig running = new ClientConfig.AnimationConfig();
   @CollapsibleObject
   public ClientConfig.AnimationConfig turningStanding = new ClientConfig.AnimationConfig();
   @CollapsibleObject
   public ClientConfig.AnimationConfig idleSneaking = new ClientConfig.AnimationConfig();
   @CollapsibleObject
   public ClientConfig.AnimationConfig walkingSneak = new ClientConfig.AnimationConfig();
   @CollapsibleObject
   public ClientConfig.AnimationConfig walkingSneakBackwards = new ClientConfig.AnimationConfig();
   @CollapsibleObject
   public ClientConfig.AnimationConfig idleCreativeFlying = new ClientConfig.AnimationConfig();
   @CollapsibleObject
   public ClientConfig.AnimationConfig falling = new ClientConfig.AnimationConfig();
   @CollapsibleObject
   public ClientConfig.AnimationConfig idleCrawling = new ClientConfig.AnimationConfig();
   @CollapsibleObject
   public ClientConfig.AnimationConfig crawling = new ClientConfig.AnimationConfig();
   @CollapsibleObject
   public ClientConfig.AnimationConfig crawlingBackwards = new ClientConfig.AnimationConfig();
   @CollapsibleObject
   public ClientConfig.AnimationConfig climbing = new ClientConfig.AnimationConfig();
   @CollapsibleObject
   public ClientConfig.AnimationConfig forwardInWater = new ClientConfig.AnimationConfig();
   @CollapsibleObject
   public ClientConfig.AnimationConfig backwardsInWater = new ClientConfig.AnimationConfig();
   @CollapsibleObject
   public ClientConfig.AnimationConfig upInWater = new ClientConfig.AnimationConfig();
   @CollapsibleObject
   public ClientConfig.AnimationConfig idleInWater = new ClientConfig.AnimationConfig();
   @CollapsibleObject
   public ClientConfig.AnimationConfig swimming = new ClientConfig.AnimationConfig();
   @CollapsibleObject
   public ClientConfig.AnimationConfig minecartIdle = new ClientConfig.AnimationConfig();
   @CollapsibleObject
   public ClientConfig.AnimationConfig horseIdle = new ClientConfig.AnimationConfig();
   @CollapsibleObject
   public ClientConfig.AnimationConfig horseRunning = new ClientConfig.AnimationConfig();
   @CollapsibleObject
   public ClientConfig.AnimationConfig boat = new ClientConfig.AnimationConfig();
   @CollapsibleObject
   public ClientConfig.AnimationConfig elytra = new ClientConfig.AnimationConfig();
   @CollapsibleObject
   public ClientConfig.AnimationConfig swordAttack = new ClientConfig.AnimationConfig();
   @CollapsibleObject
   public ClientConfig.AnimationConfig bow = new ClientConfig.AnimationConfig();
   @CollapsibleObject
   public ClientConfig.AnimationConfig pickaxe = new ClientConfig.AnimationConfig();
   @CollapsibleObject
   public ClientConfig.AnimationConfig axe = new ClientConfig.AnimationConfig();
   @CollapsibleObject
   public ClientConfig.AnimationConfig shovel = new ClientConfig.AnimationConfig();
   @CollapsibleObject
   public ClientConfig.AnimationConfig sleeping = new ClientConfig.AnimationConfig();
   @CollapsibleObject
   public ClientConfig.AnimationConfig eating = new ClientConfig.AnimationConfig();
   @CollapsibleObject
   public ClientConfig.AnimationConfig shield = new ClientConfig.AnimationConfig();
   @CollapsibleObject
   public ClientConfig.AnimationConfig trident = new ClientConfig.AnimationConfig();

   public ClientConfig.AnimationConfig getIdleStanding() {
      return this.idleStanding;
   }

   public ClientConfig.AnimationConfig getWalking() {
      return this.walking;
   }

   public ClientConfig.AnimationConfig getWalkingBackwards() {
      return this.walkingBackwards;
   }

   public ClientConfig.AnimationConfig getRunning() {
      return this.running;
   }

   public ClientConfig.AnimationConfig getTurningStanding() {
      return this.turningStanding;
   }

   public ClientConfig.AnimationConfig getIdleSneaking() {
      return this.idleSneaking;
   }

   public ClientConfig.AnimationConfig getWalkingSneak() {
      return this.walkingSneak;
   }

   public ClientConfig.AnimationConfig getWalkingSneakBackwards() {
      return this.walkingSneakBackwards;
   }

   public ClientConfig.AnimationConfig getIdleCreativeFlying() {
      return this.idleCreativeFlying;
   }

   public ClientConfig.AnimationConfig getFalling() {
      return this.falling;
   }

   public ClientConfig.AnimationConfig getIdleCrawling() {
      return this.idleCrawling;
   }

   public ClientConfig.AnimationConfig getCrawling() {
      return this.crawling;
   }

   public ClientConfig.AnimationConfig getCrawlingBackwards() {
      return this.crawlingBackwards;
   }

   public ClientConfig.AnimationConfig getClimbing() {
      return this.climbing;
   }

   public ClientConfig.AnimationConfig getForwardInWater() {
      return this.forwardInWater;
   }

   public ClientConfig.AnimationConfig getBackwardsInWater() {
      return this.backwardsInWater;
   }

   public ClientConfig.AnimationConfig getUpInWater() {
      return this.upInWater;
   }

   public ClientConfig.AnimationConfig getIdleInWater() {
      return this.idleInWater;
   }

   public ClientConfig.AnimationConfig getSwimming() {
      return this.swimming;
   }

   public ClientConfig.AnimationConfig getMinecartIdle() {
      return this.minecartIdle;
   }

   public ClientConfig.AnimationConfig getHorseIdle() {
      return this.horseIdle;
   }

   public ClientConfig.AnimationConfig getHorseRunning() {
      return this.horseRunning;
   }

   public ClientConfig.AnimationConfig getBoat() {
      return this.boat;
   }

   public ClientConfig.AnimationConfig getElytra() {
      return this.elytra;
   }

   public ClientConfig.AnimationConfig getSwordAttack() {
      return this.swordAttack;
   }

   public ClientConfig.AnimationConfig getBow() {
      return this.bow;
   }

   public ClientConfig.AnimationConfig getPickaxe() {
      return this.pickaxe;
   }

   public ClientConfig.AnimationConfig getAxe() {
      return this.axe;
   }

   public ClientConfig.AnimationConfig getShovel() {
      return this.shovel;
   }

   public ClientConfig.AnimationConfig getSleeping() {
      return this.sleeping;
   }

   public ClientConfig.AnimationConfig getEating() {
      return this.eating;
   }

   public ClientConfig.AnimationConfig getShield() {
      return this.shield;
   }

   public ClientConfig.AnimationConfig getTrident() {
      return this.trident;
   }

   public static class AnimationConfig {
      public boolean enabled = true;
      public float speedMultiplier = 1.0F;
   }

   public static class TestConfig {
      public float x = 0.0F;
      public float y = 0.0F;
      public float z = 0.0F;
      public float pitch = 0.0F;
      public float yaw = 0.0F;
      public float roll = 0.0F;
   }
}

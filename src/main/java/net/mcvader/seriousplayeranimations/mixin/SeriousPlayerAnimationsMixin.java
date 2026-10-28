package net.mcvader.seriousplayeranimations.mixin;

import com.mojang.authlib.GameProfile;
import dev.kosmx.playerAnim.api.TransformType;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.api.layered.modifier.AbstractFadeModifier;
import dev.kosmx.playerAnim.api.layered.modifier.AdjustmentModifier;
import dev.kosmx.playerAnim.api.layered.modifier.MirrorModifier;
import dev.kosmx.playerAnim.api.layered.modifier.SpeedModifier;
import dev.kosmx.playerAnim.api.layered.modifier.AdjustmentModifier.PartModifier;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation.AnimationBuilder;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation.StateCollection;
import dev.kosmx.playerAnim.core.util.Ease;
import dev.kosmx.playerAnim.core.util.Vec3f;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import java.util.Objects;
import java.util.Optional;
import net.mcvader.seriousplayeranimations.IExampleAnimatedPlayer;
import net.mcvader.seriousplayeranimations.SeriousPlayerAnimations;
import net.mcvader.seriousplayeranimations.torsoPosGetter;
import net.mcvader.seriousplayeranimations.compat.CarryOnCheck;
import net.mcvader.seriousplayeranimations.compat.ImmersiveMelodiesItemCheck;
import net.mcvader.seriousplayeranimations.compat.OldCombatModCheck;
import net.mcvader.seriousplayeranimations.compat.SupplementariesFluteCheck;
import net.mcvader.seriousplayeranimations.compat.SwordBlockingCheck;
import net.mcvader.seriousplayeranimations.compat.TriggerHappyCheck;
import net.mcvader.seriousplayeranimations.compat.UltracraftCheck;
import net.mcvader.seriousplayeranimations.config.ClientConfig;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.animal.horse.Donkey;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.animal.horse.Mule;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.animal.horse.ZombieHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.ScaffoldingBlock;
import net.minecraft.world.level.block.WeepingVinesBlock;
import net.minecraft.world.level.block.TwistingVinesBlock;
import net.minecraft.world.level.block.TwistingVinesPlantBlock;
import net.minecraft.world.level.block.WeepingVinesPlantBlock;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.item.BrushItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.client.model.HumanoidModel.ArmPose;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Unique
@Mixin({AbstractClientPlayer.class})
public abstract class SeriousPlayerAnimationsMixin extends Player implements IExampleAnimatedPlayer, torsoPosGetter {
   @Shadow
   @Final
   public ClientLevel clientLevel;
   private final ModifierLayer<IAnimation> modAnimationContainer = new ModifierLayer();
   private final ModifierLayer<IAnimation> modAnimationContainer2 = new ModifierLayer();
   public KeyframeAnimation sword_attack = null;
   public KeyframeAnimation sword_attack2 = null;
   public KeyframeAnimation idle_standing = null;
   public KeyframeAnimation idle_creative_flying = null;
   public KeyframeAnimation idle_creative_flying_item = null;
   public KeyframeAnimation walking = null;
   public KeyframeAnimation walking_backwards = null;
   public KeyframeAnimation running = null;
   public KeyframeAnimation turn_left = null;
   public KeyframeAnimation turn_right = null;
   public KeyframeAnimation idle_sneak = null;
   public KeyframeAnimation walking_sneak = null;
   public KeyframeAnimation walking_sneak_backwards = null;
   public KeyframeAnimation sword_attack_sneak = null;
   public KeyframeAnimation sword_attack_sneak2 = null;
   public KeyframeAnimation falling = null;
   public KeyframeAnimation blank_loop = null;
   public KeyframeAnimation elytra = null;
   public KeyframeAnimation eating_right = null;
   public KeyframeAnimation eating_left = null;
   public KeyframeAnimation eating_right_sneak = null;
   public KeyframeAnimation eating_left_sneak = null;
   public KeyframeAnimation idle_in_water = null;
   public KeyframeAnimation forward_in_water = null;
   public KeyframeAnimation backwards_in_water = null;
   public KeyframeAnimation up_in_water = null;
   public KeyframeAnimation swimming = null;
   public KeyframeAnimation crawling = null;
   public KeyframeAnimation idle_crawling = null;
   public KeyframeAnimation crawling_backwards = null;
   public KeyframeAnimation idle_climbing = null;
   public KeyframeAnimation idle_climbing_sneak = null;
   public KeyframeAnimation climbing = null;
   public KeyframeAnimation climbing_sneak = null;
   public KeyframeAnimation climbing_backwards = null;
   public KeyframeAnimation pickaxe = null;
   public KeyframeAnimation pickaxe_sneak = null;
   public KeyframeAnimation minecart_idle = null;
   public KeyframeAnimation minecart_pickaxe = null;
   public KeyframeAnimation horse_idle = null;
   public KeyframeAnimation horse_running = null;
   public KeyframeAnimation horse_pickaxe = null;
   public KeyframeAnimation boat1 = null;
   public KeyframeAnimation eating = null;
   public KeyframeAnimation bow_idle = null;
   public KeyframeAnimation bow_sneak = null;
   public KeyframeAnimation sleeping = null;
   public KeyframeAnimation axe = null;
   public KeyframeAnimation axe_sneak = null;
   public KeyframeAnimation shovel = null;
   public KeyframeAnimation shovel_sneak = null;
   public KeyframeAnimation paraglider = null;
   public KeyframeAnimation generic_handswing = null;
   public KeyframeAnimation shield = null;
   public KeyframeAnimation shield_sneak = null;
   public KeyframeAnimation trident = null;
   public Vec3f torso2 = new Vec3f(0.0F, 0.0F, 0.0F);
   public Vec3f torsoRotation2 = new Vec3f(0.0F, 0.0F, 0.0F);
   public Vec3f torsoPos = new Vec3f(0.0F, 0.0F, 0.0F);
   public Vec3f torsoRotation = new Vec3f(0.0F, 0.0F, 0.0F);
   public Vec3f zero = new Vec3f(0.0F, 0.0F, 0.0F);
   public KeyframeAnimation currentAnimation = null;
   public KeyframeAnimation currentOverlay = null;
   public KeyframeAnimation prevAnimation = null;
   public AnimationBuilder builder = null;
   public boolean disableArms = false;
   public boolean disableRightArmB = false;
   public boolean disableLeftArmB = false;
   public boolean disableMainArmB = false;
   public boolean disableOffArmB = false;
   public boolean disableAnimationB = false;
   public boolean disableOverlayB = false;
   public ArmPose armPosMain = ArmPose.EMPTY;
   public ArmPose armPosOff = ArmPose.EMPTY;
   int fadeTime = 0;
   int overlayFadeTime = 0;
   float overlayAnimationSpeed = 1.0F;
   int overlayPriority = 0;
   int prevOverlayPriority = 0;
   String currentAnimationId = "";
   String prevAnimationId = "";
   String currentOverlayId = "";
   String prevOverlayId = "";
   boolean modified = false;
   float animationSpeed = 1.0F;
   float byaw = 0.0F;
   float hyaw = 0.0F;
   float vx = 0.0F;
   float vy = 0.0F;
   float vz = 0.0F;
   double moveSpeed = 0.0;
   float turn = 0.0F;
   boolean prevOnGround = false;
   int priority = 0;
   int prevPriority = 0;
   Vec3 lastPos = new Vec3(0.0, 0.0, 0.0);
   boolean swordSeq = true;
   String modifyId = "";
   String prevModifyId = "";
   String blockStateString = "";
   String boatString = "";
   int flychecker = 0;
   double vyfly = 0.0;
   boolean isMovingBackwards = false;
   Item activeItem = null;
   boolean crouched = false;
   double bodyYawRadians = 0.0;
   int animationTick = 0;
   InteractionHand rightHand = InteractionHand.MAIN_HAND;
   InteractionHand leftHand = InteractionHand.OFF_HAND;
   Vec3 pos;
   float prevbyaw = 0.0F;
   Item item;
   AdjustmentModifier RightBowModifier = new AdjustmentModifier(partName -> {
      float rotationX = 0.0F;
      float rotationY = 0.0F;
      float rotationZ = 0.0F;
      float offsetX = 0.0F;
      float offsetY = 0.0F;
      float offsetZ = 0.0F;
      float pitch = this.getXRot();
      pitch = (float)Math.toRadians((double)pitch);
      switch (partName) {
         case "rightArm":
            rotationZ = -pitch;
            offsetY = pitch;
            break;
         case "leftArm":
            rotationZ = (float)((double)(-pitch) * 0.25);
            offsetY = -pitch;
            break;
         default:
            return Optional.empty();
      }

      return Optional.of(new PartModifier(new Vec3f(rotationX, rotationY, rotationZ), new Vec3f(offsetX, offsetY, offsetZ)));
   });
   AdjustmentModifier LeftBowModifier = new AdjustmentModifier(partName -> {
      float rotationX = 0.0F;
      float rotationY = 0.0F;
      float rotationZ = 0.0F;
      float offsetX = 0.0F;
      float offsetY = 0.0F;
      float offsetZ = 0.0F;
      float pitch = this.getXRot();
      pitch = (float)Math.toRadians((double)pitch);
      switch (partName) {
         case "leftArm":
            rotationZ = pitch;
            offsetY = -pitch;
            break;
         case "rightArm":
            rotationZ = (float)((double)pitch * 0.25);
            offsetY = pitch;
            break;
         default:
            return Optional.empty();
      }

      return Optional.of(new PartModifier(new Vec3f(rotationX, rotationY, rotationZ), new Vec3f(offsetX, offsetY, offsetZ)));
   });
   AdjustmentModifier ParagliderModifier = new AdjustmentModifier(partName -> {
      float rotationX = 0.0F;
      float rotationY = 0.0F;
      float rotationZ = 0.0F;
      float offsetX = 0.0F;
      float offsetY = 0.0F;
      float offsetZ = 0.0F;
      switch (partName) {
         case "torso":
            if (this.isMovingBackwards) {
               rotationX = (float)(-0.75 * this.moveSpeed);
            } else {
               rotationX = (float)(0.75 * this.moveSpeed);
            }
            break;
         case "rightLeg":
         case "leftLeg":
            if (this.isMovingBackwards) {
               rotationX = (float)(-this.moveSpeed * 1.5);
               offsetY = (float)(-this.moveSpeed * 3.5);
               offsetZ = (float)(-this.moveSpeed * 7.0);
            } else {
               rotationX = (float)(this.moveSpeed * 1.5);
               offsetY = (float)(-this.moveSpeed * 3.5);
               offsetZ = (float)(this.moveSpeed * 7.0);
            }
            break;
         default:
            return Optional.empty();
      }

      return Optional.of(new PartModifier(new Vec3f(rotationX, rotationY, rotationZ), new Vec3f(offsetX, offsetY, offsetZ)));
   });
   SpeedModifier OverlaySpeedModifier = new SpeedModifier();
   SpeedModifier AnimationSpeedModifier = new SpeedModifier();
   MirrorModifier OverlayMirrorModifier = new MirrorModifier();
   MirrorModifier AnimationMirrorModifier = new MirrorModifier();
   ModifierLayer<IAnimation> animationContainer = this.modAnimationContainer;
   ModifierLayer<IAnimation> animationContainer2 = this.modAnimationContainer2;

   public SeriousPlayerAnimationsMixin(Level world, BlockPos pos, float yaw, GameProfile gameProfile) {
      super(world, pos, yaw, gameProfile);
   }

   @Override
   public Vec3f getTorsoPos() {
      return this.torsoPos;
   }

   @Override
   public Vec3f getTorsoRotation() {
      return this.torsoRotation;
   }

   public void reloadAnimationVariables() {
      this.sword_attack = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "sword_attack"));
      this.sword_attack2 = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "sword_attack2"));
      this.idle_standing = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "idle_standing"));
      this.idle_creative_flying = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(
         ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "idle_creative_flying")
      );
      this.idle_creative_flying_item = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(
         ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "idle_creative_flying_item")
      );
      this.walking = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "walking"));
      this.walking_backwards = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "walking_backwards"));
      this.running = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "running"));
      this.turn_left = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "turn_left"));
      this.turn_right = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "turn_right"));
      this.idle_sneak = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "idle_sneak"));
      this.walking_sneak = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "walking_sneak"));
      this.walking_sneak_backwards = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(
         ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "walking_sneak_backwards")
      );
      this.sword_attack_sneak = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(
         ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "sword_attack_sneak")
      );
      this.sword_attack_sneak2 = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(
         ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "sword_attack_sneak2")
      );
      this.falling = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "falling"));
      this.blank_loop = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "blank_loop"));
      this.elytra = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "elytra"));
      this.eating_right = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "eating_right"));
      this.eating_left = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "eating_left"));
      this.eating_right_sneak = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(
         ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "eating_right_sneak")
      );
      this.eating_left_sneak = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "eating_left_sneak"));
      this.idle_in_water = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "idle_in_water"));
      this.forward_in_water = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "forward_in_water"));
      this.backwards_in_water = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(
         ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "backwards_in_water")
      );
      this.up_in_water = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "up_in_water"));
      this.swimming = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "swimming"));
      this.crawling = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "crawling"));
      this.idle_crawling = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "idle_crawling"));
      this.crawling_backwards = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(
         ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "crawling_backwards")
      );
      this.climbing = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "climbing"));
      this.climbing_sneak = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "climbing_sneak"));
      this.idle_climbing = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "idle_climbing"));
      this.idle_climbing_sneak = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(
         ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "idle_climbing_sneak")
      );
      this.climbing_backwards = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(
         ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "climbing_backwards")
      );
      this.pickaxe = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "pickaxe"));
      this.pickaxe_sneak = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "pickaxe_sneak"));
      this.minecart_idle = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "minecart_idle"));
      this.minecart_pickaxe = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "minecart_pickaxe"));
      this.horse_idle = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "horse_idle"));
      this.horse_running = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "horse_running"));
      this.horse_pickaxe = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "horse_pickaxe"));
      this.boat1 = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "boat1"));
      this.eating = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "eating"));
      this.bow_idle = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "bow_idle"));
      this.bow_sneak = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "bow_sneak"));
      this.sleeping = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "sleeping"));
      this.axe = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "axe"));
      this.axe_sneak = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "axe_sneak"));
      this.shovel = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "shovel"));
      this.shovel_sneak = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "shovel_sneak"));
      this.paraglider = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "paraglider"));
      this.generic_handswing = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "generic_handswing"));
      this.shield = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "shield"));
      this.shield_sneak = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "shield_sneak"));
      this.trident = (KeyframeAnimation)PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("seriousplayeranimations", "trident"));
   }

   @Inject(
      method = {"<init>"},
      at = {@At("RETURN")}
   )
   private void init(ClientLevel world, GameProfile profile, CallbackInfo info) {
      PlayerAnimationAccess.getPlayerAnimLayer((AbstractClientPlayer)(Object)this).addAnimLayer(1, this.modAnimationContainer);
      this.modAnimationContainer.addModifierLast(this.AnimationSpeedModifier);
      this.modAnimationContainer.addModifierLast(this.AnimationMirrorModifier);
      this.AnimationMirrorModifier.setEnabled(false);
      PlayerAnimationAccess.getPlayerAnimLayer((AbstractClientPlayer)(Object)this).addAnimLayer(2, this.modAnimationContainer2);
      this.modAnimationContainer2.addModifierLast(this.RightBowModifier);
      this.RightBowModifier.enabled = false;
      this.modAnimationContainer2.addModifierLast(this.LeftBowModifier);
      this.LeftBowModifier.enabled = false;
      this.modAnimationContainer2.addModifierLast(this.OverlaySpeedModifier);
      this.modAnimationContainer2.addModifierLast(this.OverlayMirrorModifier);
      this.OverlayMirrorModifier.setEnabled(false);
      if (SeriousPlayerAnimations.PARAGLIDER_COMPAT) {
         this.modAnimationContainer2.addModifierLast(this.ParagliderModifier);
         this.ParagliderModifier.enabled = false;
      }

      this.reloadAnimationVariables();
      this.currentAnimation = this.idle_standing;
      this.currentOverlay = this.blank_loop;
   }

   @Override
   public ModifierLayer<IAnimation> seriousplayeranimations_getModAnimation() {
      return this.modAnimationContainer;
   }

   @Override
   public void disableArms(boolean b) {
      this.disableArms = b;
   }

   @Override
   public void disableRightArmB(boolean b) {
      this.disableRightArmB = b;
   }

   @Override
   public void disableLeftArmB(boolean b) {
      this.disableLeftArmB = b;
   }

   @Override
   public void disableMainArmB(boolean b) {
      this.disableMainArmB = b;
   }

   @Override
   public void disableOffArmB(boolean b) {
      this.disableOffArmB = b;
   }

   @Override
   public void disableAnimationB(boolean b) {
      this.disableAnimationB = b;
   }

   @Override
   public void disableOverlayB(boolean b) {
      this.disableOverlayB = b;
   }

   @Override
   public void armPosMain(ArmPose pos) {
      this.armPosMain = pos;
   }

   @Override
   public void armPosOff(ArmPose pos) {
      this.armPosOff = pos;
   }

   public void disableMainArm() {
      this.builder = this.currentAnimation.mutableCopy();
      StateCollection rightArm = this.builder.getPart("rightArm");

      assert rightArm != null;

      StateCollection leftArm = this.builder.getPart("leftArm");

      assert leftArm != null;

      if (this.rightHand == InteractionHand.MAIN_HAND) {
         rightArm.pitch.setEnabled(false);
         rightArm.yaw.setEnabled(false);
         rightArm.roll.setEnabled(false);
         this.currentAnimation = this.builder.build();
      } else if (this.leftHand == InteractionHand.MAIN_HAND) {
         leftArm.pitch.setEnabled(false);
         leftArm.yaw.setEnabled(false);
         leftArm.roll.setEnabled(false);
         this.currentAnimation = this.builder.build();
      }
   }

   public void disableOffArm() {
      this.builder = this.currentAnimation.mutableCopy();
      StateCollection rightArm = this.builder.getPart("rightArm");

      assert rightArm != null;

      StateCollection leftArm = this.builder.getPart("leftArm");

      assert leftArm != null;

      if (this.rightHand == InteractionHand.OFF_HAND) {
         rightArm.pitch.setEnabled(false);
         rightArm.yaw.setEnabled(false);
         rightArm.roll.setEnabled(false);
         this.currentAnimation = this.builder.build();
      } else if (this.leftHand == InteractionHand.OFF_HAND) {
         leftArm.pitch.setEnabled(false);
         leftArm.yaw.setEnabled(false);
         leftArm.roll.setEnabled(false);
         this.currentAnimation = this.builder.build();
      }
   }

   public void disableRightArm() {
      this.builder = this.currentAnimation.mutableCopy();
      StateCollection rightArm = this.builder.getPart("rightArm");

      assert rightArm != null;

      rightArm.pitch.setEnabled(false);
      rightArm.yaw.setEnabled(false);
      rightArm.roll.setEnabled(false);
      this.currentAnimation = this.builder.build();
   }

   public void disableLeftArm() {
      this.builder = this.currentAnimation.mutableCopy();
      StateCollection leftArm = this.builder.getPart("leftArm");

      assert leftArm != null;

      leftArm.pitch.setEnabled(false);
      leftArm.yaw.setEnabled(false);
      leftArm.roll.setEnabled(false);
      this.currentAnimation = this.builder.build();
   }

   public void disableBothArms() {
      this.builder = this.currentAnimation.mutableCopy();
      StateCollection rightArm = this.builder.getPart("rightArm");

      assert rightArm != null;

      StateCollection leftArm = this.builder.getPart("leftArm");

      assert leftArm != null;

      rightArm.pitch.setEnabled(false);
      rightArm.yaw.setEnabled(false);
      rightArm.roll.setEnabled(false);
      leftArm.pitch.setEnabled(false);
      leftArm.yaw.setEnabled(false);
      leftArm.roll.setEnabled(false);
      this.currentAnimation = this.builder.build();
   }

   public void disableRightArmOverlayPos() {
      this.builder = this.currentAnimation.mutableCopy();
      StateCollection rightArm = this.builder.getPart("rightArm");

      assert rightArm != null;

      rightArm.x.setEnabled(false);
      rightArm.y.setEnabled(false);
      rightArm.z.setEnabled(false);
      this.currentAnimation = this.builder.build();
   }

   public void disableLeftArmOverlayPos() {
      this.builder = this.currentAnimation.mutableCopy();
      StateCollection leftArm = this.builder.getPart("leftArm");

      assert leftArm != null;

      leftArm.x.setEnabled(false);
      leftArm.y.setEnabled(false);
      leftArm.z.setEnabled(false);
      this.currentAnimation = this.builder.build();
   }

   public void disableAnimation() {
      this.currentAnimation = this.blank_loop;
      this.currentAnimationId = "blank_loop";
   }

   public void disableOverlay() {
      this.currentOverlay = this.blank_loop;
      this.currentOverlayId = "blank_loop";
   }

   public void loopedToolAnimation(
      KeyframeAnimation overlay, KeyframeAnimation overlay_sneak, String id, ClientConfig.AnimationConfig getconfig, int fade, float speed, int priority
   ) {
      if (getconfig.enabled) {
         this.overlayFadeTime = fade;
         this.overlayAnimationSpeed = speed * getconfig.speedMultiplier;
         this.overlayPriority = priority;
         this.OverlayMirrorModifier.setEnabled(this.rightHand != InteractionHand.MAIN_HAND);
         if (this.crouched) {
            this.currentOverlay = overlay_sneak;
            this.currentOverlayId = id + "_sneak";
         } else {
            this.currentOverlay = overlay;
            this.currentOverlayId = id;
         }
      } else {
         this.genericHandswing();
      }
   }

   public void genericHandswing() {
      if (this.swingingArm.equals(InteractionHand.MAIN_HAND)) {
         this.disableMainArm();
      } else if (this.swingingArm.equals(InteractionHand.OFF_HAND)) {
         this.disableOffArm();
      }

      this.currentAnimationId = "handswinging" + this.currentAnimationId;
      this.modifyId = "handswinging";
      this.fadeTime = 0;
      this.priority = 0;
   }

   @Inject(
      method = {"tick"},
      at = {@At("TAIL")}
   )
   public void tick(CallbackInfo ci) {
      this.animate();
   }

   public void animate() {
      this.animationTick++;
      if (this.getMainArm() == HumanoidArm.LEFT) {
         this.rightHand = InteractionHand.OFF_HAND;
         this.leftHand = InteractionHand.MAIN_HAND;
      } else {
         this.rightHand = InteractionHand.MAIN_HAND;
         this.leftHand = InteractionHand.OFF_HAND;
      }

      this.AnimationSpeedModifier.speed = this.animationSpeed * SeriousPlayerAnimations.config.animationSpeedMultiplier;
      this.OverlaySpeedModifier.speed = this.overlayAnimationSpeed * SeriousPlayerAnimations.config.animationSpeedMultiplier;
      this.byaw = this.yBodyRot;
      this.hyaw = this.yHeadRot;
      this.pos = this.position();
      this.vx = (float)(this.pos.x - this.lastPos.x);
      this.vy = (float)(this.pos.y - this.lastPos.y);
      this.vz = (float)(this.pos.z - this.lastPos.z);
      this.moveSpeed = Math.sqrt((double)(this.vx * this.vx + this.vz * this.vz));
      this.turn = this.byaw - this.prevbyaw;
      this.bodyYawRadians = Math.toRadians((double)(this.yBodyRot + 90.0F));
      Vector3f movementVector = new Vector3f(this.vx, 0.0F, this.vz);
      Vector3f lookVector = new Vector3f((float)Math.cos(this.bodyYawRadians), 0.0F, (float)Math.sin(this.bodyYawRadians));
      this.isMovingBackwards = movementVector.length() > 0.0F && movementVector.dot(lookVector) < 0.0F;
      this.currentOverlay = this.blank_loop;
      this.currentOverlayId = "blank_loop";
      this.overlayFadeTime = 10;
      this.overlayAnimationSpeed = 1.0F;
      this.overlayPriority = 0;
      this.crouched = this.isCrouching();
      if (this.moveSpeed < 0.23 && this.moveSpeed > 0.0 && !this.isMovingBackwards && !this.crouched) {
         if (40.90909090909091 * this.moveSpeed > 1.0) {
            this.animationSpeed = (float)(40.90909090909091 * this.moveSpeed * (double)SeriousPlayerAnimations.config.getWalking().speedMultiplier);
         } else {
            this.animationSpeed = 2.0F * SeriousPlayerAnimations.config.getWalking().speedMultiplier;
         }

         this.currentAnimation = this.walking;
         this.currentAnimationId = "walking";
         if (!SeriousPlayerAnimations.config.getWalking().enabled) {
            this.disableAnimation();
         }

         this.fadeTime = 7;
         this.priority = 0;
      } else if (this.isMovingBackwards && !this.crouched) {
         if (18.181818181818183 * this.moveSpeed > 1.0) {
            this.animationSpeed = (float)(18.181818181818183 * this.moveSpeed * (double)SeriousPlayerAnimations.config.getWalkingBackwards().speedMultiplier);
         } else {
            this.animationSpeed = 2.0F * SeriousPlayerAnimations.config.getWalkingBackwards().speedMultiplier;
         }

         if (this.vy > 0.0F) {
            this.animationSpeed = 0.1F;
         }

         this.currentAnimation = this.walking_backwards;
         this.currentAnimationId = "walking_backwards";
         if (!SeriousPlayerAnimations.config.getWalkingBackwards().enabled) {
            this.disableAnimation();
         }

         this.fadeTime = 7;
         this.priority = 0;
      } else if (this.moveSpeed > 0.23 && this.isSprinting() && !this.isMovingBackwards && !this.crouched) {
         if (10.714285714285714 * this.moveSpeed > 1.0) {
            this.animationSpeed = (float)(10.714285714285714 * this.moveSpeed * (double)SeriousPlayerAnimations.config.getRunning().speedMultiplier);
         } else {
            this.animationSpeed = 1.0F * SeriousPlayerAnimations.config.getRunning().speedMultiplier;
         }

         this.currentAnimation = this.running;
         this.currentAnimationId = "running";
         if (!SeriousPlayerAnimations.config.getRunning().enabled) {
            this.disableAnimation();
         }

         this.fadeTime = 2;
         this.priority = 0;
      } else if (this.moveSpeed == 0.0 && !this.crouched) {
         if (this.turn != 0.0F) {
            this.currentAnimation = this.turn < 0.0F ? this.turn_left : this.turn_right;
            this.currentAnimationId = this.turn < 0.0F ? "turn_left" : "turn_right";
            this.priority = 0;
            if (!SeriousPlayerAnimations.config.getTurningStanding().enabled) {
               this.disableAnimation();
            }

            if (Math.abs(0.5F * this.turn) > 5.0F) {
               this.animationSpeed = 2.0F * SeriousPlayerAnimations.config.getTurningStanding().speedMultiplier;
            } else {
               this.animationSpeed = Math.abs(0.5F * this.turn * SeriousPlayerAnimations.config.getTurningStanding().speedMultiplier);
            }
         } else {
            this.currentAnimation = this.idle_standing;
            this.currentAnimationId = "idle_standing";
            if (!SeriousPlayerAnimations.config.getIdleStanding().enabled) {
               this.disableAnimation();
            }

            if (!this.prevAnimationId.equals("idle_sneak") && !this.prevAnimationId.equals("walking_sneak") && !this.prevAnimationId.equals("jump")) {
               this.fadeTime = 10;
            } else {
               this.fadeTime = 1;
            }

            this.animationSpeed = SeriousPlayerAnimations.config.getIdleStanding().speedMultiplier;
            this.priority = 0;
         }
      } else if (this.crouched && this.moveSpeed == 0.0 && !this.isMovingBackwards) {
         this.currentAnimation = this.idle_sneak;
         this.currentAnimationId = "idle_sneak";
         if (!SeriousPlayerAnimations.config.getIdleSneaking().enabled) {
            this.disableAnimation();
         }

         this.animationSpeed = 1.0F * SeriousPlayerAnimations.config.getIdleSneaking().speedMultiplier;
         if (!this.prevAnimationId.equals("walking_sneak") && !this.prevAnimationId.equals("walking_sneak_backwards")) {
            this.fadeTime = 1;
         } else {
            this.fadeTime = 10;
         }

         this.priority = 0;
      } else if (this.crouched && this.moveSpeed > 0.0 && !this.isMovingBackwards) {
         this.currentAnimation = this.walking_sneak;
         this.currentAnimationId = "walking_sneak";
         if (!SeriousPlayerAnimations.config.getIdleSneaking().enabled) {
            this.disableAnimation();
         }

         this.animationSpeed = (float)(33.333333333333336 * this.moveSpeed * (double)SeriousPlayerAnimations.config.getWalkingSneak().speedMultiplier);
         if (this.animationSpeed < 1.0F) {
            this.animationSpeed = 1.0F * SeriousPlayerAnimations.config.getWalkingSneak().speedMultiplier;
         }

         if (!this.prevAnimationId.equals("idle_sneak") && !this.prevAnimationId.equals("walking_sneak_backwards")) {
            this.fadeTime = 1;
         } else {
            this.fadeTime = 7;
         }

         this.priority = 0;
      } else if (this.crouched && this.moveSpeed > 0.0) {
         this.currentAnimation = this.walking_sneak_backwards;
         this.currentAnimationId = "walking_sneak_backwards";
         if (!SeriousPlayerAnimations.config.getWalkingSneakBackwards().enabled) {
            this.disableAnimation();
         }

         this.animationSpeed = (float)(33.333333333333336 * this.moveSpeed * (double)SeriousPlayerAnimations.config.getWalkingSneakBackwards().speedMultiplier);
         if (this.animationSpeed < 1.0F) {
            this.animationSpeed = 1.0F * SeriousPlayerAnimations.config.getWalkingSneakBackwards().speedMultiplier;
         }

         if (!this.prevAnimationId.equals("idle_sneak") && !this.prevAnimationId.equals("walking_sneak")) {
            this.fadeTime = 1;
         } else {
            this.fadeTime = 7;
         }

         this.priority = 0;
      }

      if (this.vy > 0.0F && !this.onGround()) {
         this.animationSpeed = this.currentAnimationId.equals("walking") ? 4.0F : 1.0F;
         this.priority = 0;
      }

      this.vyfly = (double)Math.round((double)this.vy * 1000.0) / 1000.0;
      if ((this.vyfly == 0.0 || Math.abs(this.vyfly) == 0.375) && !this.onGround() && !this.isInWaterOrBubble()) {
         this.flychecker++;
      } else if (Math.abs(this.vyfly) > 0.375 || this.onGround()) {
         this.flychecker = 0;
      }

      if (this.flychecker > 10) {
         this.currentAnimation = this.idle_creative_flying;
         this.currentAnimationId = "idle_creative_flying";
         if (!SeriousPlayerAnimations.config.getIdleCreativeFlying().enabled) {
            this.disableAnimation();
         }

         this.fadeTime = 5;
         this.animationSpeed = SeriousPlayerAnimations.config.getIdleCreativeFlying().speedMultiplier;
         this.priority = 0;
      }

      if ((double)this.vy < -0.6 && !this.isPassenger() && !this.onGround()) {
         this.currentAnimation = this.falling;
         this.currentAnimationId = "falling";
         if (!SeriousPlayerAnimations.config.getFalling().enabled) {
            this.disableAnimation();
         }

         this.fadeTime = 8;
         this.animationSpeed = 1.0F * SeriousPlayerAnimations.config.getFalling().speedMultiplier;
         this.priority = 0;
      } else if (!this.onGround()) {
         this.animationSpeed = this.currentAnimationId.equals("walking") ? 4.0F : 1.0F;
         this.priority = 0;
      }

      if (!this.onGround() && !this.isPassenger()) {
         Block block = this.clientLevel.getBlockState(this.blockPosition()).getBlock();
         if (block instanceof LadderBlock || block instanceof VineBlock) {
            this.animationSpeed = 3.0F * SeriousPlayerAnimations.config.getClimbing().speedMultiplier;
            this.fadeTime = 7;
            this.priority = 0;
            if (!(this.getUseItem().getItem() instanceof BowItem)) {
               this.blockStateString = String.valueOf(this.clientLevel.getBlockState(this.blockPosition()));
               this.byaw = this.getVisualRotationYInDegrees();
               this.hyaw = this.getYHeadRot();
               if (this.blockStateString.contains("facing=north") || this.blockStateString.contains("south=true")) {
                  this.byaw = 0.0F;
               } else if (this.blockStateString.contains("facing=south") || this.blockStateString.contains("north=true")) {
                  this.byaw = 180.0F;
               } else if (this.blockStateString.contains("facing=west") || this.blockStateString.contains("east=true")) {
                  this.byaw = 270.0F;
               } else if (this.blockStateString.contains("facing=east") || this.blockStateString.contains("west=true")) {
                  this.byaw = 90.0F;
               }

               this.byaw = (this.byaw % 360.0F + 360.0F) % 360.0F;
               this.hyaw = (this.hyaw % 360.0F + 360.0F) % 360.0F;
               this.setYBodyRot(this.byaw);
               this.hyaw = this.hyaw - this.byaw;
               this.hyaw = (this.hyaw % 360.0F + 360.0F) % 360.0F;
               if (this.hyaw > 90.0F && this.hyaw <= 180.0F) {
                  this.setYHeadRot(this.byaw + 90.0F);
               } else if (this.hyaw > 180.0F && this.hyaw < 270.0F) {
                  this.setYHeadRot(this.byaw + 270.0F);
               }
            }

            if (this.onClimbable() && this.vy > 0.0F) {
               if (this.crouched) {
                  this.currentAnimation = this.climbing_sneak;
                  this.currentAnimationId = "climbing_sneak";
               } else {
                  this.currentAnimation = this.climbing;
                  this.currentAnimationId = "climbing";
               }
            } else if (this.onClimbable() && this.vy < 0.0F) {
               this.currentAnimation = this.climbing_backwards;
               this.currentAnimationId = "climbing_backwards";
            } else if (this.crouched) {
               this.currentAnimation = this.idle_climbing_sneak;
               this.currentAnimationId = "idle_climbing_sneak";
            } else {
               this.currentAnimation = this.idle_climbing;
               this.currentAnimationId = "idle_climbing";
            }
         } else if (block instanceof TwistingVinesPlantBlock
            || block instanceof WeepingVinesPlantBlock
            || block instanceof TwistingVinesBlock
            || block instanceof WeepingVinesBlock
            || block instanceof ScaffoldingBlock) {
            this.animationSpeed = 3.0F * SeriousPlayerAnimations.config.getClimbing().speedMultiplier;
            this.fadeTime = 7;
            this.priority = 0;
            if (!(this.getUseItem().getItem() instanceof BowItem)) {
               this.byaw = (float)Math.toDegrees(
                     Math.atan2(
                        (double)this.blockPosition().getZ() + 0.5 - this.pos.z,
                        (double)this.blockPosition().getX() + 0.5 - this.pos.x
                     )
                  )
                  - 90.0F;
               this.hyaw = this.yHeadRot;
               this.byaw = (this.byaw % 360.0F + 360.0F) % 360.0F;
               this.hyaw = (this.hyaw % 360.0F + 360.0F) % 360.0F;
               this.setYBodyRot(this.byaw);
               this.hyaw = this.hyaw - this.byaw;
               this.hyaw = (this.hyaw % 360.0F + 360.0F) % 360.0F;
               if (this.hyaw > 90.0F && this.hyaw <= 180.0F) {
                  this.setYHeadRot(this.byaw + 90.0F);
               } else if (this.hyaw > 180.0F && this.hyaw < 270.0F) {
                  this.setYHeadRot(this.byaw + 270.0F);
               }
            }

            if (this.onClimbable()) {
               if (this.vy > 0.0F) {
                  this.currentAnimation = this.crouched ? this.climbing_sneak : this.climbing;
                  this.currentAnimationId = this.crouched ? "climbing_sneak" : "climbing";
               } else if (this.vy < 0.0F) {
                  this.currentAnimation = this.climbing_backwards;
                  this.currentAnimationId = "climbing_backwards";
               } else {
                  this.currentAnimation = this.crouched ? this.idle_climbing_sneak : this.idle_climbing;
                  this.currentAnimationId = this.crouched ? "idle_climbing_sneak" : "idle_climbing";
               }
            } else {
               this.currentAnimation = this.crouched ? this.idle_climbing_sneak : this.idle_climbing;
               this.currentAnimationId = this.crouched ? "idle_climbing_sneak" : "idle_climbing";
            }
         } else if (block instanceof PowderSnowBlock) {
            this.fadeTime = 7;
            this.priority = 0;
            if (String.valueOf(this.getArmorSlots()).contains("leather_boots")) {
               if (this.vy > 0.0F) {
                  if (this.crouched) {
                     this.currentAnimation = this.climbing_sneak;
                     this.currentAnimationId = "climbing_sneak";
                  } else {
                     this.currentAnimation = this.climbing;
                     this.currentAnimationId = "climbing";
                  }
               } else if (this.vy < 0.0F) {
                  this.currentAnimation = this.climbing_backwards;
                  this.currentAnimationId = "climbing_backwards";
               } else if (this.crouched) {
                  this.currentAnimation = this.idle_climbing_sneak;
                  this.currentAnimationId = "idle_climbing_sneak";
               } else {
                  this.currentAnimation = this.idle_climbing;
                  this.currentAnimationId = "idle_climbing";
               }
            }
         }

         if (!SeriousPlayerAnimations.config.getClimbing().enabled) {
            this.disableAnimation();
         }
      }

      if (this.isVisuallyCrawling()) {
         if (this.moveSpeed > 0.0 && !this.isMovingBackwards) {
            this.currentAnimation = this.crawling;
            this.currentAnimationId = "crawling";
            if (!SeriousPlayerAnimations.config.getCrawling().enabled) {
               this.disableAnimation();
            }

            this.animationSpeed = (float)(33.333333333333336 * this.moveSpeed * (double)SeriousPlayerAnimations.config.getCrawling().speedMultiplier);
            if (this.animationSpeed < 1.0F) {
               this.animationSpeed = 1.0F * SeriousPlayerAnimations.config.getCrawling().speedMultiplier;
            }
         } else if (this.moveSpeed > 0.0) {
            this.currentAnimation = this.crawling_backwards;
            this.currentAnimationId = "crawling_backwards";
            if (!SeriousPlayerAnimations.config.getCrawlingBackwards().enabled) {
               this.disableAnimation();
            }

            this.animationSpeed = (float)(33.333333333333336 * this.moveSpeed * (double)SeriousPlayerAnimations.config.getCrawlingBackwards().speedMultiplier);
            if (this.animationSpeed < 1.0F) {
               this.animationSpeed = 1.0F * SeriousPlayerAnimations.config.getCrawlingBackwards().speedMultiplier;
            }
         } else {
            this.currentAnimation = this.idle_crawling;
            this.currentAnimationId = "idle_crawling";
            if (!SeriousPlayerAnimations.config.getIdleCrawling().enabled) {
               this.disableAnimation();
            }

            this.animationSpeed = 2.0F * SeriousPlayerAnimations.config.getIdleCrawling().speedMultiplier;
         }

         this.fadeTime = 7;
         this.priority = 0;
      }

      if ((this.isInWaterOrBubble() || this.isInLava()) && !this.onGround() && !this.isVisuallySwimming()) {
         if (this.moveSpeed > 0.0 && !this.isMovingBackwards) {
            this.currentAnimation = this.forward_in_water;
            this.currentAnimationId = "forward_in_water";
            if (!SeriousPlayerAnimations.config.getForwardInWater().enabled) {
               this.disableAnimation();
            }

            this.animationSpeed = 1.0F * SeriousPlayerAnimations.config.getForwardInWater().speedMultiplier;
            this.fadeTime = 7;
            this.priority = 0;
         } else if (this.moveSpeed > 0.0) {
            this.currentAnimation = this.backwards_in_water;
            this.currentAnimationId = "backwards_in_water";
            if (!SeriousPlayerAnimations.config.getBackwardsInWater().enabled) {
               this.disableAnimation();
            }

            this.animationSpeed = 1.0F * SeriousPlayerAnimations.config.getBackwardsInWater().speedMultiplier;
            this.fadeTime = 7;
            this.priority = 0;
         } else if (this.vy > 0.0F) {
            this.currentAnimation = this.up_in_water;
            this.currentAnimationId = "up_in_water";
            if (!SeriousPlayerAnimations.config.getUpInWater().enabled) {
               this.disableAnimation();
            }

            this.animationSpeed = 1.0F * SeriousPlayerAnimations.config.getUpInWater().speedMultiplier;
            this.fadeTime = 7;
            this.priority = 0;
         } else {
            this.currentAnimation = this.idle_in_water;
            this.currentAnimationId = "idle_in_water";
            if (!SeriousPlayerAnimations.config.getIdleInWater().enabled) {
               this.disableAnimation();
            }

            this.fadeTime = 5;
            this.animationSpeed = 1.0F * SeriousPlayerAnimations.config.getIdleInWater().speedMultiplier;
            this.priority = 0;
         }
      } else if (this.isInWaterOrBubble() && this.isVisuallySwimming()) {
         this.currentAnimation = this.swimming;
         this.currentAnimationId = "swimming";
         if (!SeriousPlayerAnimations.config.getSwimming().enabled) {
            this.disableAnimation();
         }

         this.animationSpeed = 1.0F * SeriousPlayerAnimations.config.getSwimming().speedMultiplier;
         this.fadeTime = 7;
         this.priority = 0;
      }

      if (this.isPassenger()) {
         Entity vehicle = this.getVehicle();
         if (vehicle instanceof Minecart) {
            this.currentAnimation = this.minecart_idle;
            this.currentAnimationId = "minecart_idle";
            if (!SeriousPlayerAnimations.config.getMinecartIdle().enabled) {
               this.disableAnimation();
            }

            this.animationSpeed = 1.0F * SeriousPlayerAnimations.config.getMinecartIdle().speedMultiplier;
            this.fadeTime = 2;
            this.priority = 0;
         } else if (!(vehicle instanceof Horse)
            && !(vehicle instanceof SkeletonHorse)
            && !(vehicle instanceof ZombieHorse)
            && !(vehicle instanceof Donkey)
            && !(vehicle instanceof Mule)) {
            if (!(vehicle instanceof Boat) && !(vehicle instanceof ChestBoat)) {
               this.currentAnimation = this.horse_idle;
               this.currentAnimationId = "horse_idle";
               if (!SeriousPlayerAnimations.config.getHorseIdle().enabled) {
                  this.disableAnimation();
               }

               this.animationSpeed = 1.0F * SeriousPlayerAnimations.config.getHorseIdle().speedMultiplier;
               this.fadeTime = 1;
               this.priority = 0;
            } else {
               this.boatString = String.valueOf(this.getVehicle());
               this.currentAnimation = this.boat1;
               this.currentAnimationId = "boat1";
               if (!SeriousPlayerAnimations.config.getBoat().enabled) {
                  this.disableAnimation();
               }

               this.animationSpeed = 1.0F * SeriousPlayerAnimations.config.getBoat().speedMultiplier;
               this.fadeTime = 1;
               this.priority = 0;
            }
         } else {
            if (this.moveSpeed > 0.0 && !this.isMovingBackwards) {
               this.currentAnimation = this.horse_running;
               this.currentAnimationId = "horse_running";
               if (!SeriousPlayerAnimations.config.getHorseRunning().enabled) {
                  this.disableAnimation();
               }
            } else {
               this.fadeTime = 5;
               this.currentAnimation = this.horse_idle;
               this.currentAnimationId = "horse_idle";
               if (!SeriousPlayerAnimations.config.getHorseIdle().enabled) {
                  this.disableAnimation();
               }
            }

            if (this.isUsingItem()) {
               this.currentAnimation = this.horse_idle;
               this.currentAnimationId = "horse_idle";
               if (!SeriousPlayerAnimations.config.getHorseIdle().enabled) {
                  this.disableAnimation();
               }
            }

            this.animationSpeed = 1.0F * SeriousPlayerAnimations.config.getHorseRunning().speedMultiplier;
            this.fadeTime = 1;
            this.priority = 0;
         }
      }

      if (this.isFallFlying()) {
         this.currentAnimation = this.elytra;
         this.currentAnimationId = "elytra";
         if (!SeriousPlayerAnimations.config.getElytra().enabled) {
            this.disableAnimation();
         }

         this.animationSpeed = 1.0F * SeriousPlayerAnimations.config.getElytra().speedMultiplier;
         this.priority = 0;
         this.fadeTime = 5;
      }

      if (this.swinging) {
         if ((this.getMainHandItem().getItem() instanceof SwordItem || this.getMainHandItem().getItem() instanceof TridentItem)
            && !this.isUsingItem()
            && this.swingingArm.equals(InteractionHand.MAIN_HAND)) {
            this.overlayAnimationSpeed = 1.4F * SeriousPlayerAnimations.config.getSwordAttack().speedMultiplier;
            this.overlayFadeTime = 0;
            this.overlayPriority = 1;
            this.OverlayMirrorModifier.setEnabled(this.rightHand != InteractionHand.MAIN_HAND);
            if (this.swordSeq) {
               if (this.crouched) {
                  this.currentOverlay = this.sword_attack_sneak;
                  this.currentOverlayId = "sword_attack_sneak";
               } else {
                  this.currentOverlay = this.sword_attack;
                  this.currentOverlayId = "sword_attack";
               }
            } else if (this.crouched) {
               this.currentOverlay = this.sword_attack_sneak2;
               this.currentOverlayId = "sword_attack_sneak";
            } else {
               this.currentOverlay = this.sword_attack2;
               this.currentOverlayId = "sword_attack";
            }

            if (!SeriousPlayerAnimations.config.getSwordAttack().enabled) {
               this.disableOverlay();
               this.genericHandswing();
            }
         } else if (this.getMainHandItem().getItem() instanceof PickaxeItem && this.swingingArm.equals(InteractionHand.MAIN_HAND)) {
            this.loopedToolAnimation(this.pickaxe, this.pickaxe_sneak, "pickaxe", SeriousPlayerAnimations.config.getPickaxe(), 1, 2.0F, 0);
         } else if (this.getMainHandItem().getItem() instanceof AxeItem && this.swingingArm.equals(InteractionHand.MAIN_HAND)) {
            this.loopedToolAnimation(this.axe, this.axe_sneak, "axe", SeriousPlayerAnimations.config.getAxe(), 1, 1.5F, 0);
         } else if (this.getMainHandItem().getItem() instanceof ShovelItem && this.swingingArm.equals(InteractionHand.MAIN_HAND)) {
            this.loopedToolAnimation(this.shovel, this.shovel_sneak, "shovel", SeriousPlayerAnimations.config.getShovel(), 1, 1.5F, 0);
         } else {
            this.genericHandswing();
         }
      }

      if (this.isSleeping()) {
         this.fadeTime = 2;
         this.animationSpeed = 2.0F;
         this.priority = 0;
         this.disableAnimation();
         this.currentOverlay = this.sleeping;
         this.currentOverlayId = "sleeping";
         if (!SeriousPlayerAnimations.config.getSleeping().enabled) {
            this.disableOverlay();
         }
      }

      if (this.isUsingItem()) {
         this.activeItem = this.getUseItem().getItem();
         if (this.activeItem.components().has(DataComponents.FOOD)) {
            if (SeriousPlayerAnimations.config.getEating().enabled) {
               this.overlayFadeTime = 9;
               this.overlayAnimationSpeed = 1.0F * SeriousPlayerAnimations.config.getEating().speedMultiplier;
               this.overlayPriority = 0;
               if (this.getUsedItemHand().equals(this.rightHand)) {
                  this.currentOverlay = this.eating_right;
                  this.currentOverlayId = "eating_right";
                  this.disableRightArmOverlayPos();
                  this.OverlayMirrorModifier.setEnabled(false);
               } else if (this.getUsedItemHand().equals(this.leftHand)) {
                  this.currentOverlay = this.eating_right;
                  this.currentOverlayId = "eating_left";
                  this.disableLeftArmOverlayPos();
                  this.OverlayMirrorModifier.setEnabled(true);
               }
            }
         } else if (this.isScoping()) {
            if (this.getUsedItemHand().equals(InteractionHand.MAIN_HAND)) {
               this.disableMainArmB = true;
            } else {
               this.disableOffArmB = true;
            }

            this.fadeTime = 1;
            this.priority = 0;
         } else if (this.activeItem instanceof TridentItem) {
            if (SeriousPlayerAnimations.config.getTrident().enabled) {
               this.overlayFadeTime = 5;
               this.overlayAnimationSpeed = SeriousPlayerAnimations.config.getTrident().speedMultiplier;
               this.overlayPriority = 0;
               if (this.getUsedItemHand().equals(this.rightHand)) {
                  if (this.crouched) {
                     this.disableRightArmOverlayPos();
                     this.disableLeftArmOverlayPos();
                  } else {
                     this.currentOverlay = this.trident;
                     this.currentOverlayId = "right_trident";
                  }

                  this.setYBodyRot(this.hyaw + 55.0F);
                  this.OverlayMirrorModifier.setEnabled(false);
               } else if (this.getUsedItemHand().equals(this.leftHand)) {
                  if (this.crouched) {
                     this.disableRightArmOverlayPos();
                     this.disableLeftArmOverlayPos();
                  } else {
                     this.currentOverlay = this.trident;
                     this.currentOverlayId = "left_trident";
                  }

                  this.setYBodyRot(this.hyaw - 55.0F);
                  this.OverlayMirrorModifier.setEnabled(true);
               }
            } else {
               if (this.getUsedItemHand().equals(InteractionHand.MAIN_HAND)) {
                  this.disableMainArmB = true;
               } else {
                  this.disableOffArmB = true;
               }

               this.priority = 0;
               this.fadeTime = 1;
            }
         } else if (this.activeItem instanceof BrushItem) {
            if (this.getUsedItemHand().equals(InteractionHand.MAIN_HAND)) {
               this.disableMainArmB = true;
            } else {
               this.disableOffArmB = true;
            }

            this.priority = 0;
         } else if (this.activeItem instanceof InstrumentItem) {
            if (this.getUsedItemHand().equals(InteractionHand.MAIN_HAND)) {
               this.disableMainArmB = true;
            } else {
               this.disableOffArmB = true;
            }

            this.fadeTime = 1;
            this.priority = 0;
         } else if (this.activeItem instanceof BowItem) {
            if (!this.isPassenger() && !this.isVisuallyCrawling() && SeriousPlayerAnimations.config.getBow().enabled) {
               this.overlayFadeTime = 6;
               this.overlayAnimationSpeed = 1.0F * SeriousPlayerAnimations.config.getBow().speedMultiplier;
               this.overlayPriority = 0;
               if (this.getUsedItemHand().equals(this.rightHand)) {
                  if (this.crouched) {
                     this.currentOverlay = this.bow_sneak;
                     this.currentOverlayId = "right_bow_sneak";
                     this.overlayFadeTime = 1;
                  } else {
                     this.currentOverlay = this.bow_idle;
                     this.currentOverlayId = "right_bow_idle";
                  }

                  this.disableRightArmOverlayPos();
                  this.RightBowModifier.enabled = true;
                  this.setYBodyRot(this.hyaw - 90.0F);
                  this.OverlayMirrorModifier.setEnabled(false);
               } else if (this.getUsedItemHand().equals(this.leftHand)) {
                  if (this.crouched) {
                     this.currentOverlay = this.bow_sneak;
                     this.currentOverlayId = "left_bow_sneak";
                     this.overlayFadeTime = 1;
                  } else {
                     this.currentOverlay = this.bow_idle;
                     this.currentOverlayId = "left_bow_idle";
                  }

                  this.disableLeftArmOverlayPos();
                  this.LeftBowModifier.enabled = true;
                  this.setYBodyRot(this.hyaw + 90.0F);
                  this.OverlayMirrorModifier.setEnabled(true);
               }
            } else {
               this.disableRightArm();
               this.disableLeftArm();
               this.modifyId = "bow_idle";
               this.fadeTime = 1;
            }
         } else if (this.activeItem instanceof ShieldItem) {
            if (SeriousPlayerAnimations.config.getShield().enabled) {
               this.overlayFadeTime = 5;
               this.overlayAnimationSpeed = SeriousPlayerAnimations.config.getShield().speedMultiplier;
               this.overlayPriority = 0;
               if (this.getUsedItemHand().equals(this.rightHand)) {
                  if (this.crouched) {
                     this.currentOverlay = this.shield_sneak;
                     this.currentOverlayId = "right_shield_sneak";
                     this.overlayFadeTime = 1;
                  } else {
                     this.currentOverlay = this.shield;
                     this.currentOverlayId = "right_shield";
                  }

                  this.OverlayMirrorModifier.setEnabled(false);
               } else if (this.getUsedItemHand().equals(this.leftHand)) {
                  if (this.crouched) {
                     this.currentOverlay = this.shield_sneak;
                     this.currentOverlayId = "left_shield_sneak";
                     this.overlayFadeTime = 1;
                  } else {
                     this.currentOverlay = this.shield;
                     this.currentOverlayId = "left_shield";
                  }

                  this.OverlayMirrorModifier.setEnabled(true);
               }
            } else {
               if (this.getUsedItemHand().equals(InteractionHand.MAIN_HAND)) {
                  this.disableMainArmB = true;
               } else {
                  this.disableOffArmB = true;
               }

               this.priority = 0;
               this.fadeTime = 1;
            }
         } else if (this.activeItem instanceof CrossbowItem) {
            this.disableArms = true;
         } else if (SeriousPlayerAnimations.SUPPLEMENTARIES_COMPAT) {
            if (SupplementariesFluteCheck.check(this.activeItem)) {
               this.disableArms = true;
            }
         } else {
            if (this.getUsedItemHand().equals(InteractionHand.MAIN_HAND)) {
               this.disableMainArmB = true;
            } else {
               this.disableOffArmB = true;
            }

            this.priority = 0;
         }
      } else {
         this.modifyId = "";
      }

      if (!this.getMainHandItem().isEmpty()) {
         this.item = this.getMainHandItem().getItem();
         if (SeriousPlayerAnimations.SWORDBLOCKING_COMPAT && SwordBlockingCheck.check(this)) {
            this.disableOverlay();
            this.disableMainArmB = true;
         }

         if (SeriousPlayerAnimations.OLDCOMBATMOD_COMPAT && OldCombatModCheck.check(this)) {
            this.disableOverlay();
            this.disableMainArmB = true;
         }

         if (SeriousPlayerAnimations.IMMERSIVE_MELODIES_COMPAT && ImmersiveMelodiesItemCheck.check(this.item)) {
            this.disableArms = true;
         }

         if (SeriousPlayerAnimations.ULTRACRAFT_COMPAT) {
            if (UltracraftCheck.isGenericGun(this.item)) {
               this.disableMainArmB = true;
            } else if (UltracraftCheck.isNailgun(this.item)) {
               this.disableArms = true;
            }
         }

         if (this.armPosMain.equals(ArmPose.CROSSBOW_HOLD) || this.armPosMain.equals(ArmPose.CROSSBOW_CHARGE)) {
            this.disableArms = true;
         } else if (this.armPosMain.equals(ArmPose.BOW_AND_ARROW) && !(this.item instanceof BowItem)) {
            this.disableArms = true;
         }

         if (SeriousPlayerAnimations.TRIGGERHAPPY_COMPAT) {
            if (TriggerHappyCheck.checkOneHanded(this.armPosMain)) {
               this.disableMainArmB = true;
            } else if (TriggerHappyCheck.checkTwoHanded(this.armPosMain)) {
               this.disableArms = true;
            }
         }
      }

      if (!this.getOffhandItem().isEmpty()) {
         this.item = this.getOffhandItem().getItem();
         if (SeriousPlayerAnimations.IMMERSIVE_MELODIES_COMPAT && ImmersiveMelodiesItemCheck.check(this.item)) {
            this.disableArms = true;
         }

         if (this.armPosOff.equals(ArmPose.CROSSBOW_HOLD) || this.armPosOff.equals(ArmPose.CROSSBOW_CHARGE)) {
            this.disableArms = true;
         } else if (this.armPosOff.equals(ArmPose.BOW_AND_ARROW) && !(this.item instanceof BowItem)) {
            this.disableArms = true;
         }

         if (SeriousPlayerAnimations.TRIGGERHAPPY_COMPAT) {
            if (TriggerHappyCheck.checkOneHanded(this.armPosOff)) {
               this.disableOffArmB = true;
            } else if (TriggerHappyCheck.checkTwoHanded(this.armPosOff)) {
               this.disableArms = true;
            }
         }
      }

      if (SeriousPlayerAnimations.CARRYON_COMPAT) {
         CarryOnCheck.check((AbstractClientPlayer)(Object)this);
      }

      if (this.disableRightArmB) {
         this.disableRightArm();
         this.disableRightArmB = false;
         this.modifyId = "disable_right";
         if (!Objects.equals(this.prevModifyId, this.modifyId)) {
            this.fadeTime = 1;
         }
      }

      if (this.disableLeftArmB) {
         this.disableLeftArm();
         this.disableLeftArmB = false;
         this.modifyId = "disable_left";
         if (!Objects.equals(this.prevModifyId, this.modifyId)) {
            this.fadeTime = 1;
         }
      }

      if (this.disableMainArmB) {
         this.disableMainArm();
         this.disableMainArmB = false;
         this.modifyId = "disable_main";
         if (!Objects.equals(this.prevModifyId, this.modifyId)) {
            this.fadeTime = 1;
         }
      }

      if (this.disableOffArmB) {
         this.disableOffArm();
         this.disableOffArmB = false;
         this.modifyId = "disable_off";
         if (!Objects.equals(this.prevModifyId, this.modifyId)) {
            this.fadeTime = 1;
         }
      }

      if (this.disableArms) {
         this.disableBothArms();
         this.disableArms = false;
         this.modifyId = "disable_both";
         if (!Objects.equals(this.prevModifyId, this.modifyId)) {
            this.fadeTime = 1;
         }
      }

      if (this.disableAnimationB) {
         this.disableAnimation();
         this.disableAnimationB = false;
      }

      if (this.disableOverlayB) {
         this.disableOverlay();
         this.disableAnimationB = false;
      }

      if (!Objects.equals(this.currentAnimationId, this.prevAnimationId) && this.priority >= this.prevPriority
         || !this.animationContainer.isActive()
         || !Objects.equals(this.modifyId, this.prevModifyId)) {
         this.animationContainer
            .replaceAnimationWithFade(AbstractFadeModifier.standardFadeIn(this.fadeTime, Ease.INOUTSINE), new KeyframeAnimationPlayer(this.currentAnimation));
         this.animationTick = 0;
         this.prevAnimationId = this.currentAnimationId;
         this.prevAnimation = this.currentAnimation;
         this.prevModifyId = this.modifyId;
         this.prevPriority = this.priority;
         this.modified = true;
      }

      this.lastPos = new Vec3(this.pos.x, this.pos.y, this.pos.z);
      this.prevOnGround = this.onGround();
      this.prevbyaw = this.byaw;
      if (!Objects.equals(this.currentOverlayId, this.prevOverlayId) && this.overlayPriority >= this.prevOverlayPriority
         || !this.animationContainer2.isActive()) {
         this.RightBowModifier.enabled = false;
         this.LeftBowModifier.enabled = false;
         if (this.prevOverlayId.contains("trident") && !this.currentOverlayId.contains("trident")) {
            this.overlayFadeTime = 3;
         }

         if (SeriousPlayerAnimations.PARAGLIDER_COMPAT && !this.currentOverlayId.equals("paraglider")) {
            this.ParagliderModifier.enabled = false;
         }

         if (!this.currentOverlayId.equals("sword_attack") && !this.currentOverlayId.equals("sword_attack_sneak")) {
            this.animationContainer2
               .replaceAnimationWithFade(
                  AbstractFadeModifier.standardFadeIn(this.overlayFadeTime, Ease.INOUTSINE), new KeyframeAnimationPlayer(this.currentOverlay), true
               );
         } else {
            this.swordSeq = !this.swordSeq;
            this.animationContainer2.setAnimation(null);
            this.animationContainer2
               .replaceAnimationWithFade(
                  AbstractFadeModifier.standardFadeIn(this.overlayFadeTime, Ease.INOUTSINE), new KeyframeAnimationPlayer(this.currentOverlay)
               );
         }

         this.prevOverlayId = this.currentOverlayId;
         this.prevOverlayPriority = this.overlayPriority;
      }

      if (this.animationContainer.isActive()) {
         this.torso2 = this.animationContainer2.get3DTransform("torso", TransformType.POSITION, 0.0F, this.zero);
         if ((Float)this.torso2.getZ() == 0.0F && (Float)this.torso2.getY() == 0.0F) {
            this.torsoPos = this.animationContainer.get3DTransform("torso", TransformType.POSITION, 0.0F, this.zero);
         } else {
            this.torsoPos = this.torso2;
         }

         this.torsoRotation2 = this.animationContainer2.get3DTransform("torso", TransformType.ROTATION, 0.0F, this.zero);
         if ((Float)this.torsoRotation2.getX() == 0.0F) {
            this.torsoRotation = this.animationContainer.get3DTransform("torso", TransformType.ROTATION, 0.0F, this.zero);
         } else {
            this.torsoRotation = this.torsoRotation2;
         }
      }
   }
}

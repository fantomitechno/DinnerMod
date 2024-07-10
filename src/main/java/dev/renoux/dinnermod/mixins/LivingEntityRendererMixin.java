package dev.renoux.dinnermod.mixins;

import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import dev.renoux.dinnermod.Config;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {

  @Unique
  private void transformFromName(String string, MatrixStack matrices) {
      switch (string) {
          case "Pisteur_alpin" -> {
              matrices.translate(0.3D, 0.1F, 0.0D);
              matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(15.0F));
          }
          case "Kresqle" -> {
              matrices.translate(0.8D, 0.1F, 0.0D);
              matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(42.0F));
          }
          case "Aragorn1202" -> {
              matrices.translate(0.0D, 0.1F, 0.0D);
              matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(5.0F));
          }
      }
  }

  @Inject(at = @At("TAIL"), method = "setupTransforms")
  private void setupTransforms(LivingEntity entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta, float scale, CallbackInfo ci) {
    Config Configuration = Config.getConfig();
    if ((entity instanceof PlayerEntity player)) {
      String string = player.getName().getString();
      if (player.isMainPlayer() && Configuration.getRotation() > 1) {
        switch (Configuration.getRotation()) {
          case 2 -> {
            matrices.translate(0.0D, player.getHeight() + 0.1F, 0.0D);
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90.0F));
          }
          case 3 -> {
            matrices.translate(0.0D, player.getHeight() + 0.1F, 0.0D);
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(270.0F));
          }
        }
      } else
        transformFromName(string, matrices);
      if (player.getRootVehicle().hasCustomName()) {
        transformFromName(Formatting.strip(player.getRootVehicle().getName().getString()), matrices);
      }
    } else if (entity.hasCustomName()) {
      transformFromName(Formatting.strip(entity.getName().getString()), matrices);
    }
  }

  @Inject(at = @At("HEAD"), method = "shouldFlipUpsideDown", cancellable = true)
  private static void shouldFlipUpsideDown(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
    Config Configuration = Config.getConfig();
    boolean value = false;
    if (entity instanceof PlayerEntity player) {
      String string = player.getName().getString();
      if ((player.isMainPlayer() && Configuration.getRotation() == 1) || ((string.equals("fantomitechno")
          || string.equals("fant0mib0t") || string.equals("NewGlace") || string.equals("Hardel")
          || string.equals("Dinnerbone") || string.equals("Grumm")) && !player.isMainPlayer())) {
        value = !(entity instanceof PlayerEntity) || ((PlayerEntity) entity).isPartVisible(PlayerModelPart.CAPE);
      }
    } else if (entity.hasCustomName()) {
      String string = Formatting.strip(entity.getName().getString());
      if (string.equals("Dinnerbone") || string.equals("Grumm")) {
        value = !(entity instanceof PlayerEntity) || ((PlayerEntity) entity).isPartVisible(PlayerModelPart.CAPE);
      } else if (entity instanceof WolfEntity && string.equals("NewGlace")) {
        value = !(entity instanceof PlayerEntity) || ((PlayerEntity) entity).isPartVisible(PlayerModelPart.CAPE);
      } else if (entity instanceof FoxEntity && (string.equals("fantomitechno") || string.equals("fant0mib0t"))) {
        value = !(entity instanceof PlayerEntity) || ((PlayerEntity) entity).isPartVisible(PlayerModelPart.CAPE);
      } else if (entity instanceof AxolotlEntity axolotl && string.equals("Hardel")
          && axolotl.getVariant().getId() == 3) {
        value = !(entity instanceof PlayerEntity) || ((PlayerEntity) entity).isPartVisible(PlayerModelPart.CAPE);
      }
    }
    cir.cancel();
    cir.setReturnValue(value);
  }
}

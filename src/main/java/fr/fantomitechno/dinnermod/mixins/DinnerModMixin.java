package fr.fantomitechno.dinnermod.mixins;

import fr.fantomitechno.dinnermod.DPlayerModelPart;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public class DinnerModMixin {

    @Inject(at = @At("TAIL"), method = "setupTransforms")
    private void dinnerBone(LivingEntity entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta, CallbackInfo ci) {
        if((entity instanceof PlayerEntity player)) {
            String string = player.getEntityName();
            if (string.equals("Pisteur_alpin")) {
                matrices.translate(0.3D, 0.1F, 0.0D);
                matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(15.0F));
            }
            else if (string.equals("Kresqle")) {
                matrices.translate(0.8D, 0.1F, 0.0D);
                matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(42.0F));
            }
            else if (string.equals("Aragorn1202")) {
                matrices.translate(0.0D, 0.1F, 0.0D);
                matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(5.0F));
            }
            else if (player.isMainPlayer() && DPlayerModelPart.DINNERBONE.getEnabled() > 1) {
                switch (DPlayerModelPart.DINNERBONE.getEnabled()) {
                    case 2 -> {
                        matrices.translate(0.0D, player.getHeight() + 0.1F, 0.0D);
                        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(90.0F));
                    }
                    case 3 -> {
                        matrices.translate(0.0D, player.getHeight() + 0.1F, 0.0D);
                        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(270.0F));
                    }
                }
            }
        } else if (entity.hasCustomName()) {
            String string = Formatting.strip(entity.getName().getString());
            if (entity instanceof GoatEntity && string.equals("Pisteur_alpin")) {
                matrices.translate(0.3D, 0.1F, 0.0D);
                matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(15.0F));
            }
            else if (entity instanceof LlamaEntity && string.equals("Kresqle")) {
                matrices.translate(0.8D, 0.1F, 0.0D);
                matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(42.0F));
            }
            else if (entity instanceof ParrotEntity && string.equals("Aragorn1202")) {
                matrices.translate(0.0D, 0.1F, 0.0D);
                matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(5.0F));
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "shouldFlipUpsideDown", cancellable = true)
    private static void shouldDinnerBone(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        boolean value = false;
        if (entity instanceof PlayerEntity player) {
            String string = player.getEntityName();
            if ((player.isMainPlayer() && DPlayerModelPart.DINNERBONE.getEnabled() == 1) || string.equals("fantomitechno") || string.equals("fant0mib0t") || string.equals("NewGlace") || string.equals("Hardel") || string.equals("Dinnerbone") || string.equals("Grumm")) {
                value = !(entity instanceof PlayerEntity) || ((PlayerEntity) entity).isPartVisible(PlayerModelPart.CAPE);
            }
        } else if (entity.hasCustomName()) {
            String string = Formatting.strip(entity.getName().getString());
            if (string.equals("Dinnerbone") || string.equals("Grumm")) {
                value = !(entity instanceof PlayerEntity) || ((PlayerEntity)entity).isPartVisible(PlayerModelPart.CAPE);
            }
            else if (entity instanceof WolfEntity && string.equals("NewGlace")) {
                value = !(entity instanceof PlayerEntity) || ((PlayerEntity)entity).isPartVisible(PlayerModelPart.CAPE);
            }
            else if (entity instanceof FoxEntity && (string.equals("fantomitechno") || string.equals("fant0mib0t"))) {
                value = !(entity instanceof PlayerEntity) || ((PlayerEntity)entity).isPartVisible(PlayerModelPart.CAPE);
            }
            else if (entity instanceof AxolotlEntity axolotl && string.equals("Hardel") && axolotl.getVariant().getId() == 3) {
                value = !(entity instanceof PlayerEntity) || ((PlayerEntity)entity).isPartVisible(PlayerModelPart.CAPE);
            }
        }
        cir.cancel();
        cir.setReturnValue(value);
    }
}

package fr.fantomitechno.dinnermod.mixins;

import fr.fantomitechno.dinnermod.DPlayerModelPart;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class DinnerModMixin {

    @Inject(at = @At("TAIL"), method = "setupTransforms")
    private void dinnerBone(LivingEntity entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta, CallbackInfo ci) {
        if(!(entity instanceof PlayerEntity player)) return;
        if(player.isMainPlayer() && DPlayerModelPart.DINNERBONE.getEnabled() > 0) {
            switch (DPlayerModelPart.DINNERBONE.getEnabled()) {
                case 1 -> {
                    matrices.translate(0D, player.getHeight() + .1, 0);
                    matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(180));
                }
                case 2 -> {
                    matrices.translate(0D, player.getHeight() + .1, 0);
                    matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(90));
                }
                case 3 -> {
                    matrices.translate(0D, player.getHeight() + .1, 0);
                    matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(270));
                }
            }
        }
    }
}

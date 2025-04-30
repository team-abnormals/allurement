package com.teamabnormals.allurement.core.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.teamabnormals.allurement.core.other.AllurementUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.layers.HorseArmorLayer;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HorseArmorLayer.class)
public class HorseArmorLayerMixin {

	@WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/MultiBufferSource;getBuffer(Lnet/minecraft/client/renderer/RenderType;)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
	private VertexConsumer render(MultiBufferSource buffer, RenderType renderType, Operation<VertexConsumer> original, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, Horse horse) {
		ItemStack stack = horse.getBodyArmorItem();
		AllurementUtil.setColorRuneTarget(stack);
		return ItemRenderer.getFoilBufferDirect(buffer, renderType, false, stack.hasFoil());
	}
}

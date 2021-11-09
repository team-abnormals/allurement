package com.teamabnormals.allurement.core.mixin;

import com.teamabnormals.allurement.core.other.AllurementUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.layers.HorseArmorLayer;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(HorseArmorLayer.class)
public class HorseArmorLayerMixin {

	@ModifyVariable(method = "render", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/renderer/MultiBufferSource;getBuffer(Lnet/minecraft/client/renderer/RenderType;)Lcom/mojang/blaze3d/vertex/VertexConsumer;", shift = At.Shift.AFTER))
	private VertexConsumer render(VertexConsumer builderIn, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, Horse horseEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		ItemStack stack = horseEntity.getArmor();
		AllurementUtil.setColorRuneTarget(stack);
		HorseArmorItem horseArmorItem = (HorseArmorItem) stack.getItem();
		return ItemRenderer.getFoilBufferDirect(bufferIn, RenderType.entityCutoutNoCull(horseArmorItem.getTexture()), false, stack.hasFoil());
	}
}

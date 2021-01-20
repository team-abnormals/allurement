package com.minecraftabnormals.allurement.core.mixin;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.LeatherHorseArmorLayer;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LeatherHorseArmorLayer.class)
public class LeatherHorseArmorLayerMixin {

	@ModifyVariable(method = "render", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/renderer/IRenderTypeBuffer;getBuffer(Lnet/minecraft/client/renderer/RenderType;)Lcom/mojang/blaze3d/vertex/IVertexBuilder;"))
	private IVertexBuilder render(IVertexBuilder builderIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, HorseEntity horseEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		ItemStack stack = horseEntity.func_213803_dV();
		HorseArmorItem horseArmorItem = (HorseArmorItem) stack.getItem();
		return ItemRenderer.getEntityGlintVertexBuilder(bufferIn, RenderType.getEntityCutoutNoCull(horseArmorItem.getArmorTexture()), false, stack.hasEffect());
	}
}

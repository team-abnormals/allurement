package com.teamabnormals.allurement.core.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.teamabnormals.allurement.core.Allurement;
import com.teamabnormals.allurement.core.AllurementConfig;
import com.teamabnormals.blueprint.common.world.storage.tracking.IDataManager;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ArrowRenderer.class)
public abstract class ArrowRendererMixin<T extends AbstractArrow> extends EntityRenderer<T> {
	private static final ResourceLocation INFINITY_ARROW_TEXTURE = new ResourceLocation(Allurement.MOD_ID, "textures/entity/projectiles/infinity_arrow.png");
	private static final ResourceLocation ARROW_TEXTURE = new ResourceLocation("textures/entity/projectiles/arrow.png");

	protected ArrowRendererMixin(EntityRendererProvider.Context renderManager) {
		super(renderManager);
	}

	@ModifyVariable(method = "render(Lnet/minecraft/world/entity/projectile/AbstractArrow;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/renderer/MultiBufferSource;getBuffer(Lnet/minecraft/client/renderer/RenderType;)Lcom/mojang/blaze3d/vertex/VertexConsumer;", shift = At.Shift.AFTER))
	private VertexConsumer render(VertexConsumer builderIn, T entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
		IDataManager manager = ((IDataManager) entityIn);
		if (manager.getValue(Allurement.INFINITY_ARROW)) {
			RenderType renderType = RenderType.entityCutout(AllurementConfig.CLIENT.infinityArrowTexture.get() ? INFINITY_ARROW_TEXTURE : ARROW_TEXTURE);
			if (AllurementConfig.CLIENT.infinityArrowGlint.get()) {
				return ItemRenderer.getFoilBufferDirect(bufferIn, renderType, false, true);
			} else {
				return bufferIn.getBuffer(renderType);
			}
		}
		return bufferIn.getBuffer(RenderType.entityCutout(this.getTextureLocation(entityIn)));
	}
}

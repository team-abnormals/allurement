package com.minecraftabnormals.allurement.core.mixin;

import com.minecraftabnormals.abnormals_core.common.world.storage.tracking.IDataManager;
import com.minecraftabnormals.allurement.core.Allurement;
import com.minecraftabnormals.allurement.core.AllurementConfig;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ArrowRenderer.class)
public abstract class ArrowRendererMixin<T extends AbstractArrowEntity> extends EntityRenderer<T> {
	private static final ResourceLocation INFINITY_ARROW_TEXTURE = new ResourceLocation(Allurement.MOD_ID, "textures/entity/projectiles/infinity_arrow.png");
	private static final ResourceLocation ARROW_TEXTURE = new ResourceLocation("textures/entity/projectiles/arrow.png");

	protected ArrowRendererMixin(EntityRendererManager renderManager) {
		super(renderManager);
	}

	@ModifyVariable(method = "render", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/renderer/IRenderTypeBuffer;getBuffer(Lnet/minecraft/client/renderer/RenderType;)Lcom/mojang/blaze3d/vertex/IVertexBuilder;"))
	private IVertexBuilder render(IVertexBuilder builderIn, T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		IDataManager manager = ((IDataManager) entityIn);
		if (manager.getValue(Allurement.INFINITY_ARROW)) {
			RenderType renderType = RenderType.getEntityCutout(AllurementConfig.CLIENT.infinityArrowTexture.get() ? INFINITY_ARROW_TEXTURE : ARROW_TEXTURE);
			if (AllurementConfig.CLIENT.infinityArrowGlint.get()) {
				return ItemRenderer.getEntityGlintVertexBuilder(bufferIn, renderType, false, true);
			} else {
				return bufferIn.getBuffer(renderType);
			}
		}
		return bufferIn.getBuffer(RenderType.getEntityCutout(this.getEntityTexture(entityIn)));
	}
}

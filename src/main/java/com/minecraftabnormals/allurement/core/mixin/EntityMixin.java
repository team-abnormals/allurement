package com.minecraftabnormals.allurement.core.mixin;

import com.minecraftabnormals.allurement.core.AllurementConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CauldronBlock;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {

	@Inject(at = @At("RETURN"), method = "isInWaterOrRain", cancellable = true)
	private void isWet(CallbackInfoReturnable<Boolean> cir) {
		Entity entity = (Entity) ((Object) this);
		BlockState state = entity.level.getBlockState(entity.blockPosition());
		if (state.is(Blocks.CAULDRON) && state.getValue(CauldronBlock.LEVEL) > 0 && AllurementConfig.COMMON.riptideWorksInCauldrons.get())
			cir.setReturnValue(true);
	}
}
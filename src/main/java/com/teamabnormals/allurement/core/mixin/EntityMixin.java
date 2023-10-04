package com.teamabnormals.allurement.core.mixin;

import com.teamabnormals.allurement.core.AllurementConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {

	@Inject(at = @At("RETURN"), method = "isInWaterOrRain", cancellable = true)
	private void isWet(CallbackInfoReturnable<Boolean> cir) {
		Entity entity = (Entity) ((Object) this);
		BlockState state = entity.level().getBlockState(entity.blockPosition());
		if (state.is(Blocks.WATER_CAULDRON) && state.getValue(LayeredCauldronBlock.LEVEL) > 0 && AllurementConfig.COMMON.riptideWorksInCauldrons.get())
			cir.setReturnValue(true);
	}
}
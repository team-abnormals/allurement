package com.teamabnormals.allurement.core.mixin;

import com.teamabnormals.allurement.core.AllurementConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EnchantmentTableBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentTableBlock.class)
public abstract class EnchantmentTableBlockMixin {

	@Inject(method = "isValidBookShelf", at = @At("RETURN"), cancellable = true)
	private static void isValidBookShelf(Level level, BlockPos pos, BlockPos offsetPos, CallbackInfoReturnable<Boolean> cir) {
		if (!cir.getReturnValue() && AllurementConfig.COMMON.nonSolidBlocksTransmitEnchantingPower.get() && level.getBlockState(pos.offset(offsetPos)).getEnchantPowerBonus(level, pos.offset(offsetPos)) != 0) {
			BlockPos transmitterPos = pos.offset(offsetPos.getX() / 2, offsetPos.getY(), offsetPos.getZ() / 2);
			if (!level.getBlockState(transmitterPos).isSolidRender(level, transmitterPos)) {
				cir.setReturnValue(true);
			}
		}
	}
}
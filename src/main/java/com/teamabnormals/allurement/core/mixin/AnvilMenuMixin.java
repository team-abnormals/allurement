package com.teamabnormals.allurement.core.mixin;

import com.teamabnormals.allurement.core.AllurementConfig;
import net.minecraft.world.inventory.AnvilMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin {

	@ModifyConstant(method = "createResult", constant = @Constant(intValue = 40))
	private int createResult(int value) {
		return AllurementConfig.COMMON.removeTooExpensive.get() ? Integer.MAX_VALUE : value;
	}
}
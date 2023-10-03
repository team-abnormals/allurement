package com.teamabnormals.allurement.core.mixin;

import com.teamabnormals.allurement.core.AllurementConfig;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(AnvilScreen.class)
public abstract class AnvilScreenMixin {

	@ModifyConstant(method = "renderLabels", require = 0, constant = @Constant(intValue = 40))
	private int renderLabels(int value) {
		return AllurementConfig.COMMON.removeTooExpensive.get() ? Integer.MAX_VALUE : value;
	}
}
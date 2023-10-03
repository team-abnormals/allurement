package com.teamabnormals.allurement.core.mixin;

import com.teamabnormals.allurement.core.AllurementConfig;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin {

	@Shadow
	@Final
	private DataSlot cost;

	@ModifyConstant(method = "createResult", require = 0, constant = @Constant(intValue = 40))
	private int createResult(int value) {
		return AllurementConfig.COMMON.removeTooExpensive.get() ? Integer.MAX_VALUE : value;
	}

	@Inject(method = "createResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/DataSlot;get()I", ordinal = 1), locals = LocalCapture.CAPTURE_FAILHARD)
	private void createResult(CallbackInfo ci, ItemStack stack, int i, int j, int k) {
		int anvilCostCap = AllurementConfig.COMMON.anvilCostCap.get();
		if (AllurementConfig.COMMON.capAnvilCosts.get() && cost.get() > anvilCostCap) {
			cost.set(anvilCostCap);
		}

		if (AllurementConfig.COMMON.cheapItemRenaming.get() && k == i && k > 0) {
			cost.set(1);
		}
	}
}
package com.minecraftabnormals.allurement.core.mixin;

import com.minecraftabnormals.allurement.core.AllurementConfig;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slot.class)
public class SlotMixin {

	@Inject(method = "mayPickup", at = @At("RETURN"), cancellable = true)
	private void canTakeStack(PlayerEntity playerIn, CallbackInfoReturnable<Boolean> cir) {
		Slot slot = (Slot) (Object) this;
		ItemStack stack = slot.getItem();
		if (AllurementConfig.COMMON.enchantableHorseArmor.get() && stack.getItem() instanceof HorseArmorItem) {
			if (slot.index == 1 && slot.x == 8 && slot.y == 36 && slot.mayPlace(stack) && slot.getMaxStackSize() == 1) {
				if (!playerIn.isCreative() && EnchantmentHelper.hasBindingCurse(stack))
					cir.setReturnValue(false);
			}
		}
	}
}

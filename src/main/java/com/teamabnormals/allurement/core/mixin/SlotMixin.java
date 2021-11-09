package com.teamabnormals.allurement.core.mixin;

import com.teamabnormals.allurement.core.AllurementConfig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slot.class)
public class SlotMixin {

	@Inject(method = "mayPickup", at = @At("RETURN"), cancellable = true)
	private void canTakeStack(Player playerIn, CallbackInfoReturnable<Boolean> cir) {
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

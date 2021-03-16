package com.minecraftabnormals.allurement.core.mixin;

import com.minecraftabnormals.allurement.core.other.AllurementTags.Enchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public class EnchantmentMixin {

	@Inject(method = "canApply", at = @At("RETURN"), cancellable = true)
	private void canApply(ItemStack stack, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
		Enchantment enchantment = (Enchantment) (Object) this;
		if (stack.getItem() instanceof HorseArmorItem && enchantment.isIn(Enchantments.UNUSABLE_ON_HORSE_ARMOR))
			callbackInfoReturnable.setReturnValue(false);
	}
}

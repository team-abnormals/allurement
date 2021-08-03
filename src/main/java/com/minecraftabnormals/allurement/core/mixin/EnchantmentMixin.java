package com.minecraftabnormals.allurement.core.mixin;

import com.minecraftabnormals.allurement.core.AllurementConfig;
import com.minecraftabnormals.allurement.core.other.AllurementTags;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public class EnchantmentMixin {

	@Inject(method = "canEnchant", at = @At("RETURN"), cancellable = true)
	private void canApply(ItemStack stack, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
		Enchantment enchantment = (Enchantment) (Object) this;
		if (AllurementConfig.COMMON.enchantableHorseArmor.get() && stack.getItem() instanceof HorseArmorItem && enchantment.isIn(AllurementTags.Enchantments.UNUSABLE_ON_HORSE_ARMOR))
			callbackInfoReturnable.setReturnValue(false);

		if (AllurementConfig.COMMON.disableProtection.get() && enchantment == Enchantments.ALL_DAMAGE_PROTECTION)
			callbackInfoReturnable.setReturnValue(false);

	}

	@Inject(method = "canApplyAtEnchantingTable", at = @At("RETURN"), cancellable = true, remap = false)
	private void canApplyAtEnchantingTable(ItemStack stack, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
		Enchantment enchantment = (Enchantment) (Object) this;
		if (AllurementConfig.COMMON.enchantableHorseArmor.get() && stack.getItem() instanceof HorseArmorItem && enchantment.isIn(AllurementTags.Enchantments.UNUSABLE_ON_HORSE_ARMOR))
			callbackInfoReturnable.setReturnValue(false);

		if (AllurementConfig.COMMON.disableProtection.get() && enchantment == Enchantments.ALL_DAMAGE_PROTECTION)
			callbackInfoReturnable.setReturnValue(false);
	}
}

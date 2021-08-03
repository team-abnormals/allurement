package com.minecraftabnormals.allurement.core.mixin;

import com.minecraftabnormals.allurement.core.AllurementConfig;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = {
		"net.minecraft.enchantment.EnchantmentType$1",
		"net.minecraft.enchantment.EnchantmentType$2",
		"net.minecraft.enchantment.EnchantmentType$3",
		"net.minecraft.enchantment.EnchantmentType$4",
		"net.minecraft.enchantment.EnchantmentType$5",
		"net.minecraft.enchantment.EnchantmentType$12",
		"net.minecraft.enchantment.EnchantmentType$14"
})
public class EnchantmentTypeMixin {

	@Inject(method = "canEnchant(Lnet/minecraft/item/Item;)Z", at = @At("HEAD"), cancellable = true)
	private void canEnchantItem(Item item, CallbackInfoReturnable<Boolean> cir) {
		if (AllurementConfig.COMMON.enchantableHorseArmor.get() && item instanceof HorseArmorItem) {
			cir.setReturnValue(true);
		}
	}
}
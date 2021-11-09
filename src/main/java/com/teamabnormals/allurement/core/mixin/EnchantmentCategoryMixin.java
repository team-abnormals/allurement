package com.teamabnormals.allurement.core.mixin;

import com.teamabnormals.allurement.core.AllurementConfig;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = {
		"net/minecraft/world/item/enchantment/EnchantmentCategory$1",
		"net/minecraft/world/item/enchantment/EnchantmentCategory$2",
		"net/minecraft/world/item/enchantment/EnchantmentCategory$3",
		"net/minecraft/world/item/enchantment/EnchantmentCategory$4",
		"net/minecraft/world/item/enchantment/EnchantmentCategory$5",
		"net/minecraft/world/item/enchantment/EnchantmentCategory$12",
		"net/minecraft/world/item/enchantment/EnchantmentCategory$14"
})
public abstract class EnchantmentCategoryMixin {

	@Inject(method = "canEnchant(Lnet/minecraft/world/item/Item;)Z", at = @At("HEAD"), cancellable = true)
	private void canEnchant(Item item, CallbackInfoReturnable<Boolean> cir) {
		if (AllurementConfig.COMMON.enchantableHorseArmor.get() && item instanceof HorseArmorItem) {
			cir.setReturnValue(true);
		}
	}
}
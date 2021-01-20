package com.minecraftabnormals.allurement.core.mixin;

import com.minecraftabnormals.allurement.core.Allurement;
import com.minecraftabnormals.allurement.core.AllurementConfig;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(Item.class)
public abstract class ItemMixin extends ForgeRegistryEntry<Item> {

	@Inject(at = @At("HEAD"), method = "getItemEnchantability", cancellable = true)
	private void getItemEnchantability(CallbackInfoReturnable<Integer> cir) {
		if (AllurementConfig.COMMON.enchantingHorseArmor.get() && (Object) this instanceof HorseArmorItem) {
			Map<String, Integer> map = Allurement.getEnchantibilityMap();
			String name = this.getRegistryName().toString();
			if (map.containsKey(name)) {
				cir.setReturnValue(map.get(name));
			}
		}
	}

	@Inject(at = @At("HEAD"), method = "isEnchantable", cancellable = true)
	private void isEnchantable(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		if (AllurementConfig.COMMON.enchantingHorseArmor.get() && (Object) this instanceof HorseArmorItem) {
			cir.setReturnValue(true);
		}
	}
}
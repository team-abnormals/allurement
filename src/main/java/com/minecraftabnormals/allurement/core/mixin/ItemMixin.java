package com.minecraftabnormals.allurement.core.mixin;

import com.minecraftabnormals.allurement.core.AllurementConfig;
import com.minecraftabnormals.allurement.core.other.AllurementUtil;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin extends ForgeRegistryEntry<Item> {

	@Inject(at = @At("HEAD"), method = "getItemEnchantability", cancellable = true)
	private void getItemEnchantability(CallbackInfoReturnable<Integer> cir) {
		if (AllurementConfig.COMMON.enchantableHorseArmor.get() && (Object) this instanceof HorseArmorItem) {
			String name = this.getRegistryName().toString();
			if (AllurementUtil.ENCHANTABILITY_MAP.containsKey(name)) {
				cir.setReturnValue(AllurementUtil.ENCHANTABILITY_MAP.get(name));
			} else {
				cir.setReturnValue(1);
			}
		}
	}

	@Inject(at = @At("HEAD"), method = "isEnchantable", cancellable = true)
	private void isEnchantable(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		if (AllurementConfig.COMMON.enchantableHorseArmor.get() && (Object) this instanceof HorseArmorItem) {
			cir.setReturnValue(true);
		}
	}
}
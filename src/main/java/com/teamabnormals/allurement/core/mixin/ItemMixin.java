package com.teamabnormals.allurement.core.mixin;

import com.teamabnormals.allurement.core.AllurementConfig;
import com.teamabnormals.allurement.core.other.AllurementUtil;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin {

	@Inject(at = @At("HEAD"), method = "getEnchantmentValue", cancellable = true)
	private void getItemEnchantability(CallbackInfoReturnable<Integer> cir) {
		Item item = (Item) (Object) this;
		if (AllurementConfig.COMMON.enchantableHorseArmor.get() && item instanceof HorseArmorItem) {
			String name = ForgeRegistries.ITEMS.getKey(item).toString();
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
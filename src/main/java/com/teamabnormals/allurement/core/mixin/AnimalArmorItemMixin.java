package com.teamabnormals.allurement.core.mixin;

import com.teamabnormals.allurement.core.AllurementConfig;
import com.teamabnormals.allurement.core.other.tags.AllurementItemTags;
import net.minecraft.world.item.AnimalArmorItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnimalArmorItem.class)
public abstract class AnimalArmorItemMixin {

	@Inject(at = @At("HEAD"), method = "isEnchantable", cancellable = true)
	private void isEnchantable(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		if (AllurementConfig.COMMON.enchantableAnimalArmor.get() && stack.is(AllurementItemTags.ANIMAL_ARMOR_ENCHANTABLE)) {
			cir.setReturnValue(true);
		}
	}
}
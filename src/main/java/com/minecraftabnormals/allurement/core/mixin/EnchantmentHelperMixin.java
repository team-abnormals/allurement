package com.minecraftabnormals.allurement.core.mixin;

import com.minecraftabnormals.allurement.core.AllurementConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {

	@Inject(method = "getEnchantmentLevel", at = @At("HEAD"), cancellable = true)
	private static void getEnchantmentLevel(Enchantment enchantmentIn, LivingEntity entityIn, CallbackInfoReturnable<Integer> cir) {
		if (AllurementConfig.COMMON.enchantableHorseArmor.get() && entityIn instanceof HorseEntity) {
			ItemStack stack = ((HorseEntity) entityIn).getArmor();
			if (stack.getItem() instanceof HorseArmorItem) {
				cir.setReturnValue(EnchantmentHelper.getItemEnchantmentLevel(enchantmentIn, stack));
			}
		}
	}
}
package com.minecraftabnormals.allurement.core.mixin;

import com.minecraftabnormals.allurement.core.AllurementConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Consumer;

@Mixin(LivingEntity.class)
public class LivingEntityMixin<T extends LivingEntity> {

	@Redirect(method = "tryAddSoulSpeed()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;hurtAndBreak(ILnet/minecraft/entity/LivingEntity;Ljava/util/function/Consumer;)V"))
	private void damageItem(ItemStack itemStack, int amount, T entityIn, Consumer<T> onBroken) {
		if (!AllurementConfig.COMMON.soulSpeedHurtsMore.get())
			itemStack.hurtAndBreak(amount, entityIn, onBroken);
	}
}
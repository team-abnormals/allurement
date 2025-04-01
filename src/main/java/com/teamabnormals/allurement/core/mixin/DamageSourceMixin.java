package com.teamabnormals.allurement.core.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.teamabnormals.allurement.core.registry.AllurementEnchantmentEffects;
import com.teamabnormals.allurement.core.registry.datapack.AllurementDamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import javax.annotation.Nullable;

@Mixin(DamageSource.class)
public abstract class DamageSourceMixin {

	@Shadow
	@Final
	@Nullable
	private Entity directEntity;

	@WrapOperation(method = "getLocalizedDeathMessage", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getMainHandItem()Lnet/minecraft/world/item/ItemStack;"))
	private ItemStack getLocalizedDeathMessage(LivingEntity entity, Operation<ItemStack> original) {
		if (((DamageSource) (Object) this).is(AllurementDamageTypes.SHOCKWAVE)) {
			for (EquipmentSlot slot : new EquipmentSlot[]{EquipmentSlot.FEET, EquipmentSlot.LEGS, EquipmentSlot.CHEST, EquipmentSlot.HEAD, EquipmentSlot.BODY}) {
				if (this.directEntity instanceof LivingEntity direct) entity = direct;
				ItemStack stack = entity.getItemBySlot(slot);
				if (EnchantmentHelper.has(stack, AllurementEnchantmentEffects.SHOCKWAVE.get())) {
					return stack;
				}
			}
		}

		return original.call(entity);
	}
}
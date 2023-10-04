package com.teamabnormals.allurement.core.mixin;

import com.teamabnormals.allurement.core.other.AllurementDamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DamageSource.class)
public abstract class DamageSourceMixin {

	@Redirect(method = "getLocalizedDeathMessage", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getMainHandItem()Lnet/minecraft/world/item/ItemStack;"))
	private ItemStack getLocalizedDeathMessage(LivingEntity entity) {
		if (((DamageSource) (Object) this).is(AllurementDamageTypes.SHOCKWAVE)) {
			return entity.getItemBySlot(EquipmentSlot.FEET);
		}

		return entity.getMainHandItem();
	}
}
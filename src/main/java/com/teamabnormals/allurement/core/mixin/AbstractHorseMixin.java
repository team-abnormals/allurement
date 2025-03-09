package com.teamabnormals.allurement.core.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.CommonHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractHorse.class)
public abstract class AbstractHorseMixin extends Animal {

	protected AbstractHorseMixin(EntityType<? extends Animal> type, Level worldIn) {
		super(type, worldIn);
	}

	@Inject(method = "causeFallDamage", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/entity/animal/horse/AbstractHorse;calculateFallDamage(FF)I", shift = Shift.AFTER), cancellable = true)
	private void causeFallDamage(float distance, float multiplier, DamageSource source, CallbackInfoReturnable<Boolean> cir, @Local LocalIntRef i) {
		float[] ret = CommonHooks.onLivingFall(this, distance, multiplier);
		if (ret == null) cir.setReturnValue(false);
		distance = ret[0];
		multiplier = ret[1];

		i.set(this.calculateFallDamage(distance, multiplier));
	}
}
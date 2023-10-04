package com.teamabnormals.allurement.core.mixin;

import com.teamabnormals.allurement.core.other.AllurementDamageTypes;
import com.teamabnormals.allurement.core.registry.AllurementEnchantments;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractHorse.class)
public abstract class AbstractHorseMixin extends Animal {

	protected AbstractHorseMixin(EntityType<? extends Animal> type, Level worldIn) {
		super(type, worldIn);
	}

	@Inject(method = "causeFallDamage", at = @At("RETURN"))
	private void causeFallDamage(float distance, float damageMultiplier, DamageSource source, CallbackInfoReturnable<Boolean> cir) {
		if (cir.getReturnValue()) {
			int level = EnchantmentHelper.getTagEnchantmentLevel(AllurementEnchantments.SHOCKWAVE.get(), this.getItemBySlot(EquipmentSlot.CHEST));
			int damage = Mth.ceil((distance * 0.5F - 3.0F) * damageMultiplier);

			if (level > 0 && damage > 0) {
				for (LivingEntity target : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(level, 0.0D, level))) {
					if (this != target && !this.getPassengers().contains(target))
						target.hurt(AllurementDamageTypes.shockwave(this.level(), this, this.getFirstPassenger()), damage);
				}

				if (!this.level().isClientSide()) {
					((ServerLevel) this.level()).sendParticles(ParticleTypes.CLOUD, this.getX(), this.getY(), this.getZ(), 200, level, 0.5, level, 0);
				}
			}
		}
	}
}
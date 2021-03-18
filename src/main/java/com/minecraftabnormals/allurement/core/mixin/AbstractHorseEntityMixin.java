package com.minecraftabnormals.allurement.core.mixin;

import com.minecraftabnormals.allurement.core.other.AllurementDamageSources;
import com.minecraftabnormals.allurement.core.registry.AllurementEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractHorseEntity.class)
public abstract class AbstractHorseEntityMixin extends AnimalEntity {

	protected AbstractHorseEntityMixin(EntityType<? extends AnimalEntity> type, World worldIn) {
		super(type, worldIn);
	}

	@Inject(at = @At("RETURN"), method = "onLivingFall", cancellable = true)
	private void onLivingFall(float distance, float damageMultiplier, CallbackInfoReturnable<Boolean> cir) {
		if (cir.getReturnValue()) {
			int level = EnchantmentHelper.getEnchantmentLevel(AllurementEnchantments.SHOCKWAVE.get(), this.getItemStackFromSlot(EquipmentSlotType.CHEST));
			int damage = MathHelper.ceil((distance * 0.5F - 3.0F) * damageMultiplier);

			if (level > 0 && damage > 0) {
				for (LivingEntity target : world.getEntitiesWithinAABB(LivingEntity.class, this.getBoundingBox().grow(level, 0.0D, level))) {
					if (this != target && !this.isRidingOrBeingRiddenBy(target))
						target.attackEntityFrom(AllurementDamageSources.causeShockwaveDamage(this), damage);
				}

				if (world instanceof ServerWorld) {
					((ServerWorld) world).spawnParticle(ParticleTypes.CLOUD, this.getPosX(), this.getPosY(), this.getPosZ(), 200, level, 0.5, level, 0);
				}
			}
		}
	}
}
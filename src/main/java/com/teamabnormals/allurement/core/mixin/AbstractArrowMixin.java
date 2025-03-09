package com.teamabnormals.allurement.core.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin extends Entity {

	public AbstractArrowMixin(EntityType<?> entityTypeIn, Level worldIn) {
		super(entityTypeIn, worldIn);
	}

	@Inject(at = @At("TAIL"), method = "doKnockback", locals = LocalCapture.CAPTURE_FAILEXCEPTION)
	private void onEntityHit(LivingEntity entity, DamageSource damageSource, CallbackInfo ci, double d0) {
		if (d0 < 0.0D) {
			double d1 = 1.0D - Math.max(0.0D, entity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
			Vec3 vec3 = this.getDeltaMovement().normalize().multiply(1.0, 0.5, 1.0).scale(d0 * d1);
			if (vec3.lengthSqr() > 0.0D) {
				entity.push(vec3.x, (vec3.y < 0.0D) ? Math.min(-0.1D, vec3.y) : Math.max(0.1D, vec3.y), vec3.z);
			}
		}
	}
}
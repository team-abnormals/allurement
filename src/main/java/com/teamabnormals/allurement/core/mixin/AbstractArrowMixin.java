package com.teamabnormals.allurement.core.mixin;

import com.teamabnormals.allurement.core.AllurementConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin extends Entity {

	@Shadow
	private int knockback;

	public AbstractArrowMixin(EntityType<?> entityTypeIn, Level worldIn) {
		super(entityTypeIn, worldIn);
	}

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/AbstractArrow;getPierceLevel()B", shift = Shift.BEFORE), method = "onHitEntity")
	private void onEntityHit(EntityHitResult result, CallbackInfo ci) {
		if (result.getEntity() instanceof LivingEntity && this.knockback < 0) {
			double horizontalForce = this.knockback * AllurementConfig.COMMON.reelingHorizontalFactor.get();
			double verticalForce = this.knockback * AllurementConfig.COMMON.reelingVerticalFactor.get();
			Vec3 vector3d = this.getDeltaMovement().normalize().multiply(horizontalForce, verticalForce, horizontalForce);
			if (vector3d.lengthSqr() > 0.0D) {
				verticalForce = Math.max(0.1D, vector3d.y);
				if (vector3d.y < 0.0D)
					verticalForce = Math.min(-0.1D, vector3d.y);
				result.getEntity().push(vector3d.x, verticalForce, vector3d.z);
			}
		}
	}
}
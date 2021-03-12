package com.minecraftabnormals.allurement.core.mixin;

import com.minecraftabnormals.allurement.core.AllurementConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrowEntity.class)
public abstract class AbstractArrowEntityMixin extends Entity {

	@Shadow
	private int knockbackStrength;

	public AbstractArrowEntityMixin(EntityType<?> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
	}

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/AbstractArrowEntity;getPierceLevel()B", shift = Shift.BEFORE), method = "onEntityHit", cancellable = true)
	private void onEntityHit(EntityRayTraceResult result, CallbackInfo ci) {
		if (result.getEntity() instanceof LivingEntity && this.knockbackStrength < 0) {
			double horizontalForce = this.knockbackStrength * AllurementConfig.COMMON.reelingHorizontalFactor.get();
			double verticalForce = this.knockbackStrength * AllurementConfig.COMMON.reelingVerticalFactor.get();
			Vector3d vector3d = this.getMotion().normalize().mul(horizontalForce, verticalForce, horizontalForce);
			if (vector3d.lengthSquared() > 0.0D) {
				verticalForce = Math.max(0.1D, vector3d.y);
				if (vector3d.y < 0.0D)
					verticalForce = Math.min(-0.1D, vector3d.y);
				result.getEntity().addVelocity(vector3d.x, verticalForce, vector3d.z);
			}
		}
	}
}
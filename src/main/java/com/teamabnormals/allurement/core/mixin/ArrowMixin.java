package com.teamabnormals.allurement.core.mixin;

import com.teamabnormals.allurement.core.other.AllurementTrackedData;
import com.teamabnormals.blueprint.common.world.storage.tracking.IDataManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Arrow.class)
public class ArrowMixin {

	@Inject(method = "doPostHurtEffects", at = @At("HEAD"))
	private void doPostHurtEffects(LivingEntity entity, CallbackInfo ci) {
		IDataManager manager = (IDataManager) this;
		ListTag listTag = manager.getValue(AllurementTrackedData.ARROW_EFFECTS);
		if (!listTag.isEmpty()) {
			for (int i = 0; i < listTag.size(); i++) {
				CompoundTag tag = listTag.getCompound(i);
				MobEffectInstance effect = MobEffectInstance.load(tag);
				if (effect != null) {
					entity.addEffect(effect, ((Projectile) (Object) this).getEffectSource());
				}
			}
		}
	}
}

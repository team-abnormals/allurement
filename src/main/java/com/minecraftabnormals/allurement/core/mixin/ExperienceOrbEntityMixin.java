package com.minecraftabnormals.allurement.core.mixin;

import com.minecraftabnormals.allurement.core.AllurementConfig;
import com.minecraftabnormals.allurement.core.other.AllurementUtil;
import com.minecraftabnormals.allurement.core.registry.AllurementEnchantments;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ExperienceOrbEntity.class)
public class ExperienceOrbEntityMixin {

	@Shadow
	public int value;

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;take(Lnet/minecraft/entity/Entity;I)V", shift = At.Shift.AFTER), method = "playerTouch", cancellable = true)
	private void onCollideWithPlayer(PlayerEntity player, CallbackInfo ci) {
		int count = AllurementUtil.getTotalEnchantmentLevel(AllurementEnchantments.ALLEVIATING.get(), player, EquipmentSlotType.Group.ARMOR);
		if (count > 0) {
			float factor = AllurementConfig.COMMON.alleviatingHealingFactor.get().floatValue() * count;
			float i = Math.min(this.value * factor, player.getMaxHealth() - player.getHealth());
			this.value -= Math.round(i / factor);
			player.heal(i);
		}
	}
}
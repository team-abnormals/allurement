package com.teamabnormals.allurement.core.mixin;

import com.teamabnormals.allurement.core.AllurementConfig;
import com.teamabnormals.allurement.core.other.AllurementUtil;
import com.teamabnormals.allurement.core.registry.AllurementEnchantments;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ExperienceOrb.class)
public class ExperienceOrbMixin {

	@Shadow
	public int value;

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;take(Lnet/minecraft/world/entity/Entity;I)V", shift = At.Shift.AFTER), method = "playerTouch", cancellable = true)
	private void onCollideWithPlayer(Player player, CallbackInfo ci) {
		int count = AllurementUtil.getTotalEnchantmentLevel(AllurementEnchantments.ALLEVIATING.get(), player, EquipmentSlot.Type.ARMOR);
		if (count > 0) {
			float factor = AllurementConfig.COMMON.alleviatingHealingFactor.get().floatValue() * count;
			float i = Math.min(this.value * factor, player.getMaxHealth() - player.getHealth());
			this.value -= Math.round(i / factor);
			player.heal(i);
		}
	}
}
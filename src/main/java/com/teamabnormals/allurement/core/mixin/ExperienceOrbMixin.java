package com.teamabnormals.allurement.core.mixin;

import com.teamabnormals.allurement.core.registry.AllurementEnchantmentEffects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ExperienceOrb.class)
public class ExperienceOrbMixin {

	@Shadow
	public int value;

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;take(Lnet/minecraft/world/entity/Entity;I)V", shift = At.Shift.AFTER), method = "playerTouch")
	private void onCollideWithPlayer(Player player, CallbackInfo ci) {
		if (player.level() instanceof ServerLevel serverLevel) {
			MutableFloat mutablefloat = new MutableFloat();
			EnchantmentHelper.runIterationOnEquipment(player, (ench, eLevel, use) -> {
				ench.value().modifyEntityFilteredValue(AllurementEnchantmentEffects.HEAL_WITH_XP.get(), serverLevel, eLevel, use.itemStack(), player, mutablefloat);
			});

			float factor = Math.max(0, mutablefloat.floatValue());
			if (factor > 0) {
				float i = Math.min(this.value * factor, player.getMaxHealth() - player.getHealth());
				this.value -= Math.round(i / factor);
				player.heal(i);
			}
		}
	}
}
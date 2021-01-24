package com.minecraftabnormals.allurement.core.mixin;

import com.minecraftabnormals.allurement.core.registry.AllurementEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
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
	public int xpValue;

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;onItemPickup(Lnet/minecraft/entity/Entity;I)V", shift = At.Shift.AFTER), method = "onCollideWithPlayer", cancellable = true)
	private void onCollideWithPlayer(PlayerEntity player, CallbackInfo ci) {
		int count = 0;
		for (EquipmentSlotType slot : EquipmentSlotType.values())
			count += EnchantmentHelper.getEnchantmentLevel(AllurementEnchantments.ALLEVIATING.get(), player.getItemStackFromSlot(slot));
		if (count > 0) {
			float i = Math.min(this.xpValue * 0.25F * count, player.getMaxHealth() - player.getHealth());
			this.xpValue -= i * 4.0F;
			player.heal(i);
		}
	}
}
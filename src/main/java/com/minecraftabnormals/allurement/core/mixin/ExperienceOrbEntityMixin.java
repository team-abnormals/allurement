package com.minecraftabnormals.allurement.core.mixin;

import com.minecraftabnormals.allurement.core.registry.AllurementEnchantments;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(ExperienceOrbEntity.class)
public class ExperienceOrbEntityMixin {

	@Shadow
	public int xpValue;

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;onItemPickup(Lnet/minecraft/entity/Entity;I)V", shift = At.Shift.AFTER), method = "onCollideWithPlayer", cancellable = true)
	private void onCollideWithPlayer(PlayerEntity player, CallbackInfo ci) {
		Map<EquipmentSlotType, ItemStack> map = AllurementEnchantments.ALLEVIATING.get().getEntityEquipment(player);
		if (!map.isEmpty()) {
			float i = Math.min(this.xpValue * 0.25F * map.size(), player.getMaxHealth() - player.getHealth());
			this.xpValue -= i * 4.0F;
			player.heal(i);
		}
	}
}
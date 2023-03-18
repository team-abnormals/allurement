package com.teamabnormals.allurement.core.mixin;

import com.teamabnormals.allurement.core.registry.AllurementEnchantments;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RandomStrollGoal.class)
public abstract class RandomStrollGoalMixin {

	@Shadow
	@Final
	protected PathfinderMob mob;

	@Inject(method = "canUse", at = @At("HEAD"), cancellable = true)
	private void canUse(CallbackInfoReturnable<Boolean> cir) {
		if (this.mob instanceof AbstractHorse horse && EnchantmentHelper.getTagEnchantmentLevel(AllurementEnchantments.OBEDIENCE.get(), horse.getItemBySlot(EquipmentSlot.CHEST)) != 0) {
			cir.setReturnValue(false);
		}
	}
}
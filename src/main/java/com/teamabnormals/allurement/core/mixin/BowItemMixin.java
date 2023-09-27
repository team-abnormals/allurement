package com.teamabnormals.allurement.core.mixin;

import com.teamabnormals.allurement.core.other.AllurementTrackedData;
import com.teamabnormals.blueprint.common.world.storage.tracking.IDataManager;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(BowItem.class)
public class BowItemMixin {

	@Inject(method = "releaseUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
	private void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft, CallbackInfo ci, Player player, boolean creativeOrInfEnch, ItemStack ammo, int pullTime, float velocity, boolean creativeOrInfinite, ArrowItem arrow, AbstractArrow arrowEntity) {
		IDataManager manager = (IDataManager) arrowEntity;
		boolean infinite = arrow.isInfinite(ammo, stack, player);
		manager.setValue(AllurementTrackedData.INFINITY_ARROW, infinite);
	}
}
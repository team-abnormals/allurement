package com.minecraftabnormals.allurement.core.mixin;

import com.minecraftabnormals.abnormals_core.common.world.storage.tracking.IDataManager;
import com.minecraftabnormals.allurement.core.Allurement;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(BowItem.class)
public class BowItemMixin {

	@Inject(method = "onPlayerStoppedUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;addEntity(Lnet/minecraft/entity/Entity;)Z", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
	private void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft, CallbackInfo ci, PlayerEntity player, boolean creativeOrInfEnch, ItemStack ammo, int pullTime, float velocity, boolean creativeOrInfinite, ArrowItem arrow, AbstractArrowEntity arrowEntity) {
		IDataManager manager = (IDataManager) arrowEntity;
		boolean infinite = arrow.isInfinite(ammo, stack, player);
		manager.setValue(Allurement.INFINITY_ARROW, infinite);
	}

}
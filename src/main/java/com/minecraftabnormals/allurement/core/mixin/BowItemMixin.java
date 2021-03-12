package com.minecraftabnormals.allurement.core.mixin;

import com.minecraftabnormals.abnormals_core.common.world.storage.tracking.IDataManager;
import com.minecraftabnormals.allurement.core.Allurement;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BowItem.class)
public class BowItemMixin {

	@ModifyVariable(method = "onPlayerStoppedUsing", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/item/ArrowItem;createArrow(Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/LivingEntity;)Lnet/minecraft/entity/projectile/AbstractArrowEntity;"))
	private AbstractArrowEntity onPlayerStoppedUsing(AbstractArrowEntity arrowEntity, ItemStack stack, World worldIn, LivingEntity entity, int timeLeft) {
		IDataManager manager = (IDataManager) arrowEntity;
		ItemStack arrow = entity.findAmmo(stack);
		int infinityLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack);
		if (arrow.getItem() instanceof ArrowItem && ((ArrowItem) arrow.getItem()).isInfinite(arrow, stack, (PlayerEntity) entity)) {
			manager.setValue(Allurement.INFINITY_ARROW, infinityLevel > 0);
		}

		return arrowEntity;
	}
}
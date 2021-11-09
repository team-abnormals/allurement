package com.teamabnormals.allurement.core.mixin;

import com.teamabnormals.allurement.core.registry.AllurementEnchantments;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(CrossbowItem.class)
public abstract class CrossbowItemMixin {

	@ModifyVariable(method = "getArrow", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/item/ArrowItem;createArrow(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;)Lnet/minecraft/world/entity/projectile/AbstractArrow;"))
	private static AbstractArrow getArrow(AbstractArrow arrow, Level worldIn, LivingEntity shooter, ItemStack crossbow, ItemStack ammo) {
		int level = EnchantmentHelper.getItemEnchantmentLevel(AllurementEnchantments.REELING.get(), crossbow);
		if (level > 0) {
			arrow.setKnockback(-level);
		}
		return arrow;
	}
}
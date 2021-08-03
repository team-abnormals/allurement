package com.minecraftabnormals.allurement.core.mixin;

import com.minecraftabnormals.allurement.core.registry.AllurementEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(CrossbowItem.class)
public abstract class CrossbowItemMixin {

	@ModifyVariable(method = "getArrow", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/item/ArrowItem;createArrow(Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/LivingEntity;)Lnet/minecraft/entity/projectile/AbstractArrowEntity;"))
	private static AbstractArrowEntity getArrow(AbstractArrowEntity arrow, World worldIn, LivingEntity shooter, ItemStack crossbow, ItemStack ammo) {
		int level = EnchantmentHelper.getItemEnchantmentLevel(AllurementEnchantments.REELING.get(), crossbow);
		if (level > 0) {
			arrow.setKnockback(-level);
		}
		return arrow;
	}
}
package com.teamabnormals.allurement.core.mixin;

import com.teamabnormals.allurement.core.other.AllurementTrackedData;
import com.teamabnormals.allurement.core.registry.AllurementEnchantmentEffects;
import com.teamabnormals.blueprint.common.world.storage.tracking.IDataManager;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ProjectileWeaponItem.class)
public class ProjectileWeaponItemMixin {

	@ModifyVariable(method = "createProjectile", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/item/ArrowItem;createArrow(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/world/entity/projectile/AbstractArrow;"))
	private AbstractArrow createProjectile(AbstractArrow abstractArrow, Level level, LivingEntity shooter, ItemStack weapon, ItemStack ammo, boolean isCrit) {
		if (ammo.is(Items.ARROW) && EnchantmentHelper.has(weapon, AllurementEnchantmentEffects.RENDER_INFINITY_ARROW.get())) {
			((IDataManager) abstractArrow).setValue(AllurementTrackedData.INFINITY_ARROW, true);
		}

		return abstractArrow;
	}
}
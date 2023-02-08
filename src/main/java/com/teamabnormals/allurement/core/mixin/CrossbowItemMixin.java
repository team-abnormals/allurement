package com.teamabnormals.allurement.core.mixin;

import com.teamabnormals.allurement.core.registry.AllurementEnchantments;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.apache.commons.compress.utils.Lists;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Collection;

@Mixin(CrossbowItem.class)
public abstract class CrossbowItemMixin {

	@ModifyVariable(method = "getArrow", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/item/ArrowItem;createArrow(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;)Lnet/minecraft/world/entity/projectile/AbstractArrow;"))
	private static AbstractArrow getArrow(AbstractArrow abstractArrow, Level worldIn, LivingEntity shooter, ItemStack crossbow, ItemStack ammo) {
		int level = EnchantmentHelper.getTagEnchantmentLevel(AllurementEnchantments.REELING.get(), crossbow);
		if (level > 0) {
			abstractArrow.setKnockback(-level);
		}

		int ailmentsLevel = EnchantmentHelper.getTagEnchantmentLevel(AllurementEnchantments.SPREAD_OF_AILMENTS.get(), crossbow);
		if (ailmentsLevel > 0 && abstractArrow instanceof Arrow arrow) {
			Collection<MobEffectInstance> mobEffects = Lists.newArrayList();
			shooter.getActiveEffects().forEach(effect -> {
				if (!effect.getEffect().isInstantenous() && effect.getDuration() > 100 && !effect.isAmbient()) {
					mobEffects.add(new MobEffectInstance(effect.getEffect(), 140 * ailmentsLevel, effect.getAmplifier()));
				}
			});
			arrow.setEffectsFromItem(PotionUtils.setCustomEffects(new ItemStack(Items.TIPPED_ARROW), mobEffects));
			return arrow;
		}

		return abstractArrow;
	}
}
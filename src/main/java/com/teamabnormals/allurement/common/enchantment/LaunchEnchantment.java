package com.teamabnormals.allurement.common.enchantment;

import com.teamabnormals.allurement.core.AllurementConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.KnockbackEnchantment;

public class LaunchEnchantment extends Enchantment {
	public LaunchEnchantment(Enchantment.Rarity rarityIn, EquipmentSlot... slots) {
		super(rarityIn, EnchantmentCategory.WEAPON, slots);
	}

	@Override
	public int getMinCost(int enchantmentLevel) {
		return 5 + 20 * (enchantmentLevel - 1);
	}

	@Override
	public int getMaxCost(int enchantmentLevel) {
		return super.getMinCost(enchantmentLevel) + 50;
	}

	@Override
	public int getMaxLevel() {
		return 2;
	}

	@Override
	public boolean checkCompatibility(Enchantment ench) {
		return !(ench instanceof KnockbackEnchantment) && super.checkCompatibility(ench);
	}

	@Override
	public boolean isTradeable() {
		return AllurementConfig.COMMON.enableReeling.get();
	}

	@Override
	public boolean isDiscoverable() {
		return AllurementConfig.COMMON.enableReeling.get();
	}

	@Override
	public void doPostAttack(LivingEntity attacker, Entity entity, int level) {
		if (level > 0 && entity instanceof LivingEntity target) {
			target.setOnGround(false);
			target.push(0, AllurementConfig.COMMON.launchVerticalFactor.get() * level * (1.0D - target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE)), 0);
		}
	}
}

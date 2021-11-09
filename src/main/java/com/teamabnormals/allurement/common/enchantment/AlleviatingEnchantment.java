package com.teamabnormals.allurement.common.enchantment;

import com.teamabnormals.allurement.core.AllurementConfig;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.MendingEnchantment;
import net.minecraft.world.entity.EquipmentSlot;

public class AlleviatingEnchantment extends Enchantment {
	public AlleviatingEnchantment(Enchantment.Rarity rarityIn, EquipmentSlot... slots) {
		super(rarityIn, EnchantmentCategory.ARMOR, slots);
	}

	@Override
	public int getMinCost(int enchantmentLevel) {
		return enchantmentLevel * 25;
	}

	@Override
	public int getMaxCost(int enchantmentLevel) {
		return this.getMinCost(enchantmentLevel) + 50;
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@Override
	public boolean checkCompatibility(Enchantment ench) {
		return !(ench instanceof MendingEnchantment) && !(ench instanceof ReformingEnchantment) && super.checkCompatibility(ench);
	}

	@Override
	public boolean isTreasureOnly() {
		return true;
	}

	@Override
	public boolean isTradeable() {
		return AllurementConfig.COMMON.enableAlleviating.get();
	}

	@Override
	public boolean isDiscoverable() {
		return AllurementConfig.COMMON.enableAlleviating.get();
	}
}
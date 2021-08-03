package com.minecraftabnormals.allurement.common.enchantment;

import com.minecraftabnormals.allurement.core.AllurementConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.InfinityEnchantment;
import net.minecraft.enchantment.MendingEnchantment;
import net.minecraft.inventory.EquipmentSlotType;

public class ReformingEnchantment extends Enchantment {

	public ReformingEnchantment(Enchantment.Rarity rarityIn, EquipmentSlotType... slots) {
		super(rarityIn, EnchantmentType.BREAKABLE, slots);
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
		return !(ench instanceof InfinityEnchantment) && !(ench instanceof MendingEnchantment) && super.checkCompatibility(ench);
	}

	@Override
	public boolean isTreasureOnly() {
		return true;
	}

	@Override
	public boolean isTradeable() {
		return AllurementConfig.COMMON.enableReforming.get();
	}

	@Override
	public boolean isDiscoverable() {
		return AllurementConfig.COMMON.enableReforming.get();
	}
}
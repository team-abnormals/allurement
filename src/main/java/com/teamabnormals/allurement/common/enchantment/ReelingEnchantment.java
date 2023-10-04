package com.teamabnormals.allurement.common.enchantment;

import com.teamabnormals.allurement.core.AllurementConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class ReelingEnchantment extends Enchantment {

	public ReelingEnchantment(Enchantment.Rarity rarityIn, EquipmentSlot... slots) {
		super(rarityIn, EnchantmentCategory.CROSSBOW, slots);
	}

	@Override
	public int getMinCost(int enchantmentLevel) {
		return 12 + (enchantmentLevel - 1) * 20;
	}

	@Override
	public int getMaxCost(int enchantmentLevel) {
		return this.getMinCost(enchantmentLevel) + 25;
	}

	@Override
	public int getMaxLevel() {
		return 2;
	}

	@Override
	public boolean isTradeable() {
		return AllurementConfig.COMMON.enableReeling.get();
	}

	@Override
	public boolean isDiscoverable() {
		return AllurementConfig.COMMON.enableReeling.get();
	}
}
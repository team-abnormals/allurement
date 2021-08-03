package com.minecraftabnormals.allurement.common.enchantment;

import com.minecraftabnormals.allurement.core.AllurementConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class ReelingEnchantment extends Enchantment {
	public ReelingEnchantment(Enchantment.Rarity rarityIn, EquipmentSlotType... slots) {
		super(rarityIn, EnchantmentType.CROSSBOW, slots);
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
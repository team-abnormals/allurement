package com.minecraftabnormals.allurement.common.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class ReelingEnchantment extends Enchantment {
	public ReelingEnchantment(Enchantment.Rarity rarityIn, EquipmentSlotType... slots) {
		super(rarityIn, EnchantmentType.CROSSBOW, slots);
	}

	public int getMinEnchantability(int enchantmentLevel) {
		return 12 + (enchantmentLevel - 1) * 20;
	}

	public int getMaxEnchantability(int enchantmentLevel) {
		return this.getMinEnchantability(enchantmentLevel) + 25;
	}

	public int getMaxLevel() {
		return 2;
	}
}
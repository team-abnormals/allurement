package com.minecraftabnormals.allurement.common.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.MendingEnchantment;
import net.minecraft.inventory.EquipmentSlotType;

public class AlleviatingEnchantment extends Enchantment {
	public AlleviatingEnchantment(Enchantment.Rarity rarityIn, EquipmentSlotType... slots) {
		super(rarityIn, EnchantmentType.ARMOR, slots);
	}

	public int getMinEnchantability(int enchantmentLevel) {
		return enchantmentLevel * 25;
	}

	public int getMaxEnchantability(int enchantmentLevel) {
		return this.getMinEnchantability(enchantmentLevel) + 50;
	}

	public boolean isTreasureEnchantment() {
		return true;
	}

	public int getMaxLevel() {
		return 1;
	}

	@Override
	public boolean canApplyTogether(Enchantment ench) {
		return !(ench instanceof MendingEnchantment)  && !(ench instanceof ReformingEnchantment) && super.canApplyTogether(ench);
	}
}
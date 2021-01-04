package com.minecraftabnormals.allurement.common.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.MendingEnchantment;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.inventory.EquipmentSlotType;

public class ShockwaveEnchantment extends Enchantment {
	public ShockwaveEnchantment(Rarity rarityIn, EquipmentSlotType... slots) {
		super(rarityIn, EnchantmentType.ARMOR_FEET, slots);
	}

	public int getMinEnchantability(int enchantmentLevel) {
		return 5 + (enchantmentLevel - 1) * 6;
	}

	public int getMaxEnchantability(int enchantmentLevel) {
		return this.getMinEnchantability(enchantmentLevel) + 6;
	}

	public int getMaxLevel() {
		return 4;
	}

	@Override
	public boolean canApplyTogether(Enchantment ench) {
		return !(ench instanceof ProtectionEnchantment && ((ProtectionEnchantment) ench).protectionType == ProtectionEnchantment.Type.FALL) && super.canApplyTogether(ench);
	}
}
package com.minecraftabnormals.allurement.common.enchantment;

import com.minecraftabnormals.allurement.core.AllurementConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.KnockbackEnchantment;
import net.minecraft.inventory.EquipmentSlotType;

public class LaunchEnchantment extends Enchantment {
	public LaunchEnchantment(Enchantment.Rarity rarityIn, EquipmentSlotType... slots) {
		super(rarityIn, EnchantmentType.WEAPON, slots);
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
}

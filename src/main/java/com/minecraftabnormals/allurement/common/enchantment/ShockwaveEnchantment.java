package com.minecraftabnormals.allurement.common.enchantment;

import com.minecraftabnormals.allurement.core.AllurementConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.inventory.EquipmentSlotType;

public class ShockwaveEnchantment extends Enchantment {

	public ShockwaveEnchantment(Rarity rarityIn, EquipmentSlotType... slots) {
		super(rarityIn, EnchantmentType.ARMOR_FEET, slots);
	}

	@Override
	public int getMinCost(int enchantmentLevel) {
		return 5 + (enchantmentLevel - 1) * 6;
	}

	@Override
	public int getMaxCost(int enchantmentLevel) {
		return this.getMinCost(enchantmentLevel) + 6;
	}

	@Override
	public int getMaxLevel() {
		return 4;
	}

	@Override
	public boolean checkCompatibility(Enchantment ench) {
		return !(ench instanceof ProtectionEnchantment && ((ProtectionEnchantment) ench).type == ProtectionEnchantment.Type.FALL) && super.checkCompatibility(ench);
	}

	@Override
	public boolean isTradeable() {
		return AllurementConfig.COMMON.enableShockwave.get();
	}

	@Override
	public boolean isDiscoverable() {
		return AllurementConfig.COMMON.enableShockwave.get();
	}
}
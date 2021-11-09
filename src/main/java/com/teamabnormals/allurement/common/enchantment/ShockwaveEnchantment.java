package com.teamabnormals.allurement.common.enchantment;

import com.teamabnormals.allurement.core.AllurementConfig;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.minecraft.world.entity.EquipmentSlot;

public class ShockwaveEnchantment extends Enchantment {

	public ShockwaveEnchantment(Rarity rarityIn, EquipmentSlot... slots) {
		super(rarityIn, EnchantmentCategory.ARMOR_FEET, slots);
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
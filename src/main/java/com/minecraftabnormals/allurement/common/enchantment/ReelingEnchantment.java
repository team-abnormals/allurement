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
	public int getMinEnchantability(int enchantmentLevel) {
		return 12 + (enchantmentLevel - 1) * 20;
	}

	@Override
	public int getMaxEnchantability(int enchantmentLevel) {
		return this.getMinEnchantability(enchantmentLevel) + 25;
	}

	@Override
	public int getMaxLevel() {
		return 2;
	}

	@Override
	public boolean canVillagerTrade() {
		return AllurementConfig.COMMON.enableReeling.get();
	}

	@Override
	public boolean canGenerateInLoot() {
		return AllurementConfig.COMMON.enableReeling.get();
	}
}
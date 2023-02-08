package com.teamabnormals.allurement.common.enchantment;

import com.teamabnormals.allurement.core.AllurementConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class AscensionCurseEnchantment extends Enchantment {

	public AscensionCurseEnchantment(Rarity rarityIn, EquipmentSlot... slots) {
		super(rarityIn, EnchantmentCategory.VANISHABLE, slots);
	}

	@Override
	public int getMinCost(int enchantmentLevel) {
		return 25;
	}

	@Override
	public int getMaxCost(int enchantmentLevel) {
		return 50;
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@Override
	public boolean isTreasureOnly() {
		return true;
	}

	@Override
	public boolean isCurse() {
		return true;
	}

	@Override
	public boolean isTradeable() {
		return AllurementConfig.COMMON.enableAscensionCurse.get();
	}

	@Override
	public boolean isDiscoverable() {
		return AllurementConfig.COMMON.enableAscensionCurse.get();
	}

	@Override
	public boolean allowedInCreativeTab(Item book, CreativeModeTab tab) {
		if (!AllurementConfig.COMMON.enableAscensionCurse.get()) {
			return false;
		}
		return super.allowedInCreativeTab(book, tab);
	}
}
package com.teamabnormals.allurement.common.enchantment;

import com.teamabnormals.allurement.core.AllurementConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class SpreadOfAilmentsEnchantment extends Enchantment {

	public SpreadOfAilmentsEnchantment(Enchantment.Rarity rarity, EquipmentSlot... slot) {
		super(rarity, EnchantmentCategory.CROSSBOW, slot);
	}

	@Override
	public int getMaxCost(int level) {
		return 50;
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	@Override
	public boolean isTradeable() {
		return AllurementConfig.COMMON.enableSpreadOfAilments.get();
	}

	@Override
	public boolean isDiscoverable() {
		return AllurementConfig.COMMON.enableSpreadOfAilments.get();
	}

	@Override
	public boolean allowedInCreativeTab(Item book, CreativeModeTab tab) {
		if (!AllurementConfig.COMMON.enableSpreadOfAilments.get()) {
			return false;
		}
		return super.allowedInCreativeTab(book, tab);
	}
}
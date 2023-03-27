package com.teamabnormals.allurement.common.enchantment;

import com.teamabnormals.allurement.core.AllurementConfig;
import com.teamabnormals.allurement.core.registry.AllurementEnchantments;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;

public class ObedienceEnchantment extends Enchantment {

	public ObedienceEnchantment(Rarity rarityIn, EquipmentSlot... slots) {
		super(rarityIn, AllurementEnchantments.HORSE_ARMOR, slots);
	}

	@Override
	public int getMinCost(int enchantmentLevel) {
		return 15;
	}

	@Override
	public int getMaxCost(int enchantmentLevel) {
		return this.getMinCost(enchantmentLevel) + 50;
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
	public boolean isTradeable() {
		return false;
	}

	@Override
	public boolean isDiscoverable() {
		return false;
	}

	@Override
	public boolean allowedInCreativeTab(Item book, CreativeModeTab tab) {
		if (!AllurementConfig.COMMON.enchantableHorseArmor.get() || !AllurementConfig.COMMON.enableObedience.get()) {
			return false;
		}
		return super.allowedInCreativeTab(book, tab);
	}
}
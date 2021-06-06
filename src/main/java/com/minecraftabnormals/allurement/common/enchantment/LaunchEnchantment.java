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
	public int getMinEnchantability(int enchantmentLevel) {
		return 5 + 20 * (enchantmentLevel - 1);
	}

	@Override
	public int getMaxEnchantability(int enchantmentLevel) {
		return super.getMinEnchantability(enchantmentLevel) + 50;
	}

	@Override
	public int getMaxLevel() {
		return 2;
	}

	@Override
	public boolean canApplyTogether(Enchantment ench) {
		return !(ench instanceof KnockbackEnchantment) && super.canApplyTogether(ench);
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

package com.minecraftabnormals.allurement.common.enchantment;

import com.minecraftabnormals.allurement.core.AllurementConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.ThornsEnchantment;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;

public class VengeanceEnchantment extends Enchantment {

	public VengeanceEnchantment(Enchantment.Rarity rarityIn, EquipmentSlotType... slots) {
		super(rarityIn, EnchantmentType.ARMOR_CHEST, slots);
	}

	@Override
	public int getMinEnchantability(int enchantmentLevel) {
		return 10 + 20 * (enchantmentLevel - 1);
	}

	@Override
	public int getMaxEnchantability(int enchantmentLevel) {
		return super.getMinEnchantability(enchantmentLevel) + 50;
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	@Override
	public boolean canApplyTogether(Enchantment ench) {
		return !(ench instanceof ThornsEnchantment) && super.canApplyTogether(ench);
	}

	@Override
	public boolean canApply(ItemStack stack) {
		return stack.getItem() instanceof ArmorItem || super.canApply(stack);
	}

	@Override
	public boolean isTreasureEnchantment() {
		return !AllurementConfig.COMMON.enableVengeance.get();
	}

	@Override
	public boolean canVillagerTrade() {
		return AllurementConfig.COMMON.enableVengeance.get();
	}

	@Override
	public boolean canGenerateInLoot() {
		return AllurementConfig.COMMON.enableVengeance.get();
	}
}
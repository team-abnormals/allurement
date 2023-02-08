package com.teamabnormals.allurement.common.enchantment;

import com.teamabnormals.allurement.core.AllurementConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.ThornsEnchantment;

public class VengeanceEnchantment extends Enchantment {

	public VengeanceEnchantment(Enchantment.Rarity rarityIn, EquipmentSlot... slots) {
		super(rarityIn, EnchantmentCategory.ARMOR_CHEST, slots);
	}

	@Override
	public int getMinCost(int enchantmentLevel) {
		return 10 + 20 * (enchantmentLevel - 1);
	}

	@Override
	public int getMaxCost(int enchantmentLevel) {
		return super.getMinCost(enchantmentLevel) + 50;
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	@Override
	public boolean checkCompatibility(Enchantment ench) {
		return !(ench instanceof ThornsEnchantment) && super.checkCompatibility(ench);
	}

	@Override
	public boolean canEnchant(ItemStack stack) {
		return stack.getItem() instanceof ArmorItem || super.canEnchant(stack);
	}

	@Override
	public boolean isTradeable() {
		return AllurementConfig.COMMON.enableVengeance.get();
	}

	@Override
	public boolean isDiscoverable() {
		return AllurementConfig.COMMON.enableVengeance.get();
	}

	@Override
	public boolean allowedInCreativeTab(Item book, CreativeModeTab tab) {
		if (!AllurementConfig.COMMON.enableVengeance.get()) {
			return false;
		}
		return super.allowedInCreativeTab(book, tab);
	}
}
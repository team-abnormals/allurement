package com.minecraftabnormals.allurement.core.other;

import com.google.common.collect.Maps;
import com.minecraftabnormals.allurement.core.Allurement;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import vazkii.quark.content.tools.module.ColorRunesModule;

import java.util.Map;

@Mod.EventBusSubscriber(modid = Allurement.MOD_ID)
public class AllurementUtil {
	public static final Map<String, Integer> ENCHANTABILITY_MAP = Util.make(Maps.newHashMap(), (map) -> {
		map.put("minecraft:leather_horse_armor", 15);
		map.put("minecraft:iron_horse_armor", 9);
		map.put("minecraft:golden_horse_armor", 25);
		map.put("minecraft:diamond_horse_armor", 10);
		map.put("nether_extension:netherite_horse_armor", 15);
		map.put("caverns_and_chasms:silver_horse_armor", 17);
		map.put("caverns_and_chasms:necromium_horse_armor", 13);
	});

	public static int getTotalEnchantmentLevel(Enchantment ench, LivingEntity entity, EquipmentSlotType.Group group) {
		int count = 0;
		for (EquipmentSlotType slot : EquipmentSlotType.values()) {
			if (slot.getSlotType() == group) {
				count += EnchantmentHelper.getEnchantmentLevel(ench, entity.getItemStackFromSlot(slot));
			}
		}
		return count;
	}

	public static int getEnchantmentCount(Enchantment ench, LivingEntity entity) {
		return getTotalEnchantmentLevel(ench, entity, EquipmentSlotType.Group.ARMOR) + getTotalEnchantmentLevel(ench, entity, EquipmentSlotType.Group.HAND);
	}

	public static void setColorRuneTarget(ItemStack stack) {
		if (ModList.get().isLoaded("quark"))
			ColorRunesModule.setTargetStack(stack);
	}
}

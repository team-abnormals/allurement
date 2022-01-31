package com.teamabnormals.allurement.core.other;

import com.google.common.collect.Maps;
import com.teamabnormals.allurement.core.Allurement;
import net.minecraft.Util;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
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

	public static int getTotalEnchantmentLevel(Enchantment ench, LivingEntity entity, EquipmentSlot.Type group) {
		int count = 0;
		for (EquipmentSlot slot : EquipmentSlot.values()) {
			if (slot.getType() == group) {
				count += EnchantmentHelper.getItemEnchantmentLevel(ench, entity.getItemBySlot(slot));
			}
		}
		return count;
	}

	public static void setColorRuneTarget(ItemStack stack) {
		if (ModList.get().isLoaded("quark")) ColorRunesModule.setTargetStack(stack);
	}
}

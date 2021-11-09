package com.teamabnormals.allurement.core.other;

import com.teamabnormals.allurement.core.Allurement;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.tags.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeTagHandler;
import net.minecraftforge.registries.ForgeRegistries;

public class AllurementTags {

	public static class Enchantments {
		public static final Tag.Named<Enchantment> UNUSABLE_ON_HORSE_ARMOR = createTag("unusable_on_horse_armor");

		private static Tag.Named<Enchantment> createTag(String name) {
			return ForgeTagHandler.makeWrapperTag(ForgeRegistries.ENCHANTMENTS, new ResourceLocation(Allurement.MOD_ID, name));
		}
	}
}
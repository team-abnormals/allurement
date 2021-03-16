package com.minecraftabnormals.allurement.core.other;

import com.minecraftabnormals.allurement.core.Allurement;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeTagHandler;
import net.minecraftforge.registries.ForgeRegistries;

public class AllurementTags {

	public static class Enchantments {
		public static final ITag.INamedTag<Enchantment> UNUSABLE_ON_HORSE_ARMOR = createTag("unusable_on_horse_armor");

		private static ITag.INamedTag<Enchantment> createTag(String name) {
			return ForgeTagHandler.makeWrapperTag(ForgeRegistries.ENCHANTMENTS, new ResourceLocation(Allurement.MOD_ID, name));
		}
	}
}
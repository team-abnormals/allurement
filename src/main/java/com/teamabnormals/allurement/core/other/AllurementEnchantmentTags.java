package com.teamabnormals.allurement.core.other;

import com.teamabnormals.allurement.core.Allurement;
import com.teamabnormals.blueprint.core.util.TagUtil;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.enchantment.Enchantment;

public class AllurementEnchantmentTags {
	public static final Tag.Named<Enchantment> UNUSABLE_ON_HORSE_ARMOR = enchantmentTag("unusable_on_horse_armor");

	public static Tag.Named<Enchantment> enchantmentTag(String name) {
		return TagUtil.enchantmentTag(Allurement.MOD_ID, name);
	}
}
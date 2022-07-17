package com.teamabnormals.allurement.core.other.tags;

import com.teamabnormals.allurement.core.Allurement;
import com.teamabnormals.blueprint.core.util.TagUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;

public class AllurementEnchantmentTags {
	public static final TagKey<Enchantment> UNUSABLE_ON_HORSE_ARMOR = enchantmentTag("unusable_on_horse_armor");

	public static TagKey<Enchantment> enchantmentTag(String name) {
		return TagUtil.enchantmentTag(Allurement.MOD_ID, name);
	}
}
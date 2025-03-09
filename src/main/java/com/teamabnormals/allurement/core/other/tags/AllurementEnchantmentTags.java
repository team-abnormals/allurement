package com.teamabnormals.allurement.core.other.tags;

import com.teamabnormals.allurement.core.Allurement;
import com.teamabnormals.blueprint.core.util.TagUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;

public class AllurementEnchantmentTags {
	public static final TagKey<Enchantment> ON_END_CITY_LOOT = enchantmentTag("on_end_city_loot");
	public static final TagKey<Enchantment> UNUSABLE_ON_ANIMAL_ARMOR = enchantmentTag("unusable_on_animal_armor");

	public static final TagKey<Enchantment> FALLING_EXCLUSIVE = enchantmentTag("exclusive_set/falling");
	public static final TagKey<Enchantment> KNOCKBACK_EXCLUSIVE = enchantmentTag("exclusive_set/knockback");
	public static final TagKey<Enchantment> MENDING_EXCLUSIVE = enchantmentTag("exclusive_set/mending");
	public static final TagKey<Enchantment> THORNS_EXCLUSIVE = enchantmentTag("exclusive_set/thorns");

	public static TagKey<Enchantment> enchantmentTag(String name) {
		return TagUtil.enchantmentTag(Allurement.MOD_ID, name);
	}
}
package com.teamabnormals.allurement.core.other.tags;

import com.teamabnormals.allurement.core.Allurement;
import com.teamabnormals.blueprint.core.util.TagUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class AllurementItemTags {
	public static final TagKey<Item> ANIMAL_ARMOR_ENCHANTABLE = itemTag("enchantable/animal_armor");

	public static TagKey<Item> itemTag(String name) {
		return TagUtil.itemTag(Allurement.MOD_ID, name);
	}
}
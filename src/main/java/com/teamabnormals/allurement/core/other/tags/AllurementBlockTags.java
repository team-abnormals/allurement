package com.teamabnormals.allurement.core.other.tags;

import com.teamabnormals.allurement.core.Allurement;
import com.teamabnormals.blueprint.core.util.TagUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class AllurementBlockTags {
	public static final TagKey<Block> MINEABLE_WITH_BANE_OF_ARTHROPODS = blockTag("mineable/bane_of_arthropods");
	public static final TagKey<Block> TRAMPLED_BY_SHOCKWAVE = blockTag("trampled_by_shockwave");

	public static TagKey<Block> blockTag(String name) {
		return TagUtil.blockTag(Allurement.MOD_ID, name);
	}
}
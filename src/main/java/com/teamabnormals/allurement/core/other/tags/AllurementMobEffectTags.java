package com.teamabnormals.allurement.core.other.tags;

import com.teamabnormals.allurement.core.Allurement;
import com.teamabnormals.blueprint.core.util.TagUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;

public class AllurementMobEffectTags {
	public static final TagKey<MobEffect> SPREAD_OF_AILMENTS_CANNOT_INFLICT = mobEffectTag("spread_of_ailments_cannot_inflict");

	public static TagKey<MobEffect> mobEffectTag(String name) {
		return TagUtil.mobEffectTag(Allurement.MOD_ID, name);
	}
}
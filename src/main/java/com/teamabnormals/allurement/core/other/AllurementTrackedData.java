package com.teamabnormals.allurement.core.other;

import com.mojang.serialization.Codec;
import com.teamabnormals.allurement.core.Allurement;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedData;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;

public class AllurementTrackedData {
	public static final TrackedData<Boolean> INFINITY_ARROW = TrackedData.Builder.create(ByteBufCodecs.BOOL, () -> false).enableSaving(Codec.BOOL.fieldOf("value")).build();
	public static final TrackedData<Float> ABSORBED_DAMAGE = TrackedData.Builder.create(ByteBufCodecs.FLOAT, () -> 0.0F).enableSaving(Codec.FLOAT.fieldOf("value")).build();

	public static void registerTrackedData() {
		TrackedDataManager.INSTANCE.registerData(ResourceLocation.fromNamespaceAndPath(Allurement.MOD_ID, "shot_infinity_arrow"), INFINITY_ARROW);
		TrackedDataManager.INSTANCE.registerData(ResourceLocation.fromNamespaceAndPath(Allurement.MOD_ID, "absorbed_damage"), ABSORBED_DAMAGE);
	}
}
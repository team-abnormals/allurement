package com.teamabnormals.allurement.core.other;

import com.teamabnormals.allurement.core.Allurement;
import com.teamabnormals.blueprint.common.world.storage.tracking.DataProcessors;
import com.teamabnormals.blueprint.common.world.storage.tracking.IDataProcessor;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedData;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;

public class AllurementTrackedData {

	public static final IDataProcessor<ListTag> LIST_TAG = new IDataProcessor<>() {
		@Override
		public CompoundTag write(ListTag listTag) {
			CompoundTag compound = new CompoundTag();
			compound.put("ListTag", listTag);
			return compound;
		}

		@Override
		public ListTag read(CompoundTag nbt) {
			return nbt.getList("ListTag", Tag.TAG_LIST);
		}
	};

	public static final TrackedData<Boolean> INFINITY_ARROW = TrackedData.Builder.create(DataProcessors.BOOLEAN, () -> false).enableSaving().build();
	public static final TrackedData<Float> ABSORBED_DAMAGE = TrackedData.Builder.create(DataProcessors.FLOAT, () -> 0.0F).enableSaving().build();
	public static final TrackedData<ListTag> ARROW_EFFECTS = TrackedData.Builder.create(LIST_TAG, ListTag::new).enableSaving().build();

	public static void registerTrackedData() {
		TrackedDataManager.INSTANCE.registerData(new ResourceLocation(Allurement.MOD_ID, "shot_infinity_arrow"), INFINITY_ARROW);
		TrackedDataManager.INSTANCE.registerData(new ResourceLocation(Allurement.MOD_ID, "absorbed_damage"), ABSORBED_DAMAGE);
		TrackedDataManager.INSTANCE.registerData(new ResourceLocation(Allurement.MOD_ID, "arrow_effects"), ARROW_EFFECTS);
	}
}
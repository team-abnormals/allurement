package com.teamabnormals.allurement.core.data.server.tags;

import com.teamabnormals.allurement.core.Allurement;
import com.teamabnormals.allurement.core.other.tags.AllurementBlockTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;

public class AllurementBlockTagsProvider extends BlockTagsProvider {

	public AllurementBlockTagsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Allurement.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags() {
		this.tag(AllurementBlockTags.MINEABLE_WITH_BANE_OF_ARTHROPODS).add(Blocks.COBWEB, Blocks.BEE_NEST).addOptional(new ResourceLocation("endergetic", "eetle_egg"));
	}
}
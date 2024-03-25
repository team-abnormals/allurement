package com.teamabnormals.allurement.core.data.server.tags;

import com.teamabnormals.allurement.core.Allurement;
import com.teamabnormals.allurement.core.other.tags.AllurementBlockTags;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class AllurementBlockTagsProvider extends BlockTagsProvider {

	public AllurementBlockTagsProvider(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
		super(output, provider, Allurement.MOD_ID, helper);
	}

	@Override
	protected void addTags(Provider provider) {
		this.tag(AllurementBlockTags.TRAMPLED_BY_SHOCKWAVE).add(Blocks.FARMLAND);
		this.tag(AllurementBlockTags.MINEABLE_WITH_BANE_OF_ARTHROPODS)
				.add(Blocks.COBWEB, Blocks.BEE_NEST)
				.addOptional(new ResourceLocation("atmospheric", "grimweb"))
				.addOptional(new ResourceLocation("endergetic", "eetle_egg"));
	}
}
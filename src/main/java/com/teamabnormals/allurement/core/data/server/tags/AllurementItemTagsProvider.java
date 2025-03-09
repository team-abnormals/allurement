package com.teamabnormals.allurement.core.data.server.tags;

import com.teamabnormals.allurement.core.Allurement;
import com.teamabnormals.blueprint.core.other.tags.BlueprintItemTags;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

import static com.teamabnormals.allurement.core.other.tags.AllurementItemTags.ANIMAL_ARMOR_ENCHANTABLE;

public class AllurementItemTagsProvider extends ItemTagsProvider {

	public AllurementItemTagsProvider(PackOutput output, CompletableFuture<Provider> provider, CompletableFuture<TagLookup<Block>> lookup, ExistingFileHelper helper) {
		super(output, provider, lookup, Allurement.MOD_ID, helper);
	}

	@Override
	protected void addTags(Provider provider) {
		this.tag(ANIMAL_ARMOR_ENCHANTABLE).addTag(BlueprintItemTags.HORSE_ARMOR).add(Items.WOLF_ARMOR);
		this.tag(ItemTags.VANISHING_ENCHANTABLE).addTag(ANIMAL_ARMOR_ENCHANTABLE);
		this.tag(ItemTags.HEAD_ARMOR_ENCHANTABLE).addTag(ANIMAL_ARMOR_ENCHANTABLE);
		this.tag(ItemTags.CHEST_ARMOR_ENCHANTABLE).addTag(ANIMAL_ARMOR_ENCHANTABLE);
		this.tag(ItemTags.LEG_ARMOR_ENCHANTABLE).addTag(ANIMAL_ARMOR_ENCHANTABLE);
		this.tag(ItemTags.FOOT_ARMOR_ENCHANTABLE).addTag(ANIMAL_ARMOR_ENCHANTABLE);
		this.tag(ItemTags.EQUIPPABLE_ENCHANTABLE).addTag(ANIMAL_ARMOR_ENCHANTABLE);
	}
}
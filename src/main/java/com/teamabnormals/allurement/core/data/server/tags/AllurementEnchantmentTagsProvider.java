package com.teamabnormals.allurement.core.data.server.tags;

import com.teamabnormals.allurement.core.Allurement;
import com.teamabnormals.allurement.core.other.tags.AllurementEnchantmentTags;
import com.teamabnormals.allurement.core.registry.AllurementEnchantments;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.common.data.ExistingFileHelper;

public class AllurementEnchantmentTagsProvider extends TagsProvider<Enchantment> {

	public AllurementEnchantmentTagsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Registry.ENCHANTMENT, Allurement.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags() {
		this.tag(AllurementEnchantmentTags.UNUSABLE_ON_HORSE_ARMOR).add(Enchantments.AQUA_AFFINITY, AllurementEnchantments.ALLEVIATING.get(), AllurementEnchantments.VENGEANCE.get());
	}

	@Override
	public String getName() {
		return "Enchantment Tags";
	}
}
package com.teamabnormals.allurement.core.data;

import com.teamabnormals.allurement.core.Allurement;
import com.teamabnormals.allurement.core.other.AllurementEnchantmentTags;
import com.teamabnormals.allurement.core.registry.AllurementEnchantments;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeRegistryTagsProvider;
import net.minecraftforge.registries.ForgeRegistries;

public class EnchantmentTagGenerator extends ForgeRegistryTagsProvider<Enchantment> {

	public EnchantmentTagGenerator(DataGenerator generator, ExistingFileHelper fileHelper) {
		super(generator, ForgeRegistries.ENCHANTMENTS, Allurement.MOD_ID, fileHelper);
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
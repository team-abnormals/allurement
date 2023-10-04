package com.teamabnormals.allurement.core.data.server.tags;

import com.teamabnormals.allurement.core.Allurement;
import com.teamabnormals.allurement.core.other.tags.AllurementEnchantmentTags;
import com.teamabnormals.allurement.core.registry.AllurementEnchantments;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.concurrent.CompletableFuture;

public class AllurementEnchantmentTagsProvider extends IntrinsicHolderTagsProvider<Enchantment> {

	public AllurementEnchantmentTagsProvider(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
		super(output, Registries.ENCHANTMENT, provider, enchantment -> ForgeRegistries.ENCHANTMENTS.getResourceKey(enchantment).get(), Allurement.MOD_ID, helper);
	}

	@Override
	protected void addTags(HolderLookup.Provider lookupProvider) {
		this.tag(AllurementEnchantmentTags.UNUSABLE_ON_HORSE_ARMOR).add(Enchantments.AQUA_AFFINITY, Enchantments.SWIFT_SNEAK, AllurementEnchantments.ALLEVIATING.get(), AllurementEnchantments.VENGEANCE.get());
	}

	@Override
	public String getName() {
		return "Enchantment Tags";
	}
}
package com.teamabnormals.allurement.core.data.server.tags;

import com.teamabnormals.allurement.core.Allurement;
import com.teamabnormals.allurement.core.other.tags.AllurementEnchantmentTags;
import com.teamabnormals.allurement.core.registry.datapack.AllurementEnchantments;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

import static com.teamabnormals.allurement.core.registry.datapack.AllurementEnchantments.*;

public class AllurementEnchantmentTagsProvider extends EnchantmentTagsProvider {

	public AllurementEnchantmentTagsProvider(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
		super(output, provider, Allurement.MOD_ID, helper);
	}

	@Override
	protected void addTags(Provider provider) {
		this.tag(EnchantmentTags.CURSE).add(ASCENSION_CURSE, FLEETING_CURSE);
		this.tag(EnchantmentTags.TREASURE).add(ASCENSION_CURSE, FLEETING_CURSE, ALLEVIATING, REFORMING, OBEDIENCE);
		this.tag(EnchantmentTags.TRADEABLE).add(ASCENSION_CURSE, FLEETING_CURSE, ALLEVIATING, REFORMING);
		this.tag(EnchantmentTags.NON_TREASURE).add(LAUNCH, REELING, SHOCKWAVE, SPREAD_OF_AILMENTS, VENGEANCE);

		this.tag(EnchantmentTags.ON_RANDOM_LOOT).add(FLEETING_CURSE, ALLEVIATING, REFORMING);
		this.tag(AllurementEnchantmentTags.ON_END_CITY_LOOT).addTag(EnchantmentTags.ON_RANDOM_LOOT).add(ASCENSION_CURSE);

		this.tag(AllurementEnchantmentTags.UNUSABLE_ON_ANIMAL_ARMOR).add(Enchantments.AQUA_AFFINITY, Enchantments.SWIFT_SNEAK, AllurementEnchantments.ALLEVIATING, AllurementEnchantments.VENGEANCE);

		this.tag(EnchantmentTags.BOW_EXCLUSIVE).add(REFORMING);
		this.tag(AllurementEnchantmentTags.FALLING_EXCLUSIVE).add(Enchantments.FEATHER_FALLING, SHOCKWAVE);
		this.tag(AllurementEnchantmentTags.THORNS_EXCLUSIVE).add(Enchantments.THORNS, VENGEANCE);
		this.tag(AllurementEnchantmentTags.KNOCKBACK_EXCLUSIVE).add(Enchantments.KNOCKBACK, LAUNCH);
		this.tag(AllurementEnchantmentTags.MENDING_EXCLUSIVE).add(Enchantments.MENDING, ALLEVIATING, REFORMING);
	}
}
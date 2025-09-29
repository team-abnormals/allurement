package com.teamabnormals.allurement.core.data.client;

import com.teamabnormals.allurement.core.Allurement;
import com.teamabnormals.allurement.core.registry.datapack.AllurementEnchantments;
import com.teamabnormals.blueprint.core.data.client.BlueprintLanguageProvider;
import net.minecraft.Util;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;

public class AllurementLanguageProvider extends BlueprintLanguageProvider {

	public AllurementLanguageProvider(PackOutput output) {
		super(output, Allurement.MOD_ID);
	}

	@Override
	protected void addTranslations() {
		this.addEnchantment(AllurementEnchantments.ALLEVIATING, "Heals the user upon collection of experience.");
		this.addEnchantment(AllurementEnchantments.LAUNCH, "Launches the target upwards instead of away.");
		this.addEnchantment(AllurementEnchantments.OBEDIENCE, "Prevents animals from roaming.");
		this.addEnchantment(AllurementEnchantments.REELING, "Pulls targets towards the user upon arrow impact.");
		this.addEnchantment(AllurementEnchantments.REFORMING, "Repairs the durability of items very slowly over time when in use.");
		this.addEnchantment(AllurementEnchantments.SHOCKWAVE, "Creates a shockwave when taking fall damage that damages nearby entities.");
		this.addEnchantment(AllurementEnchantments.VENGEANCE, "Stores some incoming damage and applies it to the user's next attack.");
		this.addEnchantment(AllurementEnchantments.SPREAD_OF_AILMENTS, "Applies the user's active effects to their arrows.");

		this.addCurse(AllurementEnchantments.ASCENSION_CURSE, "Causes the cursed item to float upwards when dropped.");
		this.addCurse(AllurementEnchantments.FLEETING_CURSE, "Causes nearby entities to repel the cursed item when dropped.");

		this.addDamageSource("shockwave", "%1$s was stomped by %2$s");
		this.addDamageSource("shockwave.item", "%1$s was stomped by %2$s using %3$s");
	}

	private void addEnchantment(ResourceKey<Enchantment> enchantment, String description) {
		String name = enchantment.location().getPath();
		String id = Util.makeDescriptionId("enchantment", enchantment.location());
		this.add(id, format(name));
		this.add(id + ".desc", description);
	}

	private void addCurse(ResourceKey<Enchantment> enchantment, String description) {
		String name = enchantment.location().getPath();
		String id = Util.makeDescriptionId("enchantment", enchantment.location());
		this.add(id, "Curse of " + format(name.replace("_curse", "")));
		this.add(id + ".desc", description);
	}

	private void addDamageSource(String suffix, String value) {
		this.add("death.attack." + Allurement.MOD_ID + "." + suffix, value);
	}
}
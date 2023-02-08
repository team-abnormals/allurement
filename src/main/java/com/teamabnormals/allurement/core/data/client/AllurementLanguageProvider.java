package com.teamabnormals.allurement.core.data.client;

import com.teamabnormals.allurement.core.Allurement;
import com.teamabnormals.allurement.core.registry.AllurementEnchantments;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.text.WordUtils;

public class AllurementLanguageProvider extends LanguageProvider {

	public AllurementLanguageProvider(DataGenerator generator) {
		super(generator, Allurement.MOD_ID, "en_us");
	}

	@Override
	protected void addTranslations() {
		this.addDesc(AllurementEnchantments.ALLEVIATING.get(), "Heals the player upon collection of experience");
		this.addDesc(AllurementEnchantments.LAUNCH.get(), "Launches the target upwards instead of away");
		this.addDesc(AllurementEnchantments.REELING.get(), "Pulls targets towards the user upon arrow impact");
		this.addDesc(AllurementEnchantments.REFORMING.get(), "Repairs the durability items very slowly over time when in use");
		this.addDesc(AllurementEnchantments.SHOCKWAVE.get(), "Creates a shockwave when taking fall damage that damages nearby entities");
		this.addDesc(AllurementEnchantments.VENGEANCE.get(), "Stores some incoming damage and applies it to your next attack");

		this.addCurse(AllurementEnchantments.ASCENSION_CURSE.get(), "Causes the cursed item to float upwards when dropped");

		this.addDamageSource("shockwave", "%1$s was stomped");
		this.addDamageSource("shockwave.player", "%1$s was stomped by %2$s");
		this.addDamageSource("shockwave.player.item", "%1$s was stomped by %2$s using %3$s");
	}

	private void addDesc(Enchantment enchantment, String description) {
		String name = ForgeRegistries.ENCHANTMENTS.getKey(enchantment).getPath();
		this.add(enchantment, format(name));
		this.add(enchantment.getDescriptionId() + ".desc", description);
	}

	private void addCurse(Enchantment enchantment, String description) {
		String name = ForgeRegistries.ENCHANTMENTS.getKey(enchantment).getPath();
		this.add(enchantment, "Curse of " + format(name.replace("_curse", "")));
		this.add(enchantment.getDescriptionId() + ".desc", description);
	}

	private void addDamageSource(String suffix, String value) {
		this.add("death.attack." + Allurement.MOD_ID + "." + suffix, value);
	}

	private String format(String path) {
		return WordUtils.capitalizeFully(path.replace("_", " ")).replace("Of ", "of ");
	}
}
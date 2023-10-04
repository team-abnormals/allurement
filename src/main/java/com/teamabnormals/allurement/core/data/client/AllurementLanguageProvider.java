package com.teamabnormals.allurement.core.data.client;

import com.teamabnormals.allurement.core.Allurement;
import com.teamabnormals.allurement.core.registry.AllurementEnchantments;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.text.WordUtils;

public class AllurementLanguageProvider extends LanguageProvider {

	public AllurementLanguageProvider(PackOutput output) {
		super(output, Allurement.MOD_ID, "en_us");
	}

	@Override
	protected void addTranslations() {
		this.addDesc(AllurementEnchantments.ALLEVIATING.get(), "Heals the user upon collection of experience");
		this.addDesc(AllurementEnchantments.LAUNCH.get(), "Launches the target upwards instead of away");
		this.addDesc(AllurementEnchantments.OBEDIENCE.get(), "Prevents horses from roaming");
		this.addDesc(AllurementEnchantments.REELING.get(), "Pulls targets towards the user upon arrow impact");
		this.addDesc(AllurementEnchantments.REFORMING.get(), "Repairs the durability of items very slowly over time when in use");
		this.addDesc(AllurementEnchantments.SHOCKWAVE.get(), "Creates a shockwave when taking fall damage that damages nearby entities");
		this.addDesc(AllurementEnchantments.VENGEANCE.get(), "Stores some incoming damage and applies it to the user's next attack");
		this.addDesc(AllurementEnchantments.SPREAD_OF_AILMENTS.get(), "Applies the user's active effects to their arrows");

		this.addCurse(AllurementEnchantments.ASCENSION_CURSE.get(), "Causes the cursed item to float upwards when dropped");
		this.addCurse(AllurementEnchantments.FLEETING_CURSE.get(), "Causes nearby entities to repel the cursed item when dropped");

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
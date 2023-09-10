package com.teamabnormals.allurement.core.data.server.modifiers;

import com.google.common.collect.Maps;
import com.teamabnormals.allurement.core.Allurement;
import com.teamabnormals.allurement.core.AllurementConfig;
import com.teamabnormals.allurement.core.registry.AllurementEnchantments;
import com.teamabnormals.blueprint.common.loot.modification.LootModifierProvider;
import com.teamabnormals.blueprint.common.loot.modification.modifiers.LootPoolEntriesModifier;
import com.teamabnormals.blueprint.core.api.conditions.ConfigValueCondition;
import com.teamabnormals.blueprint.core.util.modification.selection.ConditionedResourceSelector;
import com.teamabnormals.blueprint.core.util.modification.selection.selectors.NamesResourceSelector;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.crafting.conditions.AndCondition;

import java.util.List;


public class AllurementLootModifierProvider extends LootModifierProvider {

	public AllurementLootModifierProvider(DataGenerator dataGenerator) {
		super(dataGenerator, Allurement.MOD_ID);
	}

	@Override
	protected void registerEntries() {
		this.entry("bastion_hoglin_stable")
				.selector(new ConditionedResourceSelector(
						new NamesResourceSelector(BuiltInLootTables.BASTION_HOGLIN_STABLE),
						new AndCondition(
								config(AllurementConfig.COMMON.enchantableHorseArmor, "enchantable_horse_armor", false),
								config(AllurementConfig.COMMON.enableObedience, "enable_obedience", false)
						))
				)
				.addModifier(new LootPoolEntriesModifier(false, 1, List.of(
						LootItem.lootTableItem(Items.BOOK).setWeight(3).apply((new EnchantRandomlyFunction.Builder()).withEnchantment(AllurementEnchantments.OBEDIENCE.get())).build()
				)));
	}

	public static ConfigValueCondition config(ForgeConfigSpec.ConfigValue<?> value, String key, boolean inverted) {
		return new ConfigValueCondition(new ResourceLocation(Allurement.MOD_ID, "config"), value, key, Maps.newHashMap(), inverted);
	}
}
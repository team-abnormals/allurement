package com.teamabnormals.allurement.core.data.server;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.teamabnormals.allurement.core.Allurement;
import com.teamabnormals.allurement.core.AllurementConfig;
import com.teamabnormals.allurement.core.other.tags.AllurementEnchantmentTags;
import com.teamabnormals.allurement.core.registry.AllurementConditions;
import com.teamabnormals.allurement.core.registry.AllurementEnchantmentEffects;
import com.teamabnormals.allurement.core.registry.datapack.AllurementEnchantments;
import com.teamabnormals.blueprint.common.remolder.Remolder;
import com.teamabnormals.blueprint.common.remolder.SequenceRemolder;
import com.teamabnormals.blueprint.common.remolder.data.RemolderProvider;
import com.teamabnormals.blueprint.common.remolder.util.LootRemolders;
import com.teamabnormals.blueprint.core.api.conditions.ConfigValueCondition;
import com.teamabnormals.blueprint.core.util.modification.selection.ConditionedResourceSelector;
import com.teamabnormals.blueprint.core.util.modification.selection.selectors.NamesResourceSelector;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.PackOutput.Target;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Unit;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;
import net.minecraft.world.item.enchantment.effects.MultiplyValue;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.AllOfCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static com.teamabnormals.blueprint.common.remolder.RemolderTypes.*;
import static com.teamabnormals.blueprint.common.remolder.data.DynamicReference.target;
import static com.teamabnormals.blueprint.common.remolder.data.DynamicReference.value;

public class AllurementDataRemolderProvider extends RemolderProvider {

	public AllurementDataRemolderProvider(PackOutput output, CompletableFuture<Provider> provider) {
		super(Allurement.MOD_ID, Target.DATA_PACK, output, provider);
	}

	@Override
	protected void registerEntries(Provider provider) {
		this.entry("enchantable_animal_armor").path(
				"minecraft:enchantment/frost_walker",
				"minecraft:enchantment/depth_strider",
				"minecraft:enchantment/soul_speed",
				"minecraft:enchantment/respiration"
		).remolder(add(target("slots[]"), value("body", Codec.STRING)));

		RegistryLookup<Enchantment> enchantments = provider.lookupOrThrow(Registries.ENCHANTMENT);
		this.entry("bastion_hoglin_stable").path(
						new ConditionedResourceSelector(
								new NamesResourceSelector(BuiltInLootTables.BASTION_HOGLIN_STABLE.location().withPrefix("loot_table/")),
								config(AllurementConfig.COMMON.enchantableAnimalArmor, "enchantable_animal_armor", false)
						))
				.remolder(LootRemolders.addEntry(1,
						LootItem.lootTableItem(Items.BOOK).setWeight(3).apply((new EnchantRandomlyFunction.Builder()).withEnchantment(enchantments.getOrThrow(AllurementEnchantments.OBEDIENCE))).build()
				));

		this.entry("end_city_treasure").path(BuiltInLootTables.END_CITY_TREASURE.location().withPrefix("loot_table/").toString())
				.remolder(replaceOptions("pools[0].entries[", "].functions[0].options", 9, provider));

		this.entry("infinity").path("minecraft:enchantment/infinity").remolder(sequence(
				addEffect(AllurementEnchantmentEffects.RENDER_INFINITY_ARROW),
				addEffect(AllurementEnchantmentEffects.CAN_SHOOT_WITHOUT_ARROW)));
		this.entry("feather_falling").path("minecraft:enchantment/feather_falling").remolder(addEffect(AllurementEnchantmentEffects.PREVENTS_FARMLAND_TRAMPLE));

		this.entry("soul_speed").path(new ConditionedResourceSelector(
				new NamesResourceSelector("minecraft:enchantment/soul_speed"),
				config(AllurementConfig.COMMON.soulSpeedHurtsMore, "soul_speed_hurts_more", false)
		)).remolder(sequence(
				remove(target("effects[\"minecraft:location_changed\"[2]")),
				addEffect(
						AllurementEnchantmentEffects.INCREASE_INCOMING_DAMAGE,
						new ConditionalEffect<>(
								new MultiplyValue(LevelBasedValue.perLevel(1.25F, 0.25F)),
								Optional.of(AllOfCondition.allOf(
										LootItemEntityPropertyCondition.hasProperties(
												LootContext.EntityTarget.THIS,
												EntityPredicate.Builder.entity()
														.flags(EntityFlagsPredicate.Builder.flags().setOnGround(true))
														.movementAffectedBy(
																LocationPredicate.Builder.location()
																		.setBlock(BlockPredicate.Builder.block().of(BlockTags.SOUL_SPEED_BLOCKS))
														)
										)
								).build()))
				)));
	}

	public static SequenceRemolder replaceOptions(String half1, String half2, int start, Provider provider) {
		List<Remolder> remolders = Lists.newArrayList();
		for (int i = 0; i < 14; i++) {
			remolders.add(replace(target(half1 + (start + i) + half2), value(provider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(AllurementEnchantmentTags.ON_END_CITY_LOOT), RegistryCodecs.homogeneousList(Registries.ENCHANTMENT))));
		}
		return new SequenceRemolder(ImmutableList.copyOf(remolders));
	}

	public static ConfigValueCondition config(ModConfigSpec.ConfigValue<?> value, String key, boolean inverted) {
		return new ConfigValueCondition(AllurementConditions.CONFIG.get(), value, key, Maps.newHashMap(), inverted);
	}

	public static Remolder addEffect(DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> holder) {
		return add(target("effects[\"" + holder.getKey().location() + "\"]"), value(Unit.INSTANCE, Unit.CODEC));
	}

	public static Remolder addEffect(DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<EnchantmentValueEffect>>>> holder, ConditionalEffect<EnchantmentValueEffect> value) {
		return add(target("effects[\"" + holder.getKey().location() + "\"]"), value(List.of(value), Codec.list(ConditionalEffect.codec(EnchantmentValueEffect.CODEC, LootContextParamSets.ENCHANTED_DAMAGE))));
	}
}
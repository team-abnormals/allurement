package com.teamabnormals.allurement.common.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.allurement.core.AllurementConfig;
import com.teamabnormals.allurement.core.other.tags.AllurementEnchantmentTags;
import com.teamabnormals.allurement.core.other.tags.AllurementItemTags;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.HolderSet.Named;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class AnimalArmorLootModifier extends LootModifier {
	public static final MapCodec<AnimalArmorLootModifier> CODEC = RecordCodecBuilder.mapCodec(inst -> codecStart(inst).apply(inst, AnimalArmorLootModifier::new));

	public AnimalArmorLootModifier(LootItemCondition[] conditionsIn) {
		super(conditionsIn);
	}

	@Override
	protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		if (AllurementConfig.COMMON.enchantableAnimalArmor.get() && AllurementConfig.COMMON.enchantedAnimalArmorGenerates.get()) {
			if (!AllurementConfig.COMMON.unenchantedAnimalArmorLootTables.get().contains(context.getQueriedLootTableId().toString())) {
				RandomSource random = context.getRandom();
				for (ItemStack stack : generatedLoot) {
					if (stack.is(AllurementItemTags.ANIMAL_ARMOR_ENCHANTABLE)) {
						RegistryAccess access = context.getLevel().registryAccess();
						TagKey<Enchantment> key = context.getQueriedLootTableId().equals(BuiltInLootTables.END_CITY_TREASURE.location()) ? AllurementEnchantmentTags.ON_END_CITY_LOOT : EnchantmentTags.ON_RANDOM_LOOT;
						Optional<Named<Enchantment>> optional = access.registryOrThrow(Registries.ENCHANTMENT).getTag(key);
						EnchantmentHelper.enchantItem(random, stack, UniformGenerator.between(20.0F, 39.0F).getInt(context), access, optional);
					}
				}
			}
		}

		return generatedLoot;
	}

	@Override
	public MapCodec<? extends IGlobalLootModifier> codec() {
		return CODEC;
	}
}
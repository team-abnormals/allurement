package com.teamabnormals.allurement.common.loot;

import com.google.gson.JsonObject;
import com.teamabnormals.allurement.core.AllurementConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class HorseArmorLootModifier extends LootModifier {

	public HorseArmorLootModifier(LootItemCondition[] conditionsIn) {
		super(conditionsIn);
	}

	@Nonnull
	@Override
	protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
		if (AllurementConfig.COMMON.enchantableHorseArmor.get() && AllurementConfig.COMMON.enchantedHorseArmorGenerates.get()) {
			if (!AllurementConfig.COMMON.unenchantedHorseArmorLootTables.get().contains(context.getQueriedLootTableId().toString())) {
				Random random = context.getRandom();
				for (ItemStack stack : generatedLoot) {
					if (stack.getItem() instanceof HorseArmorItem) {
						EnchantmentHelper.enchantItem(random, stack, UniformGenerator.between(20.0F, 39.0F).getInt(context), true);
					}
				}
			}
		}

		return generatedLoot;
	}

	public static class HorseArmorSerializer extends GlobalLootModifierSerializer<HorseArmorLootModifier> {

		@Override
		public HorseArmorLootModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] conditions) {
			return new HorseArmorLootModifier(conditions);
		}

		@Override
		public JsonObject write(HorseArmorLootModifier instance) {
			return makeConditions(instance.conditions);
		}
	}
}
package com.minecraftabnormals.allurement.common.loot;

import com.google.gson.JsonObject;
import com.minecraftabnormals.allurement.core.AllurementConfig;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class HorseArmorLootModifier extends LootModifier {

	protected HorseArmorLootModifier(ILootCondition[] conditionsIn) {
		super(conditionsIn);
	}

	@Nonnull
	@Override
	protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
		if (AllurementConfig.COMMON.enchantableHorseArmor.get() && AllurementConfig.COMMON.enchantedHorseArmorGenerates.get()) {
			if (!AllurementConfig.COMMON.enchantedHorseArmorLootTableDenyList.get().contains(context.getQueriedLootTableId().toString())) {
				Random random = context.getRandom();
				for (ItemStack stack : generatedLoot) {
					if (stack.getItem() instanceof HorseArmorItem) {
						EnchantmentHelper.addRandomEnchantment(random, stack, RandomValueRange.of(20.0F, 39.0F).generateInt(random), true);
					}
				}
			}
		}

		return generatedLoot;
	}

	public static class HorseArmorSerializer extends GlobalLootModifierSerializer<HorseArmorLootModifier> {

		@Override
		public HorseArmorLootModifier read(ResourceLocation location, JsonObject object, ILootCondition[] conditions) {
			return new HorseArmorLootModifier(conditions);
		}

		@Override
		public JsonObject write(HorseArmorLootModifier instance) {
			return new JsonObject();
		}
	}
}
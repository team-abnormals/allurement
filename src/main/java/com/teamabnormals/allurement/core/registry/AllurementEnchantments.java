package com.teamabnormals.allurement.core.registry;

import com.teamabnormals.allurement.common.enchantment.effects.Launch;
import com.teamabnormals.allurement.common.enchantment.effects.RepairItem;
import com.teamabnormals.allurement.common.enchantment.effects.TransferEffects;
import com.teamabnormals.allurement.core.Allurement;
import com.teamabnormals.allurement.core.other.tags.AllurementEnchantmentTags;
import com.teamabnormals.allurement.core.other.tags.AllurementMobEffectTags;
import net.minecraft.advancements.critereon.DamageSourcePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentTarget;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.AddValue;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.DamageSourceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;

public class AllurementEnchantments {
	public static final ResourceKey<Enchantment> ALLEVIATING = create("alleviating");
	public static final ResourceKey<Enchantment> LAUNCH = create("launch");
	public static final ResourceKey<Enchantment> OBEDIENCE = create("obedience");
	public static final ResourceKey<Enchantment> REELING = create("reeling");
	public static final ResourceKey<Enchantment> REFORMING = create("reforming");
	public static final ResourceKey<Enchantment> SHOCKWAVE = create("shockwave");
	public static final ResourceKey<Enchantment> SPREAD_OF_AILMENTS = create("spread_of_ailments");
	public static final ResourceKey<Enchantment> VENGEANCE = create("vengeance");

	public static final ResourceKey<Enchantment> ASCENSION_CURSE = create("ascension_curse");
	public static final ResourceKey<Enchantment> FLEETING_CURSE = create("fleeting_curse");

	public static void bootstrap(BootstrapContext<Enchantment> context) {
		HolderGetter<Item> items = context.lookup(Registries.ITEM);
		HolderGetter<Enchantment> enchants = context.lookup(Registries.ENCHANTMENT);

		register(context, ALLEVIATING, Enchantment.enchantment(Enchantment.definition(
				items.getOrThrow(ItemTags.ARMOR_ENCHANTABLE), 2, 1, Enchantment.dynamicCost(25, 25), Enchantment.dynamicCost(75, 25), 4, EquipmentSlotGroup.ARMOR
		)).withEffect(AllurementEnchantmentEffects.HEAL_WITH_XP.get(), new AddValue(LevelBasedValue.constant(0.25F))
		).exclusiveWith(enchants.getOrThrow(AllurementEnchantmentTags.MENDING_EXCLUSIVE)));

		register(context, LAUNCH, Enchantment.enchantment(Enchantment.definition(
				items.getOrThrow(ItemTags.SWORD_ENCHANTABLE), 5, 2, Enchantment.dynamicCost(5, 20), Enchantment.dynamicCost(55, 20), 2, EquipmentSlotGroup.MAINHAND
		)).withEffect(
				EnchantmentEffectComponents.POST_ATTACK,
				EnchantmentTarget.ATTACKER,
				EnchantmentTarget.VICTIM,
				new Launch(LevelBasedValue.perLevel(0.35F)),
				DamageSourceCondition.hasDamageSource(DamageSourcePredicate.Builder.damageType().isDirect(true))
		).exclusiveWith(enchants.getOrThrow(AllurementEnchantmentTags.KNOCKBACK_EXCLUSIVE)));

		register(context, OBEDIENCE, Enchantment.enchantment(Enchantment.definition(
				items.getOrThrow(ItemTags.ARMOR_ENCHANTABLE), 2, 1, Enchantment.constantCost(15), Enchantment.constantCost(65), 4, EquipmentSlotGroup.BODY
		)).withEffect(AllurementEnchantmentEffects.PREVENT_ANIMAL_ROAMING.get()));

		register(context, REELING, Enchantment.enchantment(Enchantment.definition(
				items.getOrThrow(ItemTags.CROSSBOW_ENCHANTABLE), 2, 2, Enchantment.dynamicCost(12, 20), Enchantment.dynamicCost(37, 20), 4, EquipmentSlotGroup.MAINHAND
		)).withEffect(
				EnchantmentEffectComponents.KNOCKBACK,
				new AddValue(LevelBasedValue.perLevel(-1.0F)),
				LootItemEntityPropertyCondition.hasProperties(
						LootContext.EntityTarget.DIRECT_ATTACKER, EntityPredicate.Builder.entity().of(EntityTypeTags.ARROWS).build()
				)
		));

		EntityPredicate.Builder reformingPredicate = EntityPredicate.Builder.entity().periodicTick(600);
		register(context, REFORMING, Enchantment.enchantment(Enchantment.definition(
				items.getOrThrow(ItemTags.DURABILITY_ENCHANTABLE), 2, 1, Enchantment.dynamicCost(25, 25), Enchantment.dynamicCost(75, 25), 4, EquipmentSlotGroup.ANY
		)).withEffect(
				EnchantmentEffectComponents.TICK,
				new RepairItem(LevelBasedValue.constant(1.0F)),
				LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, reformingPredicate)
		).exclusiveWith(enchants.getOrThrow(AllurementEnchantmentTags.MENDING_EXCLUSIVE)));

		register(context, SHOCKWAVE, Enchantment.enchantment(Enchantment.definition(
				items.getOrThrow(ItemTags.FOOT_ARMOR_ENCHANTABLE), 5, 4, Enchantment.dynamicCost(5, 6), Enchantment.dynamicCost(11, 6), 2, EquipmentSlotGroup.ARMOR
		)).withEffect(AllurementEnchantmentEffects.SHOCKWAVE.get(), new AddValue(LevelBasedValue.perLevel(1.0F))
		).withEffect(AllurementEnchantmentEffects.TRAMPLE_FARMLAND.get(), new AddValue(LevelBasedValue.perLevel(1.0F))
		).exclusiveWith(enchants.getOrThrow(AllurementEnchantmentTags.FALLING_EXCLUSIVE)));

		register(context, SPREAD_OF_AILMENTS, Enchantment.enchantment(Enchantment.definition(
				items.getOrThrow(ItemTags.CROSSBOW_ENCHANTABLE), 2, 3, Enchantment.dynamicCost(12, 20), Enchantment.constantCost(50), 4, EquipmentSlotGroup.MAINHAND
		)).withEffect(
				EnchantmentEffectComponents.POST_ATTACK,
				EnchantmentTarget.ATTACKER,
				EnchantmentTarget.VICTIM,
				new TransferEffects(LevelBasedValue.perLevel(200.0F), AllurementMobEffectTags.SPREAD_OF_AILMENTS_CANNOT_INFLICT),
				LootItemEntityPropertyCondition.hasProperties(
						LootContext.EntityTarget.DIRECT_ATTACKER, EntityPredicate.Builder.entity().of(EntityTypeTags.ARROWS).build()
				)
		));

		register(context, VENGEANCE, Enchantment.enchantment(Enchantment.definition(
				items.getOrThrow(ItemTags.ARMOR_ENCHANTABLE), items.getOrThrow(ItemTags.CHEST_ARMOR_ENCHANTABLE), 1, 3, Enchantment.dynamicCost(10, 20), Enchantment.dynamicCost(60, 20), 8, EquipmentSlotGroup.ANY
		)).withEffect(AllurementEnchantmentEffects.STORE_INCOMING_DAMAGE.get(), new AddValue(LevelBasedValue.perLevel(0.0375F))
		).exclusiveWith(enchants.getOrThrow(AllurementEnchantmentTags.THORNS_EXCLUSIVE)));

		register(context, ASCENSION_CURSE, Enchantment.enchantment(Enchantment.definition(
				items.getOrThrow(ItemTags.VANISHING_ENCHANTABLE), 1, 1, Enchantment.constantCost(25), Enchantment.constantCost(50), 8, EquipmentSlotGroup.ANY
		)).withEffect(AllurementEnchantmentEffects.ITEM_ASCEND.get()));

		register(context, FLEETING_CURSE, Enchantment.enchantment(Enchantment.definition(
				items.getOrThrow(ItemTags.VANISHING_ENCHANTABLE), 1, 1, Enchantment.constantCost(25), Enchantment.constantCost(50), 8, EquipmentSlotGroup.ANY
		)).withEffect(AllurementEnchantmentEffects.ITEM_FLEE.get()));
	}

	private static ResourceKey<Enchantment> create(String name) {
		return ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(Allurement.MOD_ID, name));
	}

	private static void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
		context.register(key, builder.build(key.location()));
	}
}
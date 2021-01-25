package com.minecraftabnormals.allurement.core.other;

import com.google.common.collect.Maps;
import com.minecraftabnormals.allurement.core.Allurement;
import com.minecraftabnormals.allurement.core.registry.AllurementEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

@Mod.EventBusSubscriber(modid = Allurement.MOD_ID)
public class AllurementEvents {
	public static final Map<String, Integer> ENCHANTABILITY_MAP = Util.make(Maps.newHashMap(), (map) -> {
		map.put("minecraft:leather_horse_armor", 15);
		map.put("minecraft:iron_horse_armor", 9);
		map.put("minecraft:golden_horse_armor", 25);
		map.put("minecraft:diamond_horse_armor", 10);
		map.put("nether_extension:netherite_horse_armor", 15);
		map.put("caverns_and_chasms:necromium_horse_armor", 13);
	});

	@SubscribeEvent
	public static void onLivingFall(LivingFallEvent event) {
		LivingEntity entity = event.getEntityLiving();
		World world = entity.getEntityWorld();
		int level = EnchantmentHelper.getEnchantmentLevel(AllurementEnchantments.SHOCKWAVE.get(), entity.getItemStackFromSlot(EquipmentSlotType.FEET));

		EffectInstance effectinstance = entity.getActivePotionEffect(Effects.JUMP_BOOST);
		float f = effectinstance == null ? 0.0F : (float) (effectinstance.getAmplifier() + 1);
		int damage = MathHelper.ceil((event.getDistance() - 3.0F - f) * event.getDamageMultiplier());

		if (level > 0 && damage > 0) {
			for (LivingEntity target : world.getEntitiesWithinAABB(LivingEntity.class, entity.getBoundingBox().grow(level, 0.0D, level))) {
				if (entity != target)
					target.attackEntityFrom(AllurementDamageSources.causeShockwaveDamage(entity), event.getDistance());
			}

			if (world instanceof ServerWorld) {
				((ServerWorld) world).spawnParticle(ParticleTypes.CLOUD, entity.getPosX(), entity.getPosY(), entity.getPosZ(), 200, level, 0.5, level, 0);
			}
		}
	}

	@SubscribeEvent
	public static void onLivingUpdate(LivingUpdateEvent event) {
		LivingEntity entity = event.getEntityLiving();
		World world = entity.getEntityWorld();

		for (EquipmentSlotType slot : EquipmentSlotType.values()) {
			ItemStack stack = entity.getItemStackFromSlot(slot);
			int level = EnchantmentHelper.getEnchantmentLevel(AllurementEnchantments.REFORMING.get(), stack);
			if (!stack.isEmpty() && stack.isDamaged() && level > 0 && world.getGameTime() % 600 == 0) {
				stack.setDamage(stack.getDamage() - 1);
			}
		}
	}
}

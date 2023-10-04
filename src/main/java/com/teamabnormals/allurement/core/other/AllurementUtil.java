package com.teamabnormals.allurement.core.other;

import com.google.common.collect.Maps;
import com.teamabnormals.allurement.core.Allurement;
import com.teamabnormals.allurement.core.AllurementConfig;
import net.minecraft.Util;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import vazkii.quark.content.tools.module.ColorRunesModule;

import java.util.Map;

@EventBusSubscriber(modid = Allurement.MOD_ID)
public class AllurementUtil {
	public static final Map<String, Integer> ENCHANTABILITY_MAP = Util.make(Maps.newHashMap(), (map) -> {
		map.put("minecraft:leather_horse_armor", 15);
		map.put("minecraft:iron_horse_armor", 9);
		map.put("minecraft:golden_horse_armor", 25);
		map.put("minecraft:diamond_horse_armor", 10);
		map.put("caverns_and_chasms:netherite_horse_armor", 15);
		map.put("caverns_and_chasms:silver_horse_armor", 17);
		map.put("caverns_and_chasms:necromium_horse_armor", 13);
	});

	public static int getTotalEnchantmentLevel(Enchantment ench, LivingEntity entity, EquipmentSlot.Type group) {
		int count = 0;
		for (EquipmentSlot slot : EquipmentSlot.values()) {
			if (slot.getType() == group) {
				count += entity.getItemBySlot(slot).getEnchantmentLevel(ench);
			}
		}
		return count;
	}

	public static int getXpNeededForNextLevel(int experienceLevel) {
		int original = experienceLevel >= 30 ? 112 + (experienceLevel - 30) * 9 : (experienceLevel >= 15 ? 37 + (experienceLevel - 15) * 5 : 7 + experienceLevel * 2);
		if (AllurementConfig.COMMON.removeLevelScaling.get()) {
			int xpPerLevel = AllurementConfig.COMMON.experiencePerLevel.get();
			if (AllurementConfig.COMMON.removeLevelScalingAfterCap.get()) {
				return Math.min(original, xpPerLevel);
			} else {
				return xpPerLevel;
			}
		}

		return original;
	}

	public static void ascendItem(ItemEntity entity) {
		Vec3 motion = entity.getDeltaMovement();
		entity.setDeltaMovement(motion.x, (0.25D - motion.y) * 0.3D, motion.z);
	}

	public static void repelItem(ItemEntity entity) {
		Level level = entity.level();
		Vec3 motion = entity.getDeltaMovement();

		double distance = -1.0D;
		LivingEntity followingPlayer = null;
		for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(5.0D), (livingEntity) -> true)) {
			double newDistance = living.distanceToSqr(entity.getX(), entity.getY(), entity.getZ());
			if (distance == -1.0D || newDistance < distance) {
				distance = newDistance;
				followingPlayer = living;
			}
		}

		if (followingPlayer != null) {
			Vec3 vec3 = new Vec3(entity.getX() - followingPlayer.getX(), entity.getY() - (followingPlayer.getY() + (double) followingPlayer.getEyeHeight() / 2.0D), entity.getZ() - followingPlayer.getZ());
			Vec3 amount = vec3.normalize().multiply(0.1D, 0.0D, 0.1);
			if (!entity.onGround()) {
				amount = amount.multiply(0.2D, 0.0D, 0.2D);
			} else {
				amount = amount.add(0.0D, 0.25D, 0.0D);
			}

			entity.setDeltaMovement(motion.add(amount));
		}
	}

	public static void setColorRuneTarget(ItemStack stack) {
		if (ModList.get().isLoaded("quark")) ColorRunesModule.setTargetStack(stack);
	}
}

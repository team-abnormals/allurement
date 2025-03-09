package com.teamabnormals.allurement.core.other;

import com.teamabnormals.allurement.core.AllurementConfig;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class AllurementUtil {


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
		// if (ModList.get().isLoaded("quark")) ColorRunesModule.setTargetStack(stack);
	}

	public static int getTagEnchantmentLevel(Level level, ResourceKey<Enchantment> enchantment, ItemStack stack) {
		return EnchantmentHelper.getTagEnchantmentLevel(getEnchantment(level, enchantment), stack);
	}

	public static Holder<Enchantment> getEnchantment(Level level, ResourceKey<Enchantment> enchantment) {
		return level.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(enchantment);
	}
}

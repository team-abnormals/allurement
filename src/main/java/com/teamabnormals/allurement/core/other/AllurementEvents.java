package com.teamabnormals.allurement.core.other;

import com.teamabnormals.allurement.common.dispenser.IronIngotDispenseBehavior;
import com.teamabnormals.allurement.core.Allurement;
import com.teamabnormals.allurement.core.AllurementConfig;
import com.teamabnormals.allurement.core.other.tags.AllurementBlockTags;
import com.teamabnormals.allurement.core.registry.AllurementEnchantmentEffects;
import com.teamabnormals.allurement.core.registry.datapack.AllurementDamageTypes;
import com.teamabnormals.blueprint.common.world.storage.tracking.IDataManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.living.LivingGetProjectileEvent;
import net.neoforged.neoforge.event.entity.player.ArrowNockEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.neoforged.neoforge.event.level.BlockEvent.FarmlandTrampleEvent;
import org.apache.commons.lang3.mutable.MutableFloat;

import java.util.Optional;
import java.util.stream.Stream;

@EventBusSubscriber(modid = Allurement.MOD_ID)
public class AllurementEvents {

	@SubscribeEvent
	public static void onLivingFall(LivingFallEvent event) {
		LivingEntity entity = event.getEntity();
		if (entity.level() instanceof ServerLevel level && !entity.isPassenger()) {
			float fallDistance = event.getDistance() - (float) entity.getAttributeValue(Attributes.SAFE_FALL_DISTANCE);
			int damage = Mth.ceil((double) (fallDistance * event.getDamageMultiplier()) * entity.getAttributeValue(Attributes.FALL_DAMAGE_MULTIPLIER));

			if (damage > 0.0F) {
				MutableFloat shockwaveRadius = new MutableFloat();
				EnchantmentHelper.runIterationOnEquipment(entity, (ench, eLevel, use) -> {
					ench.value().modifyEntityFilteredValue(AllurementEnchantmentEffects.SHOCKWAVE.get(), level, eLevel, use.itemStack(), entity, shockwaveRadius);
				});

				if (shockwaveRadius.floatValue() > 0.0F) {
					float radius = shockwaveRadius.floatValue();
					for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(radius, 0.0D, radius))) {
						if (entity != target && !entity.hasPassenger(target) && !target.hasPassenger(entity)) {
							target.hurt(AllurementDamageTypes.shockwave(level, entity, entity.hasControllingPassenger() ? entity.getControllingPassenger() : entity), damage);
						}
					}

					level.sendParticles(ParticleTypes.CLOUD, entity.getX(), entity.getY(), entity.getZ(), 200, radius, 0.5F, radius, 0.0F);
				}

				MutableFloat farmlandRadius = new MutableFloat();
				EnchantmentHelper.runIterationOnEquipment(entity, (ench, eLevel, use) -> {
					ench.value().modifyEntityFilteredValue(AllurementEnchantmentEffects.TRAMPLE_FARMLAND.get(), level, eLevel, use.itemStack(), entity, farmlandRadius);
				});
				if (farmlandRadius.floatValue() > 0.0F) {
					float radius = farmlandRadius.floatValue();
					Stream<BlockPos> affectedBlocks = BlockPos.betweenClosedStream(entity.getBoundingBox().inflate(radius, 0.0D, radius).move(0, -1.0F, 0));
					affectedBlocks.forEach(pos -> {
						BlockState state = level.getBlockState(pos);
						if (state.is(AllurementBlockTags.TRAMPLED_BY_SHOCKWAVE)) {
							if (CommonHooks.onFarmlandTrample(level, pos, Blocks.DIRT.defaultBlockState(), fallDistance, entity)) {
								FarmBlock.turnToDirt(entity, state, level, pos);
							}
						}
					});
				}
			}


		}
	}

	@SubscribeEvent
	public static void onPlayerBreak(PlayerEvent.BreakSpeed event) {
		if (event.getState().is(AllurementBlockTags.MINEABLE_WITH_BANE_OF_ARTHROPODS)) {
			int level = AllurementUtil.getTagEnchantmentLevel(event.getEntity().level(), Enchantments.BANE_OF_ARTHROPODS, event.getEntity().getMainHandItem());
			if (level > 0) {
				event.setNewSpeed(event.getOriginalSpeed() + (5.0F * level * level));
			}
		}
	}

	@SubscribeEvent
	public static void onFarmlandTrample(FarmlandTrampleEvent event) {
		if (event.getEntity() instanceof LivingEntity living && AllurementConfig.COMMON.featherFallingPreventsTrampling.get()) {
			for (ItemStack stack : living.getArmorAndBodyArmorSlots())
				if (EnchantmentHelper.has(stack, AllurementEnchantmentEffects.PREVENTS_FARMLAND_TRAMPLE.get())) {
					event.setCanceled(true);
				}
		}
	}

	@SubscribeEvent
	public static void onLivingHurt(LivingDamageEvent.Pre event) {
		LivingEntity entity = event.getEntity();
		Entity source = event.getSource().getEntity();
		IDataManager manager = (IDataManager) entity;

		if (entity.level() instanceof ServerLevel serverLevel) {
			MutableFloat increasedDamage = new MutableFloat(event.getNewDamage());
			EnchantmentHelper.runIterationOnEquipment(entity, (ench, eLevel, use) -> {
				ench.value().modifyEntityFilteredValue(AllurementEnchantmentEffects.INCREASE_INCOMING_DAMAGE.get(), serverLevel, eLevel, use.itemStack(), entity, increasedDamage);
			});

			if (increasedDamage.floatValue() != event.getNewDamage()) {
				event.setNewDamage(increasedDamage.floatValue());
			}

			if (source instanceof LivingEntity attacker) {
				MutableFloat mutablefloat = new MutableFloat();
				EnchantmentHelper.runIterationOnEquipment(entity, (ench, eLevel, use) -> {
					ench.value().modifyEntityFilteredValue(AllurementEnchantmentEffects.STORE_INCOMING_DAMAGE.get(), serverLevel, eLevel, use.itemStack(), entity, mutablefloat);
				});

				if (mutablefloat.floatValue() > 0.0F) {
					manager.setValue(AllurementTrackedData.ABSORBED_DAMAGE, event.getNewDamage() * mutablefloat.floatValue());
				}

				Optional<EnchantedItemInUse> entry = EnchantmentHelper.getRandomItemWith(AllurementEnchantmentEffects.STORE_INCOMING_DAMAGE.get(), attacker, p -> true);
				if (entry.isPresent() && entry.get().inSlot() != null) {
					IDataManager attackManager = (IDataManager) attacker;
					float absorbedDamage = attackManager.getValue(AllurementTrackedData.ABSORBED_DAMAGE);
					if (absorbedDamage > 0.0F) {
						event.setNewDamage(event.getNewDamage() + absorbedDamage);
						attackManager.setValue(AllurementTrackedData.ABSORBED_DAMAGE, 0.0F);
						entry.get().itemStack().hurtAndBreak(2, attacker, entry.get().inSlot());
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void onExperienceDrop(LivingExperienceDropEvent event) {
		if (event.getEntity() instanceof EnderDragon && AllurementConfig.COMMON.adjustEnderDragonExperienceDrop.get()) {
			int amount = event.getOriginalExperience();
			int shouldDrop = AllurementConfig.COMMON.enderDragonExperienceDrop.get();
			int shouldDropRespawn = AllurementConfig.COMMON.respawnedEnderDragonExperienceDrop.get();
			if (amount == 960) {
				event.setDroppedExperience(Mth.floor((float) shouldDrop * 0.08F));
			} else if (amount == 2400) {
				event.setDroppedExperience(Mth.floor((float) shouldDrop * 0.2F));
			} else if (amount == 40) {
				event.setDroppedExperience(Mth.floor((float) shouldDropRespawn * 0.08F));
			} else if (amount == 100) {
				event.setDroppedExperience(Mth.floor((float) shouldDropRespawn * 0.2F));
			}
		}
	}

	@SubscribeEvent
	public static void onArrowNock(ArrowNockEvent event) {
		Player player = event.getEntity();
		ItemStack bow = event.getBow();
		if (!AllurementConfig.COMMON.infinityRequiresArrows.get() && EnchantmentHelper.has(bow, AllurementEnchantmentEffects.CAN_SHOOT_WITHOUT_ARROW.get()) && player.getProjectile(bow).isEmpty()) {
			player.startUsingItem(event.getHand());
			event.setAction(InteractionResultHolder.consume(bow));
		}
	}

	@SubscribeEvent
	public static void livingGetProjectile(LivingGetProjectileEvent event) {
		LivingEntity entity = event.getEntity();
		ItemStack bow = event.getProjectileWeaponItemStack();
		if (!AllurementConfig.COMMON.infinityRequiresArrows.get() && EnchantmentHelper.has(bow, AllurementEnchantmentEffects.CAN_SHOOT_WITHOUT_ARROW.get()) && event.getProjectileItemStack().isEmpty()) {
			if (bow.getItem() instanceof ProjectileWeaponItem projectileWeaponItem && entity instanceof Player player) {
				event.setProjectileItemStack(projectileWeaponItem.getDefaultCreativeAmmo(player, bow));
			}
		}
	}

	@SubscribeEvent
	public static void onRightClickBlock(RightClickBlock event) {
		Level level = event.getLevel();
		BlockPos pos = event.getPos();
		BlockState state = level.getBlockState(pos);
		Player player = event.getEntity();
		ItemStack stack = event.getItemStack();

		if (AllurementConfig.COMMON.anvilIngotRepairing.get() && stack.is(Items.IRON_INGOT) && IronIngotDispenseBehavior.canBeRepaired(state) && player.isSecondaryUseActive()) {
			if (state.is(Blocks.CHIPPED_ANVIL)) {
				IronIngotDispenseBehavior.repairAnvil(Blocks.ANVIL, level, pos);
			} else if (state.is(Blocks.DAMAGED_ANVIL)) {
				IronIngotDispenseBehavior.repairAnvil(Blocks.CHIPPED_ANVIL, level, pos);
			}

			if (!player.getAbilities().instabuild) {
				stack.shrink(1);
			}

			event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));
			event.setCanceled(true);
		}
	}
}

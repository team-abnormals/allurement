package com.teamabnormals.allurement.core.other;

import com.teamabnormals.allurement.core.Allurement;
import com.teamabnormals.allurement.core.AllurementConfig;
import com.teamabnormals.allurement.core.mixin.LivingEntityAccessor;
import com.teamabnormals.allurement.core.other.tags.AllurementBlockTags;
import com.teamabnormals.allurement.core.registry.AllurementEnchantments;
import com.teamabnormals.blueprint.common.world.storage.tracking.IDataManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent.FarmlandTrampleEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.util.Map.Entry;
import java.util.stream.Stream;

@EventBusSubscriber(modid = Allurement.MOD_ID)
public class AllurementEvents {

	@SubscribeEvent
	public static void onLivingFall(LivingFallEvent event) {
		LivingEntity entity = event.getEntity();
		Level world = entity.getCommandSenderWorld();
		int level = EnchantmentHelper.getTagEnchantmentLevel(AllurementEnchantments.SHOCKWAVE.get(), entity.getItemBySlot(EquipmentSlot.FEET));

		MobEffectInstance effectInstance = entity.getEffect(MobEffects.JUMP);
		float f = effectInstance == null ? 0.0F : (float) (effectInstance.getAmplifier() + 1);
		int damage = Mth.ceil((event.getDistance() - 3.0F - f) * event.getDamageMultiplier());

		if (level > 0 && damage > 0) {
			for (LivingEntity target : world.getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(level, 0.0D, level))) {
				if (entity != target)
					target.hurt(AllurementDamageSources.causeShockwaveDamage(entity), damage);
			}

			if (AllurementConfig.COMMON.shockwaveTramplesFarmland.get()) {
				Stream<BlockPos> affectedBlocks = BlockPos.betweenClosedStream(entity.getBoundingBox().inflate(level, 0.0D, level).move(0, -1.0F, 0));
				affectedBlocks.forEach(pos -> {
					BlockState state = world.getBlockState(pos);
					if (state.getBlock() instanceof FarmBlock) {
						if (!world.isClientSide && ForgeHooks.onFarmlandTrample(world, pos, Blocks.DIRT.defaultBlockState(), event.getDistance(), entity)) {
							FarmBlock.turnToDirt(state, world, pos);
						}
					}
				});
			}

			if (world instanceof ServerLevel) {
				((ServerLevel) world).sendParticles(ParticleTypes.CLOUD, entity.getX(), entity.getY(), entity.getZ(), 200, level, 0.5, level, 0);
			}
		}
	}

	@SubscribeEvent
	public static void onPlayerBreak(PlayerEvent.BreakSpeed event) {
		int baneOfArthropodsLevel = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.BANE_OF_ARTHROPODS, event.getEntity().getMainHandItem());
		if (AllurementConfig.COMMON.baneOfArthropodsBreaksCobwebsFaster.get() && event.getState().is(AllurementBlockTags.MINEABLE_WITH_BANE_OF_ARTHROPODS) && baneOfArthropodsLevel > 0)
			event.setNewSpeed(event.getOriginalSpeed() + (5.0F * baneOfArthropodsLevel * baneOfArthropodsLevel));
	}

	@SubscribeEvent
	public static void onFarmlandTrample(FarmlandTrampleEvent event) {
		if (event.getEntity() instanceof LivingEntity && AllurementConfig.COMMON.featherFallingPreventsTrampling.get()) {
			if (EnchantmentHelper.getTagEnchantmentLevel(Enchantments.FALL_PROTECTION, ((LivingEntity) event.getEntity()).getItemBySlot(EquipmentSlot.FEET)) > 0)
				event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onLivingHurt(LivingHurtEvent event) {
		LivingEntity entity = event.getEntity();
		Entity source = event.getSource().getEntity();
		IDataManager manager = (IDataManager) entity;

		if (source instanceof LivingEntity attacker) {
			int count = AllurementUtil.getTotalEnchantmentLevel(AllurementEnchantments.VENGEANCE.get(), entity, EquipmentSlot.Type.ARMOR);
			if (count > 0) {
				manager.setValue(AllurementTrackedData.ABSORBED_DAMAGE, event.getAmount() * count * AllurementConfig.COMMON.vengeanceDamageFactor.get().floatValue());
			}

			Entry<EquipmentSlot, ItemStack> entry = EnchantmentHelper.getRandomItemWith(AllurementEnchantments.VENGEANCE.get(), attacker);
			if (entry != null) {
				IDataManager attackManager = (IDataManager) attacker;
				float absorbedDamage = attackManager.getValue(AllurementTrackedData.ABSORBED_DAMAGE);

				if (absorbedDamage > 0.0F) {
					event.setAmount(event.getAmount() + absorbedDamage);
					attackManager.setValue(AllurementTrackedData.ABSORBED_DAMAGE, 0.0F);
					entry.getValue().hurtAndBreak(2, attacker, (livingEntity) -> livingEntity.broadcastBreakEvent(entry.getKey()));
				}
			}
		}

		if (AllurementConfig.COMMON.soulSpeedHurtsMore.get() && event.getEntity() != null) {
			if (EnchantmentHelper.hasSoulSpeed(entity) && ((LivingEntityAccessor) entity).isSoulSpeedBlock()) {
				event.setAmount(event.getAmount() * AllurementConfig.COMMON.soulSpeedDamageFactor.get().floatValue());
			}
		}
	}

	@SubscribeEvent
	public static void onLivingUpdate(LivingTickEvent event) {
		LivingEntity entity = event.getEntity();
		Level world = entity.getCommandSenderWorld();
		for (EquipmentSlot slot : EquipmentSlot.values()) {
			ItemStack stack = entity.getItemBySlot(slot);
			int level = EnchantmentHelper.getTagEnchantmentLevel(AllurementEnchantments.REFORMING.get(), stack);
			if (!stack.isEmpty() && stack.isDamaged() && level > 0 && world.getGameTime() % AllurementConfig.COMMON.reformingTickRate.get() == 0) {
				stack.setDamageValue(stack.getDamageValue() - 1);
			}
		}
	}

	@SubscribeEvent
	public static void onArrowNock(ArrowNockEvent event) {
		Player player = event.getEntity();
		ItemStack bow = event.getBow();
		if (!AllurementConfig.COMMON.infinityRequiresArrows.get() && EnchantmentHelper.getTagEnchantmentLevel(Enchantments.INFINITY_ARROWS, bow) > 0 && player.getProjectile(bow).isEmpty()) {
			player.startUsingItem(event.getHand());
			event.setAction(InteractionResultHolder.consume(bow));
		}
	}
}

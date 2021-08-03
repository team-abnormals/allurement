package com.minecraftabnormals.allurement.core.other;

import com.minecraftabnormals.abnormals_core.common.world.storage.tracking.IDataManager;
import com.minecraftabnormals.allurement.core.Allurement;
import com.minecraftabnormals.allurement.core.AllurementConfig;
import com.minecraftabnormals.allurement.core.mixin.LivingEntityAccessor;
import com.minecraftabnormals.allurement.core.registry.AllurementEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map.Entry;

@Mod.EventBusSubscriber(modid = Allurement.MOD_ID)
public class AllurementEvents {

	@SubscribeEvent
	public static void onLivingFall(LivingFallEvent event) {
		LivingEntity entity = event.getEntityLiving();
		World world = entity.getCommandSenderWorld();
		int level = EnchantmentHelper.getItemEnchantmentLevel(AllurementEnchantments.SHOCKWAVE.get(), entity.getItemBySlot(EquipmentSlotType.FEET));

		EffectInstance effectinstance = entity.getEffect(Effects.JUMP);
		float f = effectinstance == null ? 0.0F : (float) (effectinstance.getAmplifier() + 1);
		int damage = MathHelper.ceil((event.getDistance() - 3.0F - f) * event.getDamageMultiplier());

		if (level > 0 && damage > 0) {
			for (LivingEntity target : world.getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(level, 0.0D, level))) {
				if (entity != target)
					target.hurt(AllurementDamageSources.causeShockwaveDamage(entity), damage);
			}

			if (world instanceof ServerWorld) {
				((ServerWorld) world).sendParticles(ParticleTypes.CLOUD, entity.getX(), entity.getY(), entity.getZ(), 200, level, 0.5, level, 0);
			}
		}
	}

	@SubscribeEvent
	public static void onLivingHurt(LivingHurtEvent event) {
		LivingEntity entity = event.getEntityLiving();
		Entity source = event.getSource().getEntity();
		IDataManager manager = (IDataManager) entity;

		if (source instanceof LivingEntity) {
			int count = AllurementUtil.getTotalEnchantmentLevel(AllurementEnchantments.VENGEANCE.get(), entity, EquipmentSlotType.Group.ARMOR);
			if (count > 0) {
				manager.setValue(Allurement.ABSORBED_DAMAGE, event.getAmount() * count * AllurementConfig.COMMON.vengeanceDamageFactor.get());
			}

			LivingEntity attacker = (LivingEntity) source;
			Entry<EquipmentSlotType, ItemStack> entry = EnchantmentHelper.getRandomItemWith(AllurementEnchantments.VENGEANCE.get(), attacker);
			if (entry != null) {
				IDataManager attackManager = (IDataManager) attacker;
				float absorbedDamage = attackManager.getValue(Allurement.ABSORBED_DAMAGE);

				if (absorbedDamage > 0.0F) {
					event.setAmount(event.getAmount() + absorbedDamage);
					attackManager.setValue(Allurement.ABSORBED_DAMAGE, 0.0F);
					entry.getValue().hurtAndBreak(2, attacker, (livingEntity) -> livingEntity.broadcastBreakEvent(entry.getKey()));
				}
			}

			ItemStack weapon = attacker.getItemBySlot(EquipmentSlotType.MAINHAND);
			int missileLevel = EnchantmentHelper.getItemEnchantmentLevel(AllurementEnchantments.LAUNCH.get(), weapon);
			if (missileLevel > 0) {
				entity.setOnGround(false);
				entity.push(0, AllurementConfig.COMMON.launchVerticalFactor.get() * (missileLevel + 1) * (1.0D - entity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE)), 0);
			}
		}

		if (AllurementConfig.COMMON.soulSpeedHurtsMore.get() && event.getEntityLiving() != null) {
			if (EnchantmentHelper.hasSoulSpeed(entity) && ((LivingEntityAccessor) entity).isSoulSpeedBlock()) {
				event.setAmount(event.getAmount() * AllurementConfig.COMMON.soulSpeedDamageFactor.get());
			}
		}
	}

	@SubscribeEvent
	public static void onLivingUpdate(LivingUpdateEvent event) {
		LivingEntity entity = event.getEntityLiving();
		World world = entity.getCommandSenderWorld();

		for (EquipmentSlotType slot : EquipmentSlotType.values()) {
			ItemStack stack = entity.getItemBySlot(slot);
			int level = EnchantmentHelper.getItemEnchantmentLevel(AllurementEnchantments.REFORMING.get(), stack);
			if (!stack.isEmpty() && stack.isDamaged() && level > 0 && world.getGameTime() % AllurementConfig.COMMON.reformingTickRate.get() == 0) {
				stack.setDamageValue(stack.getDamageValue() - 1);
			}
		}
	}

	@SubscribeEvent
	public static void onArrowNock(ArrowNockEvent event) {
		PlayerEntity player = event.getPlayer();
		ItemStack bow = event.getBow();
		if (!AllurementConfig.COMMON.infinityRequiresArrows.get() && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, bow) > 0 && player.getProjectile(bow).isEmpty()) {
			player.startUsingItem(event.getHand());
			event.setAction(ActionResult.consume(bow));
		}
	}
}

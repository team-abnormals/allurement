package com.teamabnormals.allurement.core.mixin;

import com.teamabnormals.allurement.core.other.AllurementTrackedData;
import com.teamabnormals.allurement.core.other.tags.AllurementMobEffectTags;
import com.teamabnormals.allurement.core.registry.AllurementEnchantments;
import com.teamabnormals.blueprint.common.world.storage.tracking.IDataManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(CrossbowItem.class)
public abstract class CrossbowItemMixin {

	@ModifyVariable(method = "getArrow", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/item/ArrowItem;createArrow(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;)Lnet/minecraft/world/entity/projectile/AbstractArrow;"))
	private static AbstractArrow getArrow(AbstractArrow abstractArrow, Level worldIn, LivingEntity shooter, ItemStack crossbow, ItemStack ammo) {
		int level = EnchantmentHelper.getTagEnchantmentLevel(AllurementEnchantments.REELING.get(), crossbow);
		if (level > 0) {
			abstractArrow.setKnockback(-level);
		}

		int ailmentsLevel = EnchantmentHelper.getTagEnchantmentLevel(AllurementEnchantments.SPREAD_OF_AILMENTS.get(), crossbow);
		if (ailmentsLevel > 0 && abstractArrow instanceof Arrow arrow && !worldIn.isClientSide()) {
			IDataManager manager = (IDataManager) arrow;
			ListTag listTag = new ListTag();

			shooter.getActiveEffects().forEach(effect -> {
				if (!effect.getEffect().isInstantenous() && !effect.isAmbient() && !ForgeRegistries.MOB_EFFECTS.tags().getTag(AllurementMobEffectTags.SPREAD_OF_AILMENTS_CANNOT_INFLICT).contains(effect.getEffect())) {
					MobEffectInstance effectInstance = new MobEffectInstance(effect.getEffect(), 140 * ailmentsLevel, effect.getAmplifier());
					listTag.add(effectInstance.save(new CompoundTag()));
					manager.setValue(AllurementTrackedData.ARROW_EFFECTS, listTag);
				}
			});

			return arrow;
		}

		return abstractArrow;
	}
}
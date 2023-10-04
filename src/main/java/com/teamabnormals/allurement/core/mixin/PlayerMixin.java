package com.teamabnormals.allurement.core.mixin;

import com.teamabnormals.allurement.core.AllurementConfig;
import com.teamabnormals.allurement.core.other.AllurementUtil;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {

	@Shadow
	public int experienceLevel;

	@Shadow
	public float experienceProgress;

	protected PlayerMixin(EntityType<? extends LivingEntity> type, Level level) {
		super(type, level);
	}

	@Inject(at = @At("RETURN"), method = "getXpNeededForNextLevel", cancellable = true)
	private void xpBarCap(CallbackInfoReturnable<Integer> cir) {
		if (AllurementConfig.COMMON.removeLevelScaling.get()) {
			int xpPerLevel = AllurementConfig.COMMON.experiencePerLevel.get();
			if (AllurementConfig.COMMON.removeLevelScalingAfterCap.get()) {
				cir.setReturnValue(Math.min(cir.getReturnValue(), xpPerLevel));
			} else {
				cir.setReturnValue(xpPerLevel);
			}
		}
	}

	@Inject(at = @At("RETURN"), method = "getExperienceReward", cancellable = true)
	private void getExperienceReward(CallbackInfoReturnable<Integer> cir) {
		if (AllurementConfig.COMMON.dropExperiencePercentage.get() && !this.level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY) && !this.isSpectator()) {
			float totalXp = this.experienceProgress * AllurementUtil.getXpNeededForNextLevel(this.experienceLevel);
			for (int i = 0; i < this.experienceLevel; i++) {
				totalXp += AllurementUtil.getXpNeededForNextLevel(i);
			}

			totalXp *= AllurementConfig.COMMON.experiencePercentage.get();
			int base = Mth.floor(totalXp);
			float bonus = Mth.frac(totalXp);
			if (bonus != 0.0F && Math.random() < bonus) {
				++base;
			}

			cir.setReturnValue(base);
		}
	}
}
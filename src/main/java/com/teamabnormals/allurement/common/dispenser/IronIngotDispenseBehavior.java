package com.teamabnormals.allurement.common.dispenser;

import com.teamabnormals.allurement.core.AllurementConfig;
import com.teamabnormals.blueprint.core.util.BlockUtil;
import com.teamabnormals.blueprint.core.util.NetworkUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class IronIngotDispenseBehavior extends OptionalDispenseItemBehavior {

	@Override
	protected ItemStack execute(BlockSource source, ItemStack stack) {
		ServerLevel level = source.getLevel();
		BlockPos facingPos = BlockUtil.offsetPos(source);
		BlockState facingState = level.getBlockState(facingPos);

		if (facingState.is(Blocks.CHIPPED_ANVIL)) {
			repairAnvil(Blocks.ANVIL, level, facingPos);
		}

		if (facingState.is(Blocks.DAMAGED_ANVIL)) {
			repairAnvil(Blocks.CHIPPED_ANVIL, level, facingPos);
		}

		this.setSuccess(true);
		stack.shrink(1);
		return stack;
	}

	public static void playSound(SoundEvent soundEvent, Level level, BlockPos pos) {
		level.playSound(null, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);
	}

	public static void spawnParticles(SimpleParticleType particleType, RandomSource random, BlockPos pos) {
		for (int i = 0; i < 12; ++i) {
			double d2 = random.nextGaussian() * 0.02D;
			double d3 = random.nextGaussian() * 0.02D;
			double d4 = random.nextGaussian() * 0.02D;
			double d6 = (double) pos.getX() + random.nextDouble();
			double d7 = (double) pos.getY() + 1.0D + random.nextDouble() * 0.1D;
			double d8 = (double) pos.getZ() + random.nextDouble();
			NetworkUtil.spawnParticle(particleType.writeToString(), d6, d7, d8, d2, d3, d4);
		}
	}

	public static void repairAnvil(Block result, Level level, BlockPos facingPos) {
		RandomSource random = level.getRandom();
		if (!level.isClientSide()) {
			if (random.nextInt(AllurementConfig.COMMON.ingotRepairChance.get()) == 0) {
				level.setBlock(facingPos, BlockUtil.transferAllBlockStates(level.getBlockState(facingPos), result.defaultBlockState()), 3);
				spawnParticles(ParticleTypes.FIREWORK, random, facingPos);
			} else {
				spawnParticles(ParticleTypes.SMOKE, random, facingPos);
			}
		}
		playSound(SoundEvents.ANVIL_PLACE, level, facingPos);
	}

	public static boolean canBeRepaired(BlockState state) {
		return state.is(Blocks.CHIPPED_ANVIL) || state.is(Blocks.DAMAGED_ANVIL);
	}
}
package com.teamabnormals.allurement.core.mixin;

import com.mojang.datafixers.TypeRewriteRule.All;
import com.teamabnormals.allurement.core.AllurementConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChiseledBookShelfBlock;
import net.minecraft.world.level.block.entity.ChiseledBookShelfBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.spongepowered.asm.mixin.Mixin;

import java.util.stream.Stream;

@Mixin(ChiseledBookShelfBlock.class)
public abstract class ChiseledBookShelfBlockMixin extends Block {

	public ChiseledBookShelfBlockMixin(Properties properties) {
		super(properties);
	}

	@Override
	public float getEnchantPowerBonus(BlockState state, LevelReader level, BlockPos pos) {
		if (AllurementConfig.COMMON.chiseledBookshelfEnchanting.get()) {
			if (!level.isClientSide()) {
				if (level.getBlockEntity(pos) instanceof ChiseledBookShelfBlockEntity blockEntity) {
					int books = 0;
					int enchantedBooks = 0;
					for (int i = 0; i < blockEntity.getContainerSize(); i++) {
						ItemStack stack = blockEntity.getItem(i);
						if (stack.is(Items.ENCHANTED_BOOK)) {
							enchantedBooks++;
						} else if (stack.is(ItemTags.BOOKSHELF_BOOKS)) {
							books++;
						}
					}

					return ((float) books) / AllurementConfig.COMMON.booksNeededPerLevel.get() + ((float) enchantedBooks) / AllurementConfig.COMMON.enchantedBooksNeededPerLevel.get();
				}
			} else {
				int count = 0;
				for (BooleanProperty property : ChiseledBookShelfBlock.SLOT_OCCUPIED_PROPERTIES) {
					if (state.getValue(property)) {
						count++;
					}
				}
				if (count > 0) {
					return count / 3.0F;
				}
			}
		}
		return super.getEnchantPowerBonus(state, level, pos);
	}
}
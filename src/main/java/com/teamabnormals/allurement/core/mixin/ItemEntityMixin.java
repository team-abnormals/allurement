package com.teamabnormals.allurement.core.mixin;

import com.teamabnormals.allurement.core.other.AllurementUtil;
import com.teamabnormals.allurement.core.registry.AllurementEnchantmentEffects;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/item/ItemEntity;applyGravity()V", shift = At.Shift.BY, by = 2), method = "tick")
	private void getItemEnchantability(CallbackInfo ci) {
		ItemEntity item = (ItemEntity) (Object) this;
		if (EnchantmentHelper.has(item.getItem(), AllurementEnchantmentEffects.ITEM_ASCEND.get())) {
			AllurementUtil.ascendItem(item);
		}
		if (EnchantmentHelper.has(item.getItem(), AllurementEnchantmentEffects.ITEM_FLEE.get())) {
			AllurementUtil.repelItem(item);
		}
	}
}
package com.teamabnormals.allurement.core.mixin;

import com.teamabnormals.allurement.core.other.AllurementUtil;
import com.teamabnormals.allurement.core.registry.AllurementEnchantments;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;add(DDD)Lnet/minecraft/world/phys/Vec3;", shift = At.Shift.BY, by = 2), method = "tick")
	private void getItemEnchantability(CallbackInfo ci) {
		ItemEntity item = (ItemEntity) (Object) this;
		if (EnchantmentHelper.getTagEnchantmentLevel(AllurementEnchantments.ASCENSION_CURSE.get(), item.getItem()) > 0) {
			AllurementUtil.ascendItem(item);
		}
		if (EnchantmentHelper.getTagEnchantmentLevel(AllurementEnchantments.FLEETING_CURSE.get(), item.getItem()) > 0) {
			AllurementUtil.repelItem(item);
		}
	}
}
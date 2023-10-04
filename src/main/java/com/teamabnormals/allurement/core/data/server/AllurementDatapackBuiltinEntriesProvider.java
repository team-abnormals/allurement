package com.teamabnormals.allurement.core.data.server;

import com.teamabnormals.allurement.core.Allurement;
import com.teamabnormals.allurement.core.other.AllurementDamageTypes;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class AllurementDatapackBuiltinEntriesProvider extends DatapackBuiltinEntriesProvider {

	public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
			.add(Registries.DAMAGE_TYPE, AllurementDamageTypes::bootstrap);

	public AllurementDatapackBuiltinEntriesProvider(PackOutput output, CompletableFuture<Provider> provider) {
		super(output, provider, BUILDER, Set.of(Allurement.MOD_ID));
	}
}

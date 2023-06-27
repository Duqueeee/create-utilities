package me.duquee.createutilities.blocks.voidtypes;

import me.duquee.createutilities.blocks.voidtypes.motor.VoidMotorNetworkHandler.NetworkKey;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class VoidStorageData<T extends Storage<?>> extends SavedData {

	protected final Map<NetworkKey, T> storages = new HashMap<>();

	public T computeStorageIfAbsent(NetworkKey key, Function<NetworkKey, T> function) {
		return storages.computeIfAbsent(key, function);
	}

	public @NotNull CompoundTag save(@NotNull CompoundTag tag,
									 Function<T, Boolean> isEmpty,
									 Function<T, CompoundTag> serializeNBT) {
		storages.forEach( (key, inventory) -> {
			if (!isEmpty.apply(inventory))
				tag.put(key.toString(), serializeNBT.apply(inventory));
		} );
		return tag;
	}

	public static <T extends Storage<?>, S extends VoidStorageData<T>> S load(CompoundTag tag,
																			  Supplier<S> storageDataSupplier,
																			  Function<NetworkKey, T> storageSupplier,
																			  BiConsumer<T, CompoundTag> deserializeNBT) {
		S data = storageDataSupplier.get();
		tag.getAllKeys().forEach(k -> {
			NetworkKey key = NetworkKey.fromString(k);
			T inventory = storageSupplier.apply(key);
			deserializeNBT.accept(inventory, tag.getCompound(k));
			data.storages.put(key, inventory);
		});
		return data;
	}

}


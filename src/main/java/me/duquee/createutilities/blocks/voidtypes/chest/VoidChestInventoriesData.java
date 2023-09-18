package me.duquee.createutilities.blocks.voidtypes.chest;

import me.duquee.createutilities.blocks.voidtypes.motor.VoidMotorNetworkHandler.NetworkKey;
import net.minecraft.nbt.CompoundTag;

import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class VoidChestInventoriesData extends SavedData {

	protected final Map<NetworkKey, VoidChestInventory> storages = new HashMap<>();

	public VoidChestInventory computeStorageIfAbsent(NetworkKey key) {
		return storages.computeIfAbsent(key, k -> new VoidChestInventory());
	}

	@Override
	public @NotNull CompoundTag save(@NotNull CompoundTag tag) {
		storages.forEach( (key, inventory) -> {
			if (!inventory.isEmpty())
				tag.put(key.toString(), inventory.serializeNBT());
		} );
		return tag;
	}

	public static VoidChestInventoriesData load(CompoundTag tag) {
		VoidChestInventoriesData data = new VoidChestInventoriesData();
		tag.getAllKeys().forEach(k -> {
			NetworkKey key = NetworkKey.fromString(k);
			VoidChestInventory inventory = new VoidChestInventory();
			inventory.deserializeNBT(tag.getCompound(k));
			data.storages.put(key, inventory);
		});
		return data;
	}

}

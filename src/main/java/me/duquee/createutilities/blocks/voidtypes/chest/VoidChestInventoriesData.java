package me.duquee.createutilities.blocks.voidtypes.chest;

import me.duquee.createutilities.blocks.voidtypes.VoidStorageData;
import me.duquee.createutilities.blocks.voidtypes.motor.VoidMotorNetworkHandler.NetworkKey;
import net.minecraft.nbt.CompoundTag;

import org.jetbrains.annotations.NotNull;

public class VoidChestInventoriesData extends VoidStorageData<VoidChestInventory> {

	public VoidChestInventory computeStorageIfAbsent(NetworkKey key) {
		return super.computeStorageIfAbsent(key, VoidChestInventory::new);
	}

	@Override
	public @NotNull CompoundTag save(@NotNull CompoundTag tag) {
		return super.save(tag, VoidChestInventory::isEmpty, VoidChestInventory::serializeNBT);
	}

	public static VoidChestInventoriesData load(CompoundTag tag) {
		return load(tag, VoidChestInventoriesData::new, VoidChestInventory::new, VoidChestInventory::deserializeNBT);
	}

}

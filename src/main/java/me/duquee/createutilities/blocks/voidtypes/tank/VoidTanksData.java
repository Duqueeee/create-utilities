package me.duquee.createutilities.blocks.voidtypes.tank;

import me.duquee.createutilities.blocks.voidtypes.motor.VoidMotorNetworkHandler.NetworkKey;
import net.minecraft.nbt.CompoundTag;

import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class VoidTanksData extends SavedData {

	protected final Map<NetworkKey, VoidTank> storages = new HashMap<>();

	public VoidTank computeStorageIfAbsent(NetworkKey key) {
		return storages.computeIfAbsent(key, VoidTank::new);
	}

	@Override
	public @NotNull CompoundTag save(@NotNull CompoundTag tag) {
		storages.forEach( (key, tank) -> {
			if (!tank.isEmpty())
				tag.put(key.toString(), tank.writeToNBT(new CompoundTag()));
		} );
		return tag;
	}

	public static VoidTanksData load(CompoundTag tag) {
		VoidTanksData data = new VoidTanksData();
		tag.getAllKeys().forEach(k -> {
			NetworkKey key = NetworkKey.fromString(k);
			VoidTank tank = new VoidTank(key);
			tank.readFromNBT(tag.getCompound(k));
			data.storages.put(key, tank);
		});
		return data;
	}

}

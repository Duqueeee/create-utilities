package me.duquee.createutilities.blocks.voidtypes.tank;

import me.duquee.createutilities.blocks.voidtypes.VoidStorageData;
import me.duquee.createutilities.blocks.voidtypes.motor.VoidMotorNetworkHandler.NetworkKey;
import net.minecraft.nbt.CompoundTag;

import org.jetbrains.annotations.NotNull;

public class VoidTanksData extends VoidStorageData<VoidTank> {

	public VoidTank computeStorageIfAbsent(NetworkKey key) {
		return super.computeStorageIfAbsent(key, VoidTank::new);
	}

	@Override
	public @NotNull CompoundTag save(@NotNull CompoundTag tag) {
		return super.save(tag, VoidTank::isEmpty, tank -> tank.writeToNBT(new CompoundTag()));
	}

	public static VoidTanksData load(CompoundTag tag) {
		return load(tag, VoidTanksData::new, VoidTank::new, VoidTank::readFromNBT);
	}

}

package me.duquee.createutilities.blocks.voidtypes.tank;

import io.github.fabricators_of_create.porting_lib.transfer.fluid.FluidTank;
import me.duquee.createutilities.blocks.voidtypes.VoidStorageData;
import me.duquee.createutilities.blocks.voidtypes.motor.VoidMotorNetworkHandler.NetworkKey;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.CompoundTag;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class VoidTanksData extends VoidStorageData<VoidTank> {

	@Environment(EnvType.CLIENT)
	public final Map<NetworkKey, FluidTank> clientTanks = new HashMap<>();

	@Environment(EnvType.CLIENT)
	public FluidTank computeClientTankIfAbsent(NetworkKey key) {
		return clientTanks.computeIfAbsent(key, k -> new FluidTank(VoidTank.CAPACITY));
	}

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

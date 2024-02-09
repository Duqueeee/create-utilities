package me.duquee.createutilities.blocks.voidtypes.battery;

import me.duquee.createutilities.CreateUtilities;
import me.duquee.createutilities.blocks.voidtypes.motor.VoidMotorNetworkHandler.NetworkKey;
import me.duquee.createutilities.networking.CUPackets;
import me.duquee.createutilities.networking.packets.VoidBatteryUpdatePacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.network.PacketDistributor;

public class VoidBattery extends EnergyStorage {

	private final NetworkKey key;

	public VoidBattery(NetworkKey key) {
		super(32000, 4096, 4096);
		this.key = key;
	}

	public boolean isEmpty() {
		return energy == 0;
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		int inserted = super.receiveEnergy(maxReceive, simulate);
		if (inserted != 0) onContentsChanged();
		return inserted;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		int extracted = super.extractEnergy(maxExtract, simulate);
		if (extracted != 0) onContentsChanged();
		return extracted;
	}

	private void onContentsChanged() {
		if (CreateUtilities.VOID_BATTERIES_DATA != null) CreateUtilities.VOID_BATTERIES_DATA.setDirty();
		CUPackets.channel.send(PacketDistributor.ALL.noArg(), new VoidBatteryUpdatePacket(key, this));
	}

	public CompoundTag serializeNBT() {
		CompoundTag nbt = new CompoundTag();
		nbt.putLong("Energy", energy);
		return nbt;
	}

	public void deserializeNBT(CompoundTag nbt) {
		energy = nbt.getInt("Energy");
	}

}

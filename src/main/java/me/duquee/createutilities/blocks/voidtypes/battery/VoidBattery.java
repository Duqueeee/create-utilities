package me.duquee.createutilities.blocks.voidtypes.battery;

import me.duquee.createutilities.CreateUtilities;
import me.duquee.createutilities.blocks.voidtypes.motor.VoidMotorNetworkHandler.NetworkKey;
import me.duquee.createutilities.networking.CUPackets;
import me.duquee.createutilities.networking.packets.VoidBatteryUpdatePacket;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.nbt.CompoundTag;
import team.reborn.energy.api.base.SimpleEnergyStorage;

public class VoidBattery extends SimpleEnergyStorage {

	private final NetworkKey key;

	public VoidBattery(NetworkKey key) {
		super(32000, 32, 32);
		this.key = key;
	}

	public boolean isEmpty() {
		return amount == 0;
	}

	public CompoundTag serializeNBT() {
		CompoundTag nbt = new CompoundTag();
		nbt.putLong("Energy", amount);
		return nbt;
	}

	public void deserializeNBT(CompoundTag nbt) {
		amount = nbt.getLong("Energy");
	}

	@Override
	protected void onFinalCommit() {
		if (CreateUtilities.VOID_BATTERIES_DATA != null) CreateUtilities.VOID_BATTERIES_DATA.setDirty();
	}

	@Override
	public long insert(long maxAmount, TransactionContext transaction) {
		long inserted = super.insert(maxAmount, transaction);
		if (inserted != 0) onContentsChanged();
		return inserted;
	}

	@Override
	public long extract(long maxAmount, TransactionContext transaction) {
		long extracted = super.extract(maxAmount, transaction);
		if (extracted != 0) onContentsChanged();
		return extracted;
	}

	private void onContentsChanged() {
		CUPackets.channel.sendToClientsInCurrentServer(new VoidBatteryUpdatePacket(key, this));
	}

}

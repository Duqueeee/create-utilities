package me.duquee.createutilities.blocks.voidtypes.battery;

import org.jetbrains.annotations.NotNull;

import me.duquee.createutilities.blocks.voidtypes.VoidStorageData;
import me.duquee.createutilities.blocks.voidtypes.motor.VoidMotorNetworkHandler.NetworkKey;
import net.minecraft.nbt.CompoundTag;

public class VoidBatteryData extends VoidStorageData<VoidBattery> {

	public VoidBattery computeStorageIfAbsent(NetworkKey key) {
		return super.computeStorageIfAbsent(key, VoidBattery::new);
	}

	@Override
	public @NotNull CompoundTag save(@NotNull CompoundTag tag) {
		return super.save(tag, VoidBattery::isEmpty, VoidBattery::serializeNBT);
	}

	public static VoidBatteryData load(CompoundTag tag) {
		return load(tag, VoidBatteryData::new, VoidBattery::new, VoidBattery::deserializeNBT);
	}

}

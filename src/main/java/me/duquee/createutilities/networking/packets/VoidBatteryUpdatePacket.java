package me.duquee.createutilities.networking.packets;

import com.simibubi.create.foundation.networking.SimplePacketBase;
import com.simibubi.create.foundation.utility.animation.LerpedFloat;
import com.tterrag.registrate.fabric.EnvExecutor;

import me.duquee.createutilities.CreateUtilitiesClient;
import me.duquee.createutilities.blocks.voidtypes.battery.VoidBattery;
import me.duquee.createutilities.blocks.voidtypes.motor.VoidMotorNetworkHandler.NetworkKey;
import net.fabricmc.api.EnvType;
import net.minecraft.network.FriendlyByteBuf;
import team.reborn.energy.api.base.SimpleEnergyStorage;

public class VoidBatteryUpdatePacket extends SimplePacketBase {

	private final NetworkKey key;
	private final VoidBattery battery;

	public VoidBatteryUpdatePacket(NetworkKey key, VoidBattery battery) {
		this.key = key;
		this.battery = battery;
	}

	public VoidBatteryUpdatePacket(FriendlyByteBuf buffer) {
		key = NetworkKey.fromBuffer(buffer);
		battery = new VoidBattery(key);
		battery.deserializeNBT(buffer.readNbt());
	}

	@Override
	public void write(FriendlyByteBuf buffer) {
		key.writeToBuffer(buffer);
		buffer.writeNbt(battery.serializeNBT());
	}

	@Override
	public boolean handle(Context context) {
		context.enqueueWork(() -> EnvExecutor.runWhenOn(EnvType.CLIENT, () -> () ->
			CreateUtilitiesClient.VOID_BATTERIES.storages.put(key, battery)
		));
		return true;
	}

}

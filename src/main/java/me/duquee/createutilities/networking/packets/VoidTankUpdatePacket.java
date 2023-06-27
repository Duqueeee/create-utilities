package me.duquee.createutilities.networking.packets;

import com.simibubi.create.foundation.networking.SimplePacketBase;

import com.tterrag.registrate.fabric.EnvExecutor;

import io.github.fabricators_of_create.porting_lib.transfer.fluid.FluidTank;
import me.duquee.createutilities.CreateUtilities;
import me.duquee.createutilities.blocks.voidtypes.motor.VoidMotorNetworkHandler.NetworkKey;
import me.duquee.createutilities.blocks.voidtypes.tank.VoidTank;
import net.fabricmc.api.EnvType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;

public class VoidTankUpdatePacket extends SimplePacketBase {

	private final NetworkKey key;
	private final FluidTank tank;

	public VoidTankUpdatePacket(NetworkKey key, VoidTank tank) {
		this.key = key;
		this.tank = tank;
	}

	public VoidTankUpdatePacket(FriendlyByteBuf buffer) {
		key = NetworkKey.fromBuffer(buffer);
		tank = new FluidTank(VoidTank.CAPACITY).readFromNBT(buffer.readNbt());
	}

	@Override
	public void write(FriendlyByteBuf buffer) {
		key.writeToBuffer(buffer);
		buffer.writeNbt(tank.writeToNBT(new CompoundTag()));
	}

	@Override
	public boolean handle(Context context) {
		context.enqueueWork(() -> EnvExecutor.runWhenOn(EnvType.CLIENT, () -> () ->
			CreateUtilities.VOID_TANKS_DATA.clientTanks.put(key, tank)
		));
		return true;
	}

}

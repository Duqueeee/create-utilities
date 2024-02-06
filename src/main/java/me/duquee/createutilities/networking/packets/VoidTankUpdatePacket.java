package me.duquee.createutilities.networking.packets;

import com.simibubi.create.foundation.networking.SimplePacketBase;

import me.duquee.createutilities.CreateUtilitiesClient;
import me.duquee.createutilities.blocks.voidtypes.motor.VoidMotorNetworkHandler.NetworkKey;
import me.duquee.createutilities.blocks.voidtypes.tank.VoidTank;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

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
	public boolean handle(NetworkEvent.Context context) {
		context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
				CreateUtilitiesClient.VOID_TANKS.storages.put(key, tank)));
		return true;
	}

}

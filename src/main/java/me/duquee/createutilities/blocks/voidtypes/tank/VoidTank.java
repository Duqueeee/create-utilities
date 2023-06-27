package me.duquee.createutilities.blocks.voidtypes.tank;

import com.simibubi.create.infrastructure.config.AllConfigs;

import io.github.fabricators_of_create.porting_lib.transfer.fluid.FluidTank;
import me.duquee.createutilities.CreateUtilities;
import me.duquee.createutilities.blocks.voidtypes.motor.VoidMotorNetworkHandler.NetworkKey;
import me.duquee.createutilities.networking.CUPackets;
import me.duquee.createutilities.networking.packets.VoidTankUpdatePacket;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;

public class VoidTank extends FluidTank {

	public static final long CAPACITY = AllConfigs.server().fluids.fluidTankCapacity.get() * FluidConstants.BUCKET;

	private final NetworkKey key;

	public VoidTank(NetworkKey key) {
		super(CAPACITY);
		this.key = key;
	}

	@Override
	protected void onContentsChanged() {
		if (CreateUtilities.VOID_TANKS_DATA != null) CreateUtilities.VOID_TANKS_DATA.setDirty();
		CUPackets.channel.sendToClientsInCurrentServer(new VoidTankUpdatePacket(key, this));
	}

	public boolean isEmpty() {
		return stack.isEmpty();
	}

}

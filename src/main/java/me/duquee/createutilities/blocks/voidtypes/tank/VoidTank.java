package me.duquee.createutilities.blocks.voidtypes.tank;

import com.simibubi.create.infrastructure.config.AllConfigs;
import me.duquee.createutilities.CreateUtilities;
import me.duquee.createutilities.blocks.voidtypes.motor.VoidMotorNetworkHandler.NetworkKey;
import me.duquee.createutilities.networking.CUPackets;
import me.duquee.createutilities.networking.packets.VoidTankUpdatePacket;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.network.PacketDistributor;

public class VoidTank extends FluidTank {

	public static final int CAPACITY = AllConfigs.server().fluids.fluidTankCapacity.get() * 1000;

	private final NetworkKey key;

	public VoidTank(NetworkKey key) {
		super(CAPACITY);
		this.key = key;
	}

	@Override
	protected void onContentsChanged() {
		if (CreateUtilities.VOID_TANKS_DATA != null) CreateUtilities.VOID_TANKS_DATA.setDirty();
		CUPackets.channel.send(PacketDistributor.ALL.noArg(), new VoidTankUpdatePacket(key, this));
	}

}

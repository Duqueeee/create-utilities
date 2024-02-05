package me.duquee.createutilities;

import me.duquee.createutilities.blocks.voidtypes.tank.VoidTank;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class CreateUtilitiesClient {

	public static final VoidStorageClient<FluidTank> VOID_TANKS = new VoidStorageClient<>(
			k -> new FluidTank(VoidTank.CAPACITY));

	public static final VoidStorageClient<VoidBattery> VOID_BATTERIES = new VoidStorageClient<>(
			VoidBattery::new);

}

package me.duquee.createutilities;

import me.duquee.createutilities.blocks.CUPartialsModels;
import me.duquee.createutilities.blocks.voidtypes.VoidStorageClient;
import me.duquee.createutilities.blocks.voidtypes.battery.VoidBattery;
import me.duquee.createutilities.blocks.voidtypes.tank.VoidTank;
import me.duquee.createutilities.ponder.CUPonderIndex;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class CreateUtilitiesClient {

	public static final VoidStorageClient<FluidTank> VOID_TANKS = new VoidStorageClient<>(
			k -> new FluidTank(VoidTank.CAPACITY));

	public static final VoidStorageClient<VoidBattery> VOID_BATTERIES = new VoidStorageClient<>(
			VoidBattery::new);

	public static void onCtorClient(IEventBus modEventBus, IEventBus forgeEventBus) {
		CUPartialsModels.init();
		modEventBus.addListener(CreateUtilitiesClient::clientInit);
		CUPartialsModels.init();
	}

	public static void clientInit(final FMLClientSetupEvent event) {
		CUPonderIndex.register();
	}

}

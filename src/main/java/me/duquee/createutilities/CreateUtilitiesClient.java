package me.duquee.createutilities;

import me.duquee.createutilities.blocks.CUPartialsModels;
import me.duquee.createutilities.blocks.voidtypes.tank.VoidTanksClient;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class CreateUtilitiesClient {

	public static final VoidTanksClient VOID_TANKS = new VoidTanksClient();

	public static void onCtorClient(IEventBus modEventBus, IEventBus forgeEventBus) {
		modEventBus.addListener(CreateUtilitiesClient::clientInit);
	}

	public static void clientInit(final FMLClientSetupEvent event) {
		CUPartialsModels.init();
	}

}

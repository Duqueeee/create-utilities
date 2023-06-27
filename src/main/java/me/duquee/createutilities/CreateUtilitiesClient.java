package me.duquee.createutilities;

import me.duquee.createutilities.blocks.CUPartialsModels;
import me.duquee.createutilities.events.ClientEvents;
import me.duquee.createutilities.networking.CUPackets;
import net.fabricmc.api.ClientModInitializer;

public class CreateUtilitiesClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		CUPartialsModels.init();
		ClientEvents.register();
		CUPackets.channel.initClientListener();
	}

}

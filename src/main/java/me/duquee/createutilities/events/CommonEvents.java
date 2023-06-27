package me.duquee.createutilities.events;

import me.duquee.createutilities.CreateUtilities;
import me.duquee.createutilities.blocks.voidtypes.chest.VoidChestInventoriesData;
import me.duquee.createutilities.blocks.voidtypes.tank.VoidTanksData;
import me.duquee.createutilities.voidlink.VoidLinkHandler;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;

public class CommonEvents {

	public static void onLoad(MinecraftServer server, ServerLevel level) {

		CreateUtilities.VOID_MOTOR_LINK_NETWORK_HANDLER.onLoadWorld(level);

		CreateUtilities.VOID_CHEST_INVENTORIES_DATA = server.overworld().getDataStorage()
				.computeIfAbsent(VoidChestInventoriesData::load, VoidChestInventoriesData::new, "VoidChestInventories");

		CreateUtilities.VOID_TANKS_DATA = server.overworld().getDataStorage()
				.computeIfAbsent(VoidTanksData::load, VoidTanksData::new, "VoidTanks");

	}

	public static void register() {
		UseBlockCallback.EVENT.register(VoidLinkHandler::onBlockActivated);
		ServerWorldEvents.LOAD.register(CommonEvents::onLoad);
		ServerWorldEvents.UNLOAD.register((e, w) -> CreateUtilities.VOID_MOTOR_LINK_NETWORK_HANDLER.onUnloadWorld(w));
	}

}

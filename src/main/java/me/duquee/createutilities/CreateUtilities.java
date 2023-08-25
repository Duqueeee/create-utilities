package me.duquee.createutilities;

import com.simibubi.create.foundation.data.CreateRegistrate;

import me.duquee.createutilities.blocks.CUBlocks;
import me.duquee.createutilities.blocks.CUTileEntities;
import me.duquee.createutilities.blocks.voidtypes.CUContainerTypes;
import me.duquee.createutilities.blocks.voidtypes.chest.VoidChestInventoriesData;
import me.duquee.createutilities.blocks.voidtypes.tank.VoidTanksData;
import me.duquee.createutilities.events.CommonEvents;
import me.duquee.createutilities.items.CUItems;
import me.duquee.createutilities.blocks.voidtypes.motor.VoidMotorNetworkHandler;
import me.duquee.createutilities.networking.CUPackets;
import me.duquee.createutilities.tabs.CUCreativeTabs;
import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.ResourceLocation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateUtilities implements ModInitializer {

	public static final String ID = "createutilities";
	public static final String NAME = "Create Utilities";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

	public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(ID);

	public static final VoidMotorNetworkHandler VOID_MOTOR_LINK_NETWORK_HANDLER = new VoidMotorNetworkHandler();
	public static VoidChestInventoriesData VOID_CHEST_INVENTORIES_DATA;
	public static VoidTanksData VOID_TANKS_DATA;

	@Override
	public void onInitialize() {

		CUBlocks.register();
		CUItems.register();
		CUTileEntities.register();
		CUContainerTypes.register();

		REGISTRATE.register();

		CUCreativeTabs.register();
		CommonEvents.register();
		CUPackets.registerPackets();
		CUPackets.channel.initServerListener();

	}

	public static ResourceLocation asResource(String path) {
		return new ResourceLocation(ID, path);
	}
}

package me.duquee.createutilities.events;

import me.duquee.createutilities.CreateUtilities;
import me.duquee.createutilities.blocks.voidtypes.battery.VoidBatteryData;
import me.duquee.createutilities.blocks.voidtypes.chest.VoidChestInventoriesData;
import me.duquee.createutilities.blocks.voidtypes.tank.VoidTanksData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CommonEvents {

	@SubscribeEvent
	public static void onLoad(WorldEvent.Load event) {

		MinecraftServer server = event.getWorld().getServer();
		if (server == null) return;

		LevelAccessor level = event.getWorld();
		DimensionDataStorage dataStorage = server.overworld().getDataStorage();

		CreateUtilities.VOID_MOTOR_LINK_NETWORK_HANDLER.onLoadWorld(level);

		CreateUtilities.VOID_CHEST_INVENTORIES_DATA = dataStorage
				.computeIfAbsent(VoidChestInventoriesData::load, VoidChestInventoriesData::new, "VoidChestInventories");

		CreateUtilities.VOID_TANKS_DATA = dataStorage
				.computeIfAbsent(VoidTanksData::load, VoidTanksData::new, "VoidTanks");

		CreateUtilities.VOID_BATTERIES_DATA = dataStorage
				.computeIfAbsent(VoidBatteryData::load, VoidBatteryData::new, "VoidBatteries");

	}

	@SubscribeEvent
	public static void onUnload(WorldEvent.Unload event) {
		CreateUtilities.VOID_MOTOR_LINK_NETWORK_HANDLER.onUnloadWorld(event.getWorld());
	}

}

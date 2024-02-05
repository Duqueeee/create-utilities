package me.duquee.createutilities;

import com.simibubi.create.foundation.utility.animation.LerpedFloat;

import io.github.fabricators_of_create.porting_lib.transfer.fluid.FluidTank;
import me.duquee.createutilities.blocks.CUPartialsModels;
import me.duquee.createutilities.blocks.voidtypes.VoidStorageClient;
import me.duquee.createutilities.blocks.voidtypes.battery.VoidBattery;
import me.duquee.createutilities.blocks.voidtypes.tank.VoidTank;
import me.duquee.createutilities.events.ClientEvents;
import me.duquee.createutilities.networking.CUPackets;
import me.duquee.createutilities.ponder.CUPonderIndex;
import net.fabricmc.api.ClientModInitializer;
import team.reborn.energy.api.base.SimpleEnergyStorage;

public class CreateUtilitiesClient implements ClientModInitializer {

	public static final VoidStorageClient<FluidTank> VOID_TANKS = new VoidStorageClient<>(
			k -> new FluidTank(VoidTank.CAPACITY));

	public static final VoidStorageClient<VoidBattery> VOID_BATTERIES = new VoidStorageClient<>(
			VoidBattery::new);

	@Override
	public void onInitializeClient() {
		CUPartialsModels.init();
		CUPonderIndex.register();
		ClientEvents.register();
		CUPackets.channel.initClientListener();
	}

}

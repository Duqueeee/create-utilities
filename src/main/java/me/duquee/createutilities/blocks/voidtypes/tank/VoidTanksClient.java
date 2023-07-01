package me.duquee.createutilities.blocks.voidtypes.tank;


import io.github.fabricators_of_create.porting_lib.transfer.fluid.FluidTank;
import me.duquee.createutilities.blocks.voidtypes.motor.VoidMotorNetworkHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class VoidTanksClient {

	public final Map<VoidMotorNetworkHandler.NetworkKey, FluidTank> clientTanks = new HashMap<>();

	public FluidTank computeClientTankIfAbsent(VoidMotorNetworkHandler.NetworkKey key) {
		return clientTanks.computeIfAbsent(key, k -> new FluidTank(VoidTank.CAPACITY));
	}

}

package me.duquee.createutilities.blocks.voidtypes.tank;

import me.duquee.createutilities.blocks.voidtypes.motor.VoidMotorNetworkHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import java.util.HashMap;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class VoidTanksClient {

	public final Map<VoidMotorNetworkHandler.NetworkKey, FluidTank> clientTanks = new HashMap<>();

	public FluidTank computeClientTankIfAbsent(VoidMotorNetworkHandler.NetworkKey key) {
		return clientTanks.computeIfAbsent(key, k -> new FluidTank(VoidTank.CAPACITY));
	}

}

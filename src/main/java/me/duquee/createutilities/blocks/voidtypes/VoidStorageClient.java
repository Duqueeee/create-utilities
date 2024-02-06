package me.duquee.createutilities.blocks.voidtypes;


import me.duquee.createutilities.blocks.voidtypes.motor.VoidMotorNetworkHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class VoidStorageClient<T> {

	public final Map<VoidMotorNetworkHandler.NetworkKey, T> storages = new HashMap<>();
	private final Function<VoidMotorNetworkHandler.NetworkKey, T> factory;

	public VoidStorageClient(Function<VoidMotorNetworkHandler.NetworkKey, T> factory) {
		this.factory = factory;
	}

	public final T computeStorageIfAbsent(VoidMotorNetworkHandler.NetworkKey key) {
		return storages.computeIfAbsent(key, factory);
	}

}

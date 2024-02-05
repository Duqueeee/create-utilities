package me.duquee.createutilities.blocks.voidtypes;


import me.duquee.createutilities.blocks.voidtypes.motor.VoidMotorNetworkHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Environment(EnvType.CLIENT)
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

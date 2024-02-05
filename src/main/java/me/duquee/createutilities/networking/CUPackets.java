package me.duquee.createutilities.networking;

import com.simibubi.create.foundation.networking.SimplePacketBase;

import me.duquee.createutilities.CreateUtilities;
import me.duquee.createutilities.networking.packets.VoidBatteryUpdatePacket;
import me.duquee.createutilities.networking.packets.VoidTankUpdatePacket;
import me.pepperbell.simplenetworking.S2CPacket;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

import java.util.function.BiConsumer;
import java.util.function.Function;

import static com.simibubi.create.foundation.networking.SimplePacketBase.NetworkDirection.PLAY_TO_CLIENT;
import static com.simibubi.create.foundation.networking.SimplePacketBase.NetworkDirection.PLAY_TO_SERVER;

public enum CUPackets {

	VOID_TANK_UPDATE(VoidTankUpdatePacket.class, VoidTankUpdatePacket::new, PLAY_TO_CLIENT),
	VOID_BATTERY_UPDATE(VoidBatteryUpdatePacket.class, VoidBatteryUpdatePacket::new, PLAY_TO_CLIENT);

	public static final ResourceLocation CHANNEL_NAME = CreateUtilities.asResource("main");
	public static SimpleChannel channel;

	private final LoadedPacket<?> packet;

	<T extends SimplePacketBase> CUPackets(Class<T> type, Function<FriendlyByteBuf, T> factory,
											SimplePacketBase.NetworkDirection direction) {
		packet = new LoadedPacket<>(type, factory, direction);
	}

	public static void registerPackets() {
		channel = new SimpleChannel(CHANNEL_NAME);
		int id = 0;
		for (CUPackets packet : values()) {
			boolean registered = false;
			if (packet.packet.direction == PLAY_TO_SERVER) {
				channel.registerC2SPacket(packet.packet.type, id++);
				registered = true;
			}
			if (packet.packet.direction == PLAY_TO_CLIENT) {
				channel.registerS2CPacket(packet.packet.type, id++);
				registered = true;
			}
			if (!registered) {
				CreateUtilities.LOGGER.error("Could not register packet with type " + packet.packet.type);
			}
		}
	}

	public static void sendToNear(Level world, BlockPos pos, int range, Object message) {
		channel.sendToClientsAround((S2CPacket) message, (ServerLevel) world, pos, range);
	}

	private static class LoadedPacket<T extends SimplePacketBase> {

		private BiConsumer<T, FriendlyByteBuf> encoder;
		private Function<FriendlyByteBuf, T> decoder;
		private BiConsumer<T, SimplePacketBase.Context> handler;
		private Class<T> type;
		private SimplePacketBase.NetworkDirection direction;

		private LoadedPacket(Class<T> type, Function<FriendlyByteBuf, T> factory, SimplePacketBase.NetworkDirection direction) {
			encoder = T::write;
			decoder = factory;
			handler = T::handle;
			this.type = type;
			this.direction = direction;
		}

	}

}

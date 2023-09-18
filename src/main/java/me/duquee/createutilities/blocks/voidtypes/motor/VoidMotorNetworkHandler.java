package me.duquee.createutilities.blocks.voidtypes.motor;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.simibubi.create.Create;
import com.simibubi.create.content.redstone.link.RedstoneLinkNetworkHandler.Frequency;
import com.simibubi.create.foundation.utility.Couple;

import com.simibubi.create.foundation.utility.WorldHelper;

import me.duquee.createutilities.CreateUtilities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;

import javax.annotation.Nullable;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class VoidMotorNetworkHandler {

	static final Map<LevelAccessor, Map<NetworkKey, Set<BlockPos>>> connections =
			new IdentityHashMap<>();

	public Set<BlockPos> getNetworkOf(LevelAccessor world, VoidMotorLinkBehaviour actor) {
		Map<NetworkKey, Set<BlockPos>> networksInWorld = networksIn(world);
		NetworkKey key = actor.getNetworkKey();
		if (!networksInWorld.containsKey(key))
			networksInWorld.put(key, new LinkedHashSet<>());
		return networksInWorld.get(key);
	}

	public Map<NetworkKey, Set<BlockPos>> networksIn(LevelAccessor world) {
		if (!connections.containsKey(world)) {
			Create.LOGGER.warn("Tried to Access unprepared network space of " + WorldHelper.getDimensionID(world));
			return new HashMap<>();
		}
		return connections.get(world);
	}

	public void onLoadWorld(LevelAccessor world) {
		connections.put(world, new HashMap<>());
		Create.LOGGER.debug("Prepared Void Motor Network Space for " + WorldHelper.getDimensionID(world));
	}

	public void onUnloadWorld(LevelAccessor world) {
		connections.remove(world);
		Create.LOGGER.debug("Removed Void Motor Network Space for " + WorldHelper.getDimensionID(world));
	}

	public void addToNetwork(LevelAccessor world, VoidMotorLinkBehaviour actor) {
		getNetworkOf(world, actor).add(actor.getPos());
		if (actor.blockEntity instanceof VoidMotorTileEntity voidMotor) voidMotor.onConnectToVoidNetwork();
	}

	public void removeFromNetwork(LevelAccessor world, VoidMotorLinkBehaviour actor) {
		if (actor.blockEntity instanceof VoidMotorTileEntity voidMotor) voidMotor.onDisconnectFromVoidNetwork();
		Set<BlockPos> network = getNetworkOf(world, actor);
		network.remove(actor.getPos());
		if (network.isEmpty()) networksIn(world).remove(actor.getNetworkKey());
	}

	public static class NetworkKey {

		@Nullable
		public final GameProfile owner;
		public final Couple<Frequency> frequencies;

		public NetworkKey(@Nullable GameProfile owner, Frequency frequencyFirst, Frequency frequencySecond) {
			this.owner = owner;
			this.frequencies = Couple.create(frequencyFirst, frequencySecond);
		}

		public void writeToBuffer(FriendlyByteBuf buffer) {
			buffer.writeItem(frequencies.get(true).getStack());
			buffer.writeItem(frequencies.get(false).getStack());
			buffer.writeBoolean(owner != null);
			if (owner != null) buffer.writeGameProfile(owner);
		}

		public static NetworkKey fromBuffer(FriendlyByteBuf buffer) {
			ItemStack frequencyFirst = buffer.readItem();
			ItemStack frequencyLast = buffer.readItem();
			GameProfile owner = null;
			if (buffer.readBoolean()) owner = buffer.readGameProfile();
			return new NetworkKey(owner, Frequency.of(frequencyFirst), Frequency.of(frequencyLast));
		}

		@Override
		public int hashCode() {
			return Objects.hash(owner, frequencies);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null || getClass() != obj.getClass())
				return false;
			NetworkKey other = (NetworkKey) obj;
			return Objects.equals(owner, other.owner) && frequencies.equals(other.frequencies);
		}

		@Override
		public String toString() {
			CompoundTag tag = new CompoundTag();
			if (owner != null) {
				CompoundTag tag_ = new CompoundTag();
				NbtUtils.writeGameProfile(tag_, owner);
				tag.put("Owner", tag_);
			}
			tag.put("FrequencyFirst", frequencies.get(true).getStack().save(new CompoundTag()));
			tag.put("FrequencyLast", frequencies.get(false).getStack().save(new CompoundTag()));
			return tag.toString();
		}

		public static NetworkKey fromString(String json) {

			CompoundTag tag;
			try {
				tag = TagParser.parseTag(json);
			} catch (CommandSyntaxException e) {
				CreateUtilities.LOGGER.error("Tried to load invalid NetworkKey '" + json + "'");
				return null;
			}

			Frequency frequencyFirst = Frequency.of(ItemStack.of(tag.getCompound("FrequencyFirst")));
			Frequency frequencyLast = Frequency.of(ItemStack.of(tag.getCompound("FrequencyLast")));
			GameProfile owner = tag.contains("Owner", 10) ? NbtUtils.readGameProfile(tag.getCompound("Owner")) : null;

			return new NetworkKey(owner, frequencyFirst, frequencyLast);
		}

	}

}

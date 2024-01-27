package me.duquee.createutilities.blocks.voidtypes;

import com.mojang.authlib.GameProfile;
import com.simibubi.create.content.equipment.clipboard.ClipboardCloneable;
import com.simibubi.create.content.redstone.link.RedstoneLinkNetworkHandler.Frequency;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BehaviourType;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.utility.NBTHelper;
import me.duquee.createutilities.blocks.voidtypes.motor.VoidMotorNetworkHandler.NetworkKey;
import me.duquee.createutilities.voidlink.VoidLinkSlot;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import org.apache.commons.lang3.tuple.Triple;

import javax.annotation.Nullable;

import java.util.Objects;

public class VoidLinkBehaviour extends BlockEntityBehaviour implements ClipboardCloneable {

	public static final BehaviourType<VoidLinkBehaviour> TYPE = new BehaviourType<>();

	Frequency frequencyFirst = Frequency.EMPTY;
	Frequency frequencyLast = Frequency.EMPTY;
	@Nullable
	GameProfile owner;

	VoidLinkSlot firstSlot;
	VoidLinkSlot secondSlot;
	VoidLinkSlot playerSlot;

	public VoidLinkBehaviour(SmartBlockEntity te,
							 Triple<VoidLinkSlot, VoidLinkSlot, VoidLinkSlot> slots) {
		super(te);
		firstSlot = slots.getLeft();
		secondSlot = slots.getMiddle();
		this.playerSlot = slots.getRight();
	}

	@Override
	public void write(CompoundTag nbt, boolean clientPacket) {
		super.write(nbt, clientPacket);

		nbt.put("FrequencyFirst", frequencyFirst.getStack().save(new CompoundTag()));
		nbt.put("FrequencyLast", frequencyLast.getStack().save(new CompoundTag()));

		if (this.owner != null) {
			CompoundTag compoundTag = new CompoundTag();
			NbtUtils.writeGameProfile(compoundTag, this.owner);
			nbt.put("Owner", compoundTag);
		}

	}

	@Override
	public void read(CompoundTag nbt, boolean clientPacket) {
		super.read(nbt, clientPacket);

		frequencyFirst = Frequency.of(ItemStack.of(nbt.getCompound("FrequencyFirst")));
		frequencyLast = Frequency.of(ItemStack.of(nbt.getCompound("FrequencyLast")));

		owner = nbt.contains("Owner", 10) ? NbtUtils.readGameProfile(nbt.getCompound("Owner")) : null;
	}

	public NetworkKey getNetworkKey() {
		return new NetworkKey(owner, frequencyFirst, frequencyLast);
	}

	public void setFrequency(boolean first, ItemStack stack) {

		stack = stack.copy();
		stack.setCount(1);
		ItemStack toCompare = getFrequencyStack(first);
		boolean changed = !ItemStack.isSame(stack, toCompare) || !ItemStack.tagMatches(stack, toCompare);

		if (changed) onLeaveNetwork();

		if (first) frequencyFirst = Frequency.of(stack);
		else frequencyLast = Frequency.of(stack);

		if (!changed) return;

		blockEntity.sendData();
		onJoinNetwork();

	}

	public boolean testHit(int index, Vec3 hit) {
		BlockState state = blockEntity.getBlockState();
		Vec3 localHit = hit.subtract(Vec3.atLowerCornerOf(blockEntity.getBlockPos()));
		return getSlot(index).testHit(state, localHit);
	}

	public ValueBoxTransform getSlot(int index) {
		return index < 2 ? getFrequencySlot(index == 0) : playerSlot;
	}

	public ValueBoxTransform getFrequencySlot(boolean first) {
		return first ? firstSlot : secondSlot;
	}
	public ItemStack getFrequencyStack(boolean first) {
		return first ? frequencyFirst.getStack() : frequencyLast.getStack();
	}

	public boolean canInteract(Player player) {
		return !isAdventure(player) && isOwner(player);
	}

	private boolean isAdventure(Player player) {
		return player != null && !player.mayBuild() && !player.isSpectator();
	}

	@Nullable
	public GameProfile getOwner() {
		return owner;
	}

	public void setOwner(@Nullable GameProfile owner) {
		if (!Objects.equals(this.owner, owner)) {
			onLeaveNetwork();
			this.owner = owner;
			blockEntity.sendData();
			onJoinNetwork();
		}
	}

	protected void onLeaveNetwork() {}
	protected void onJoinNetwork() {}

	public boolean isOwner(Player player) {
		return owner == null || player.getGameProfile().equals(owner);
	}

	@Override
	public BehaviourType<?> getType() {
		return TYPE;
	}

	@Override
	public String getClipboardKey() {
		return "Frequencies";
	}

	@Override
	public boolean writeToClipboard(CompoundTag nbt, Direction side) {
		nbt.put("First", frequencyFirst.getStack().save(new CompoundTag()));
		nbt.put("Last", frequencyLast.getStack().save(new CompoundTag()));
		if (owner != null) NBTHelper.putMarker(nbt, "Owned");
		return true;
	}

	@Override
	public boolean readFromClipboard(CompoundTag nbt, Player player, Direction side, boolean simulate) {

		if (!nbt.contains("First") || !nbt.contains("Last") || !isOwner(player)) return false;
		if (simulate) return true;

		setFrequency(true, ItemStack.of(nbt.getCompound("First")));
		setFrequency(false, ItemStack.of(nbt.getCompound("Last")));
		setOwner(nbt.contains("Owned") ? player.getGameProfile() : null);

		return true;
	}
}

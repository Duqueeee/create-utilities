package me.duquee.createutilities.blocks.voidtypes.tank;

import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.utility.VecHelper;

import me.duquee.createutilities.CreateUtilities;
import me.duquee.createutilities.CreateUtilitiesClient;
import me.duquee.createutilities.blocks.voidtypes.VoidLinkBehaviour;
import me.duquee.createutilities.voidlink.VoidLinkSlot;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class VoidTankTileEntity extends SmartBlockEntity implements IHaveGoggleInformation {

	VoidLinkBehaviour link;

	public VoidTankTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
		createLink();
		behaviours.add(link);
	}

	public void createLink() {

		Triple<VoidLinkSlot, VoidLinkSlot, VoidLinkSlot> slots = VoidLinkSlot.makeSlots(
				index -> new VoidLinkSlot(index,
						state -> Direction.DOWN,
						VecHelper.voxelSpace(5.5F, 10.5F, -.001F)) );

		link = new VoidLinkBehaviour(this, slots);
	}

	public FluidTank getFluidStorage() {
		return level != null && !level.isClientSide ?
				CreateUtilities.VOID_TANKS_DATA.computeStorageIfAbsent(link.getNetworkKey()) :
				CreateUtilitiesClient.VOID_TANKS.computeStorageIfAbsent(link.getNetworkKey());
	}

	@Override
	public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return LazyOptional.of(this::getFluidStorage).cast();
		}
		return super.getCapability(cap, side);
	}

	@Override
	protected void read(CompoundTag tag, boolean clientPacket) {
		super.read(tag, clientPacket);
		if (clientPacket) getFluidStorage().readFromNBT(tag.getCompound("Tank"));
	}

	@Override
	protected void write(CompoundTag tag, boolean clientPacket) {
		if (clientPacket) tag.put("Tank", getFluidStorage().writeToNBT(new CompoundTag()));
		super.write(tag, clientPacket);
	}

	public boolean isClosed() {
		return getBlockState().getValue(VoidTankBlock.CLOSED);
	}

	@Override
	public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
		return containedFluidTooltip(tooltip, isPlayerSneaking, getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY));
	}
}

package me.duquee.createutilities.blocks.voidtypes.tank;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;

import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;

import com.simibubi.create.foundation.utility.VecHelper;

import io.github.fabricators_of_create.porting_lib.transfer.fluid.FluidTank;
import me.duquee.createutilities.CreateUtilities;
import me.duquee.createutilities.CreateUtilitiesClient;
import me.duquee.createutilities.blocks.voidtypes.VoidLinkBehaviour;
import me.duquee.createutilities.voidlink.VoidLinkSlot;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SidedStorageBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class VoidTankTileEntity extends SmartBlockEntity implements SidedStorageBlockEntity {

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

	@Override
	public @Nullable FluidTank getFluidStorage(Direction side) {
		return level != null && !level.isClientSide ?
				CreateUtilities.VOID_TANKS_DATA.computeStorageIfAbsent(link.getNetworkKey()) :
				CreateUtilitiesClient.VOID_TANKS.computeClientTankIfAbsent(link.getNetworkKey());
	}

	public FluidTank getFluidStorage() {
		return getFluidStorage(null);
	}

	@Override
	protected void read(CompoundTag tag, boolean clientPacket) {
		super.read(tag, clientPacket);
		if (clientPacket) getFluidStorage().readFromNBT(tag.getCompound("Tank"));
	}

	@Override
	protected void write(CompoundTag tag, boolean clientPacket) {
		tag.put("Tank", getFluidStorage().writeToNBT(new CompoundTag()));
		super.write(tag, clientPacket);
	}

	public boolean isClosed() {
		return getBlockState().getValue(VoidTankBlock.CLOSED);
	}

}

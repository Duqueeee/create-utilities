package me.duquee.createutilities.blocks.voidtypes.motor;

import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.utility.VecHelper;

import me.duquee.createutilities.voidlink.VoidLinkSlot;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import org.apache.commons.lang3.tuple.Triple;

import java.util.List;

public class VoidMotorTileEntity extends KineticBlockEntity {

	VoidMotorLinkBehaviour link;

	public VoidMotorTileEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
		super(typeIn, pos, state);
	}

	@Override
	public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
		createLink();
		behaviours.add(link);
	}

	public void createLink() {

		Triple<VoidLinkSlot, VoidLinkSlot, VoidLinkSlot> slots = VoidLinkSlot.makeSlots(
				index -> new VoidLinkSlot(index,
						state -> state.getValue(VoidMotorBlock.FACING),
						VecHelper.voxelSpace(5.5F, 10.5F, -.001F)));

		link = new VoidMotorLinkBehaviour(this, slots);

	}

	public void onConnectToVoidNetwork() {
		attachKinetics();
	}

	public void onDisconnectFromVoidNetwork() {
		detachKinetics();
		removeSource();
	}

	@Override
	public List<BlockPos> addPropagationLocations(IRotate block, BlockState state, List<BlockPos> neighbours) {
		neighbours.addAll(link.getNetwork());
		return neighbours;
	}

	@Override
	public float propagateRotationTo(KineticBlockEntity target, BlockState stateFrom, BlockState stateTo, BlockPos diff, boolean connectedViaAxes, boolean connectedViaCogs) {
		VoidMotorLinkBehaviour targetLink = (VoidMotorLinkBehaviour) BlockEntityBehaviour.get(target, VoidMotorLinkBehaviour.TYPE);
		if (targetLink != null) return targetLink.getNetworkKey().equals(link.getNetworkKey()) ? 1 : 0;
		return 0;
	}

	@Override
	protected boolean isNoisy() {
		return false;
	}

}

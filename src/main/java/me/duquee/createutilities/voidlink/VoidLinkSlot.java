package me.duquee.createutilities.voidlink;

import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;

import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.utility.AngleHelper;
import com.simibubi.create.foundation.utility.VecHelper;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import org.apache.commons.lang3.tuple.Triple;

import java.util.function.Function;

public class VoidLinkSlot extends ValueBoxTransform {

	protected int index;

	private final Function<BlockState, Direction> facingGetter;

	protected final Vec3 horizontal;
	protected final Vec3 vertical;

	public VoidLinkSlot(int index, Function<BlockState, Direction> facingGetter, Vec3 position) {

		this.index = index;
		this.facingGetter = facingGetter;

		if (index == 0) this.horizontal = position.add(.3125F, 0, 0);
		else if (index == 1) this.horizontal = position;
		else this.horizontal = position.add(.15625F, -.3125F, 0);

		vertical = getVertical(horizontal);

	}

	public int getIndex() {
		return index;
	}

	public boolean isFrequency() {
		return index < 2;
	}

	public boolean isOwner() {
		return index == 2;
	}

	public static Triple<VoidLinkSlot, VoidLinkSlot, VoidLinkSlot> makeSlots(Function<Integer, VoidLinkSlot> factory) {
		return Triple.of(factory.apply(0), factory.apply(1), factory.apply(2));
	}

	public boolean testHit(BlockState state, Vec3 localHit) {
		Vec3 offset = getLocalOffset(state);
		if (offset == null)
			return false;
		return localHit.distanceTo(offset) < scale / 3.5f;
	}

	@Override
	public Vec3 getLocalOffset(BlockState state) {

		Direction facing = facingGetter.apply(state);
		if (facing.getAxis().isHorizontal()) {
			float yRot = AngleHelper.horizontalAngle(facing);
			return VecHelper.rotateCentered(horizontal, yRot, Direction.Axis.Y);
		} else {
			return VecHelper.rotateCentered(vertical, facing == Direction.DOWN ? 180 : 0, Direction.Axis.X);
		}

	}

	@Override
	public void rotate(BlockState state, PoseStack ms) {
		Direction facing = facingGetter.apply(state);
		float yRot = facing.getAxis().isVertical() ? 90 : AngleHelper.horizontalAngle(facing);
		float xRot = facing == Direction.UP ? 270 : facing == Direction.DOWN ? 90 : 0;
		TransformStack.cast(ms).rotateY(yRot).rotateX(xRot);
	}

	@Override
	public float getScale() {
		return .4975f;
	}

	private static Vec3 getVertical(Vec3 horizontal) {
		return new Vec3(horizontal.y, horizontal.z, horizontal.x);
	}

}

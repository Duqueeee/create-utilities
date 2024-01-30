package me.duquee.createutilities.blocks.gearcube;

import com.jozufozu.flywheel.backend.Backend;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;

import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;

import com.simibubi.create.foundation.utility.AnimationTickHolder;
import com.simibubi.create.foundation.utility.Iterate;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class SimpleKineticRenderer<T extends KineticBlockEntity> extends KineticBlockEntityRenderer<T> {

	public SimpleKineticRenderer(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	protected void renderSafe(T te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
		if (Backend.canUseInstancing(te.getLevel())) return;

		BlockState state = te.getBlockState();
		if (!(state.getBlock() instanceof IRotate block)) return;

		final BlockPos pos = te.getBlockPos();
		float time = AnimationTickHolder.getRenderTime(te.getLevel());

		for (Direction direction : Iterate.directions) {

			if (!block.hasShaftTowards(te.getLevel(), pos, state, direction)) continue;
			Direction.Axis axis = direction.getAxis();

			SuperByteBuffer shaft = CachedBufferer.partialFacing(AllPartialModels.SHAFT_HALF, state, direction);
			float offset = getRotationOffsetForPosition(te, pos, axis);
			float angle = (time * te.getSpeed() * 3f / 10) % 360;

			if (te.getSpeed() != 0 && te.hasSource()) {
				BlockPos source = te.source.subtract(pos);
				Direction sourceFacing = Direction.getNearest(source.getX(), source.getY(), source.getZ());
				if (sourceFacing.getAxis() == axis)
					angle *= sourceFacing == direction ? 1 : -1;
				else if (sourceFacing.getAxisDirection() == direction.getAxisDirection())
					angle *= -1;
			}

			angle += offset;
			angle = angle / 180f * (float) Math.PI;

			kineticRotationTransform(shaft, te, axis, angle, light);
			shaft.renderInto(ms, buffer.getBuffer(RenderType.solid()));
		}

	}
}

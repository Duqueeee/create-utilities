package me.duquee.createutilities.blocks.voidtypes.chest;

import com.mojang.blaze3d.vertex.PoseStack;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;

import me.duquee.createutilities.blocks.CUPartialsModels;
import me.duquee.createutilities.blocks.voidtypes.VoidTileEntityRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.BiFunction;
import java.util.function.Function;

public class VoidChestRenderer extends VoidTileEntityRenderer<VoidChestTileEntity> {

	public VoidChestRenderer(BlockEntityRendererProvider.Context context,
							 float width, Function<Direction, Float> offSet, BiFunction<VoidChestTileEntity, Direction, Boolean> shouldRenderFrame) {
		super(context, width, offSet, shouldRenderFrame);
	}

	@Override
	protected void renderSafe(VoidChestTileEntity te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
		super.renderSafe(te, partialTicks, ms, buffer, light, overlay);

		BlockState blockState = te.getBlockState();
		Direction facing = blockState.getValue(VoidChestBlock.FACING).getOpposite();

		SuperByteBuffer lid = CachedBufferer.partial(CUPartialsModels.VOID_CHEST_LID, blockState);
		float lidAngle = te.lid.getValue(partialTicks);

		VertexConsumer builder = buffer.getBuffer(RenderType.cutoutMipped());
		lid.centre()
				.rotateY(-facing.toYRot())
				.unCentre()
				.translate(0, 10 / 16f, 15 / 16f)
				.rotateX(135 * lidAngle)
				.translate(0, -10 / 16f, -15 / 16f)
				.light(light)
				.renderInto(ms, builder);

	}
}

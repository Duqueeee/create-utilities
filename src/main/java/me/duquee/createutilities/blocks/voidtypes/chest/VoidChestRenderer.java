package me.duquee.createutilities.blocks.voidtypes.chest;

import com.mojang.blaze3d.vertex.PoseStack;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;

import me.duquee.createutilities.blocks.CUPartialsModels;
import me.duquee.createutilities.blocks.voidtypes.VoidTileRenderer;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class VoidChestRenderer extends SafeBlockEntityRenderer<VoidChestTileEntity> implements VoidTileRenderer<VoidChestTileEntity> {

	private final SkullModelBase skullModelBase;

	public VoidChestRenderer(BlockEntityRendererProvider.Context context) {
		skullModelBase = new SkullModel(context.getModelSet().bakeLayer(ModelLayers.PLAYER_HEAD));
	}

	@Override
	protected void renderSafe(VoidChestTileEntity te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
		renderVoid(te, partialTicks, ms, buffer, light, overlay);

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

	@Override
	public SkullModelBase getSkullModelBase() {
		return skullModelBase;
	}

	@Override
	public boolean shouldRenderFrame(VoidChestTileEntity te, Direction direction) {
		return direction == Direction.UP && !te.isClosed();
	}

	@Override
	public float getFrameWidth() {
		return .625F;
	}

	@Override
	public float getFrameOffset(Direction direction) {
		return .626F;
	}
}

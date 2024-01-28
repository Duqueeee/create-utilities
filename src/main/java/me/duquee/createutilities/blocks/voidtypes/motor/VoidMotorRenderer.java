package me.duquee.createutilities.blocks.voidtypes.motor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;

import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;

import me.duquee.createutilities.blocks.voidtypes.VoidTileRenderer;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class VoidMotorRenderer extends KineticBlockEntityRenderer<VoidMotorTileEntity> implements VoidTileRenderer<VoidMotorTileEntity> {

	private final SkullModelBase skullModelBase;

	public VoidMotorRenderer(BlockEntityRendererProvider.Context context) {
		super(context);
		skullModelBase = new SkullModel(context.getModelSet().bakeLayer(ModelLayers.PLAYER_HEAD));
	}

	@Override
	protected void renderSafe(VoidMotorTileEntity te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
		super.renderSafe(te, partialTicks, ms, buffer, light, overlay);
		renderVoid(te, partialTicks, ms, buffer, light, overlay);
	}

	@Override
	public SkullModelBase getSkullModelBase() {
		return skullModelBase;
	}

	@Override
	public boolean shouldRenderFrame(VoidMotorTileEntity te, Direction direction) {
		return te.getBlockState().getValue(VoidMotorBlock.FACING) == direction;
	}

	@Override
	public float getFrameWidth() {
		return .375F;
	}

	@Override
	public float getFrameOffset(Direction direction) {
		return .876F;
	}

	@Override
	protected SuperByteBuffer getRotatedModel(VoidMotorTileEntity te, BlockState state) {
		return CachedBufferer.partialFacing(AllPartialModels.SHAFT_HALF, state);
	}

}

package me.duquee.createutilities.blocks.voidtypes.tank;

import com.mojang.blaze3d.vertex.PoseStack;

import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;
import com.simibubi.create.foundation.fluid.FluidRenderer;

import io.github.fabricators_of_create.porting_lib.transfer.fluid.FluidTank;
import me.duquee.createutilities.blocks.voidtypes.VoidTileRenderer;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;

public class VoidTankRenderer extends SafeBlockEntityRenderer<VoidTankTileEntity> implements VoidTileRenderer<VoidTankTileEntity> {

	private final SkullModelBase skullModelBase;

	public VoidTankRenderer(BlockEntityRendererProvider.Context context) {
		skullModelBase = new SkullModel(context.getModelSet().bakeLayer(ModelLayers.PLAYER_HEAD));
	}

	@Override
	protected void renderSafe(VoidTankTileEntity te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
		renderVoid(te, partialTicks, ms, buffer, light, overlay);

		FluidTank tank = te.getFluidStorage();
		if (!te.isClosed()) FluidRenderer.renderFluidBox(tank.getFluid(), .125F, .25F, .125F, .875F, .25F + 0.5F * tank.getAmount()/tank.getCapacity(), .875F, buffer, ms, light, false);
	}

	@Override
	public SkullModelBase getSkullModelBase() {
		return skullModelBase;
	}

	@Override
	public boolean shouldRenderFrame(VoidTankTileEntity te, Direction direction) {
		return !te.isClosed();
	}

	@Override
	public float getFrameWidth() {
		return .75F;
	}

	@Override
	public float getFrameOffset(Direction direction) {
		return direction.getAxis() == Direction.Axis.Y ? .251F : .124F;
	}
}

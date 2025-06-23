package me.duquee.createutilities.blocks.voidtypes.tank;
//a way fuck the bug
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.blockEntity.renderer.SmartBlockEntityRenderer;
import com.simibubi.create.foundation.fluid.FluidRenderer;
import me.duquee.createutilities.blocks.voidtypes.VoidTileRenderer;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class VoidTankRenderer
		extends SmartBlockEntityRenderer<VoidTankTileEntity>
		implements VoidTileRenderer<VoidTankTileEntity> {

	private final SkullModelBase skullModelBase;

	public VoidTankRenderer(BlockEntityRendererProvider.Context context) {
		super(context);
		skullModelBase = new SkullModel(context.getModelSet().bakeLayer(ModelLayers.PLAYER_HEAD));
	}

	@Override
	protected void renderSafe(VoidTankTileEntity te, float partialTicks,
							  PoseStack ms, MultiBufferSource buffer,
							  int light, int overlay) {
		super.renderSafe(te, partialTicks, ms, buffer, light, overlay);
		renderVoid(te, partialTicks, ms, buffer, light, overlay);
		FluidTank tank = te.getFluidStorage();
		if (!te.isClosed() && !tank.isEmpty()) {
			float capacity = tank.getCapacity();
			float amount   = tank.getFluidAmount();
			float progress = amount / capacity;
			float radius   = 0.375f;
			for (Direction dir : Direction.Plane.HORIZONTAL) {
				FluidRenderer.renderFluidStream(
						tank.getFluid(),
						dir,
						radius,
						progress,
						false,
						buffer,
						ms,
						light
				);
			}

			FluidRenderer.renderFluidStream(
					tank.getFluid(),
					Direction.UP,
					radius,
					progress,
					false,
					buffer,
					ms,
					light
			);
		}
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
		return 0.75f;
	}

	@Override
	public float getFrameOffset(Direction direction) {
		return direction.getAxis() == Direction.Axis.Y ? 0.251f : 0.124f;
	}
}

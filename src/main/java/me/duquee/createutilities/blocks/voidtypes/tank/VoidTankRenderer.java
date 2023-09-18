package me.duquee.createutilities.blocks.voidtypes.tank;

import com.mojang.blaze3d.vertex.PoseStack;

import com.simibubi.create.foundation.fluid.FluidRenderer;

import me.duquee.createutilities.blocks.voidtypes.VoidTileEntityRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class VoidTankRenderer extends VoidTileEntityRenderer<VoidTankTileEntity> {

	public VoidTankRenderer(BlockEntityRendererProvider.Context context) {
		super(context,
				.75F,
				direction -> direction.getAxis() == Direction.Axis.Y ? .251F : .124F,
				(te, direction) -> !te.isClosed());
	}

	@Override
	protected void renderSafe(VoidTankTileEntity te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
		super.renderSafe(te, partialTicks, ms, buffer, light, overlay);

		FluidTank tank = te.getFluidStorage();
		if (!te.isClosed()) FluidRenderer.renderFluidBox(tank.getFluid(), .125F, .25F, .125F, .875F, .25F + 0.5F * tank.getFluidAmount()/tank.getCapacity(), .875F, buffer, ms, light, false);
	}

}

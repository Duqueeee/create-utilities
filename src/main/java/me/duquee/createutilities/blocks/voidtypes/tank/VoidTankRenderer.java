package me.duquee.createutilities.blocks.voidtypes.tank;

import com.mojang.blaze3d.vertex.PoseStack;

import com.simibubi.create.foundation.fluid.FluidRenderer;

import io.github.fabricators_of_create.porting_lib.transfer.fluid.FluidTank;
import me.duquee.createutilities.blocks.voidtypes.VoidTileEntityRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;

import java.util.function.BiFunction;
import java.util.function.Function;

public class VoidTankRenderer extends VoidTileEntityRenderer<VoidTankTileEntity> {

	public VoidTankRenderer(BlockEntityRendererProvider.Context context, float width, Function<Direction, Float> offSet, BiFunction<VoidTankTileEntity, Direction, Boolean> shouldRenderFrame) {
		super(context, width, offSet, shouldRenderFrame);
	}

	@Override
	protected void renderSafe(VoidTankTileEntity te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
		super.renderSafe(te, partialTicks, ms, buffer, light, overlay);

		FluidTank tank = te.getFluidStorage();
		if (!te.isClosed()) FluidRenderer.renderFluidBox(tank.getFluid(), .125F, .25F, .125F, .875F, .25F + 0.5F * tank.getAmount()/tank.getCapacity(), .875F, buffer, ms, light, false);
	}

}

package me.duquee.createutilities.blocks.voidtypes.motor;

import me.duquee.createutilities.blocks.voidtypes.VoidTileEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class VoidMotorRenderer extends VoidTileEntityRenderer<VoidMotorTileEntity> {

	public VoidMotorRenderer(BlockEntityRendererProvider.Context context) {
		super(context,
				.375F,
				direction -> .876F,
				(te, direction) -> te.getBlockState().getValue(VoidMotorBlock.FACING) == direction
		);
	}

}

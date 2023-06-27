package me.duquee.createutilities.blocks;

import com.simibubi.create.content.kinetics.base.HalfShaftInstance;
import com.simibubi.create.content.kinetics.gearbox.GearboxBlockEntity;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

import me.duquee.createutilities.blocks.voidtypes.chest.VoidChestRenderer;
import me.duquee.createutilities.blocks.voidtypes.chest.VoidChestTileEntity;
import me.duquee.createutilities.blocks.voidtypes.motor.VoidMotorBlock;
import me.duquee.createutilities.blocks.voidtypes.motor.VoidMotorTileEntity;
import me.duquee.createutilities.blocks.gearcube.GearcubeInstance;
import me.duquee.createutilities.blocks.voidtypes.VoidTileEntityRenderer;
import me.duquee.createutilities.blocks.voidtypes.tank.VoidTankRenderer;
import me.duquee.createutilities.blocks.voidtypes.tank.VoidTankTileEntity;
import net.minecraft.core.Direction;

import static me.duquee.createutilities.CreateUtilities.REGISTRATE;

public class CUTileEntities {

	public static final BlockEntityEntry<VoidMotorTileEntity> VOID_MOTOR = REGISTRATE
			.blockEntity("void_motor", VoidMotorTileEntity::new)
			.instance(() -> HalfShaftInstance::new, true)
			.validBlocks(CUBlocks.VOID_MOTOR)
			.renderer(() -> context -> new VoidTileEntityRenderer<>(context, .375F, direction -> .876F,
					(te, direction) -> te.getBlockState().getValue(VoidMotorBlock.FACING) == direction
			)).register();

	public static final BlockEntityEntry<VoidChestTileEntity> VOID_CHEST = REGISTRATE
			.blockEntity("void_chest", VoidChestTileEntity::new)
			.validBlocks(CUBlocks.VOID_CHEST)
			.renderer(() -> context -> new VoidChestRenderer(context, .625F, direction -> .626F,
					(te, direction) -> direction == Direction.UP && !te.isClosed()
			)).register();

	public static final BlockEntityEntry<VoidTankTileEntity> VOID_TANK = REGISTRATE
			.blockEntity("void_tank", VoidTankTileEntity::new)
			.validBlocks(CUBlocks.VOID_TANK)
			.renderer(() -> context -> new VoidTankRenderer(context, .75F,
					direction -> direction.getAxis() == Direction.Axis.Y ? .251F : .124F,
					(te, direction) -> !te.isClosed()
			)).register();

	public static final BlockEntityEntry<GearboxBlockEntity> GEARCUBE = REGISTRATE
			.blockEntity("gearcube", GearboxBlockEntity::new)
			.instance(() -> GearcubeInstance::new, false)
			.validBlocks(CUBlocks.GEARCUBE)
			.register();

	public static void register() {}

}

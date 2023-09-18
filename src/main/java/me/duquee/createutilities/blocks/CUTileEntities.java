package me.duquee.createutilities.blocks;

import com.simibubi.create.content.kinetics.base.HalfShaftInstance;
import com.simibubi.create.content.kinetics.gearbox.GearboxBlockEntity;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

import me.duquee.createutilities.blocks.voidtypes.chest.VoidChestRenderer;
import me.duquee.createutilities.blocks.voidtypes.chest.VoidChestTileEntity;
import me.duquee.createutilities.blocks.voidtypes.motor.VoidMotorRenderer;
import me.duquee.createutilities.blocks.voidtypes.motor.VoidMotorTileEntity;
import me.duquee.createutilities.blocks.gearcube.GearcubeInstance;
import me.duquee.createutilities.blocks.voidtypes.tank.VoidTankRenderer;
import me.duquee.createutilities.blocks.voidtypes.tank.VoidTankTileEntity;

import static me.duquee.createutilities.CreateUtilities.REGISTRATE;

public class CUTileEntities {

	public static final BlockEntityEntry<VoidMotorTileEntity> VOID_MOTOR = REGISTRATE
			.blockEntity("void_motor", VoidMotorTileEntity::new)
			.instance(() -> HalfShaftInstance::new, true)
			.validBlocks(CUBlocks.VOID_MOTOR)
			.renderer(() -> VoidMotorRenderer::new)
			.register();

	public static final BlockEntityEntry<VoidChestTileEntity> VOID_CHEST = REGISTRATE
			.blockEntity("void_chest", VoidChestTileEntity::new)
			.validBlocks(CUBlocks.VOID_CHEST)
			.renderer(() -> VoidChestRenderer::new)
			.register();

	public static final BlockEntityEntry<VoidTankTileEntity> VOID_TANK = REGISTRATE
			.blockEntity("void_tank", VoidTankTileEntity::new)
			.validBlocks(CUBlocks.VOID_TANK)
			.renderer(() -> VoidTankRenderer::new)
			.register();

	public static final BlockEntityEntry<GearboxBlockEntity> GEARCUBE = REGISTRATE
			.blockEntity("gearcube", GearboxBlockEntity::new)
			.instance(() -> GearcubeInstance::new, false)
			.validBlocks(CUBlocks.GEARCUBE)
			.register();

	public static void register() {}

}

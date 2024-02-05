package me.duquee.createutilities.blocks;

import com.simibubi.create.content.kinetics.base.HalfShaftInstance;
import com.simibubi.create.content.kinetics.gearbox.GearboxBlockEntity;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

import me.duquee.createutilities.blocks.gearcube.SimpleKineticRenderer;
import me.duquee.createutilities.blocks.lgearbox.LShapedGearboxInstance;
import me.duquee.createutilities.blocks.voidtypes.battery.VoidBatteryRenderer;
import me.duquee.createutilities.blocks.voidtypes.battery.VoidBatteryTileEntity;
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

	public static final BlockEntityEntry<VoidBatteryTileEntity> VOID_BATTERY = REGISTRATE
			.blockEntity("void_battery", VoidBatteryTileEntity::new)
			.validBlocks(CUBlocks.VOID_BATTERY)
			.renderer(() -> VoidBatteryRenderer::new)
			.register();

	public static final BlockEntityEntry<GearboxBlockEntity> GEARCUBE = REGISTRATE
			.blockEntity("gearcube", GearboxBlockEntity::new)
			.instance(() -> GearcubeInstance::new, false)
			.validBlocks(CUBlocks.GEARCUBE)
			.renderer(() -> SimpleKineticRenderer::new)
			.register();

	public static final BlockEntityEntry<GearboxBlockEntity> LSHAPED_GEARBOX = REGISTRATE
			.blockEntity("lshaped_gearbox", GearboxBlockEntity::new)
			.instance(() -> LShapedGearboxInstance::new, false)
			.validBlocks(CUBlocks.LSHAPED_GEARBOX)
			.renderer(() -> SimpleKineticRenderer::new)
			.register();

	public static void register() {}

}

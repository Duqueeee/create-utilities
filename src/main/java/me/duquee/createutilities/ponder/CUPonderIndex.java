package me.duquee.createutilities.ponder;

import com.simibubi.create.foundation.ponder.PonderRegistrationHelper;

import me.duquee.createutilities.CreateUtilities;
import me.duquee.createutilities.blocks.CUBlocks;

public class CUPonderIndex {

	static final PonderRegistrationHelper HELPER = new PonderRegistrationHelper(CreateUtilities.ID);

	public static void register() {

		HELPER.addStoryBoard(CUBlocks.VOID_MOTOR, "void_motor", VoidScenes::voidMotor);
		HELPER.addStoryBoard(CUBlocks.VOID_CHEST, "void_chest", VoidScenes::voidChest);
		HELPER.addStoryBoard(CUBlocks.VOID_TANK, "void_tank", VoidScenes::voidTank);
		HELPER.addStoryBoard(CUBlocks.VOID_BATTERY, "void_battery", VoidScenes::voidBattery);

		HELPER.addStoryBoard(CUBlocks.GEARCUBE, "gearcube", GearboxScenes::gearCube);
		HELPER.addStoryBoard(CUBlocks.LSHAPED_GEARBOX, "lshaped_gearbox", GearboxScenes::lShapedGearbox);

	}

}

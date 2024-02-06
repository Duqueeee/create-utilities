package me.duquee.createutilities.ponder;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.kinetics.simpleRelays.ShaftBlock;
import com.simibubi.create.foundation.ponder.ElementLink;
import com.simibubi.create.foundation.ponder.PonderPalette;
import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import com.simibubi.create.foundation.ponder.Selection;

import com.simibubi.create.foundation.ponder.element.InputWindowElement;
import com.simibubi.create.foundation.ponder.element.WorldSectionElement;

import com.simibubi.create.foundation.utility.Pointing;

import me.duquee.createutilities.blocks.CUBlocks;
import me.duquee.createutilities.blocks.lgearbox.LShapedGearboxBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class GearboxScenes {

	public static void gearCube(SceneBuilder scene, SceneBuildingUtil util) {

		scene.title("gearcube", "Relaying Rotational Force using 6-Axis Gearboxes");
		scene.configureBasePlate(0, 0, 5);
		scene.setSceneOffsetY(-1);

		Selection belt = util.select.fromTo(2, 0, 5, 2, 3, 5)
				.add(util.select.fromTo(2, 3, 4, 3, 2, 3));

		scene.showBasePlate();
		scene.world.showSection(belt, Direction.UP);
		scene.idle(10);

		Selection gearboxes = util.select.fromTo(2, 3, 2, 2, 4, 2);
		scene.world.showSection(gearboxes, Direction.SOUTH);
		scene.idle(10);

		scene.world.showSection(util.select.position(2, 2, 2), Direction.UP);
		scene.world.showSection(util.select.position(2, 3, 1), Direction.SOUTH);
		scene.world.showSection(util.select.position(1, 4, 2), Direction.EAST);
		scene.world.showSection(util.select.position(3, 4, 2), Direction.WEST);
		scene.world.showSection(util.select.position(2, 5, 2), Direction.DOWN);
		scene.idle(20);

		BlockPos bottomGBPos = util.grid.at(2, 3, 2);
		Vec3 bottomGBVec = util.vector.centerOf(bottomGBPos);
		scene.overlay.showText(50)
				.pointAt(bottomGBVec)
				.placeNearTarget()
				.text("Relaying rotation in all directions can get bulky quickly");
		scene.idle(60);

		scene.world.hideSection(util.select.position(2, 2, 2), Direction.DOWN);
		scene.world.hideSection(util.select.position(2, 3, 1), Direction.NORTH);
		scene.world.hideSection(util.select.position(1, 4, 2), Direction.WEST);
		scene.world.hideSection(util.select.position(3, 4, 2), Direction.EAST);
		scene.world.hideSection(util.select.position(2, 5, 2), Direction.UP);
		scene.idle(10);

		scene.world.hideSection(gearboxes, Direction.NORTH);
		scene.idle(20);

		BlockPos gearcubePos = util.grid.at(2, 3, 2);
		Selection gearcube = util.select.position(gearcubePos);
		scene.world.setBlock(gearcubePos, CUBlocks.GEARCUBE.getDefaultState(), false);
		scene.world.showSection(gearcube, Direction.SOUTH);
		scene.world.setKineticSpeed(gearcube, 16);
		scene.idle(10);

		BlockPos shaft1Pos = util.grid.at(1, 3, 2);
		BlockPos shaft2Pos = util.grid.at(3, 3, 2);
		BlockPos shaft3Pos = util.grid.at(2, 4, 2);
		BlockState shaftState = AllBlocks.SHAFT.getDefaultState()
				.setValue(ShaftBlock.AXIS, Direction.Axis.X);
		scene.world.setBlock(shaft1Pos, shaftState, false);
		scene.world.setBlock(shaft2Pos, shaftState, false);
		scene.world.setBlock(shaft3Pos, shaftState.setValue(ShaftBlock.AXIS, Direction.Axis.Y), false);

		Selection shaft1 = util.select.position(shaft1Pos);
		Selection shaft2 = util.select.position(shaft2Pos);
		Selection shaft3 = util.select.position(shaft3Pos);
		scene.world.setKineticSpeed(shaft1, 16);
		scene.world.setKineticSpeed(shaft2, -16);
		scene.world.setKineticSpeed(shaft3, 16);

		scene.world.showSection(util.select.position(2, 2, 2), Direction.UP);
		scene.world.showSection(util.select.position(2, 3, 1), Direction.SOUTH);
		scene.world.showSection(util.select.position(shaft1Pos), Direction.EAST);
		scene.world.showSection(util.select.position(shaft2Pos), Direction.WEST);
		scene.world.showSection(util.select.position(shaft3Pos), Direction.DOWN);
		scene.idle(20);

		scene.overlay.showText(60)
				.colored(PonderPalette.GREEN)
				.pointAt(util.vector.topOf(3, 2, 3))
				.placeNearTarget()
				.attachKeyFrame()
				.text("A 6-Axis Gearcube is the more compact equivalent of this setup");
		scene.idle(70);

	}

	public static void lShapedGearbox(SceneBuilder scene, SceneBuildingUtil util) {

		scene.title("lshaped_gearbox", "Relaying Rotational Force using L-Shaped Gearboxes");
		scene.configureBasePlate(0, 0, 5);

		Selection belt = util.select.fromTo(2, 0, 5, 2, 2, 5)
				.add(util.select.position(2, 2, 4));

		scene.showBasePlate();
		scene.world.showSection(belt, Direction.UP);
		scene.idle(10);

		BlockPos cog1Pos = util.grid.at(2, 2, 3);
		BlockPos cog2Pos = util.grid.at(1, 2, 2);

		Selection cog1 = util.select.position(cog1Pos);
		Selection cog2 = util.select.position(cog2Pos);
		BlockPos shaftPos = util.grid.at(0, 2, 2);
		Selection shaft = util.select.position(shaftPos);

		scene.world.showSection(cog1, Direction.SOUTH);
		scene.idle(5);
		scene.world.showSection(cog2.add(shaft), Direction.EAST);
		scene.idle(10);

		scene.overlay.showText(50)
				.pointAt(util.vector.blockSurface(shaftPos, Direction.WEST))
				.placeNearTarget()
				.text("Changing directions can get bulky quickly");

		scene.idle(50);
		scene.world.hideSection(cog1.add(cog2), Direction.UP);
		scene.idle(20);

		BlockPos gearboxPos = util.grid.at(2, 2, 2);
		Selection gearbox = util.select.position(gearboxPos);
		scene.world.setKineticSpeed(gearbox, 16);

		BlockState shaftState = AllBlocks.SHAFT.getDefaultState();
		scene.world.setBlock(cog1Pos, shaftState.setValue(ShaftBlock.AXIS, Direction.Axis.Z), false);
		scene.world.setBlock(cog2Pos, shaftState.setValue(ShaftBlock.AXIS, Direction.Axis.X), false);
		scene.world.showSection(util.select.fromTo(cog1Pos, cog2Pos), Direction.DOWN);
		scene.idle(10);

		scene.overlay.showText(80)
				.colored(PonderPalette.GREEN)
				.pointAt(util.vector.blockSurface(gearboxPos, Direction.NORTH))
				.placeNearTarget()
				.text("A more compact alternative to this setup is using a Gearbox, but two shafts go unused");
		scene.idle(80);

		scene.world.hideSection(gearbox, Direction.UP);
		scene.idle(20);

		BlockState gearboxState = CUBlocks.LSHAPED_GEARBOX.getDefaultState()
				.setValue(LShapedGearboxBlock.FACING_1, Direction.WEST)
				.setValue(LShapedGearboxBlock.FACING_2, Direction.WEST);
		scene.world.setBlock(gearboxPos, gearboxState, false);
		scene.world.setKineticSpeed(gearbox, -16);
		ElementLink<WorldSectionElement> lGearbox = scene.world.showIndependentSection(gearbox, Direction.DOWN);
		scene.idle(10);

		scene.overlay.showText(80)
				.colored(PonderPalette.GREEN)
				.pointAt(util.vector.blockSurface(gearboxPos, Direction.NORTH))
				.placeNearTarget()
				.text("With an L-Shaped Gearbox you can make this in a cleaner and cheaper way");
		scene.idle(80);
		scene.addKeyframe();

		belt = util.select.fromTo(2, 0, 5, 2, 2, 5)
				.add(util.select.position(2, 2, 4));
		scene.world.hideSection(belt.add(cog1), Direction.SOUTH);
		scene.world.hideSection(cog2.add(shaft), Direction.WEST);
		scene.idle(20);

		scene.world.setKineticSpeed(gearbox, 0);
		scene.rotateCameraY(-90);
		scene.world.moveSection(lGearbox, new Vec3(0, -1, 0), 15);
		scene.idle(30);

		BlockPos lGearboxPos = util.grid.at(2, 1, 2);
		Vec3 lGearboxVec = util.vector.blockSurface(lGearboxPos, Direction.DOWN);
		scene.overlay.showControls(new InputWindowElement(lGearboxVec, Pointing.UP).rightClick()
				.withWrench(), 40);

		for (int i = 0; i < 8; i++) {
			scene.idle(10);
			scene.world.modifyBlock(gearboxPos, s -> s.cycle(LShapedGearboxBlock.FACING_2), false);
			if (i == 1) {
				scene.overlay.showText(50)
						.text("By Right-clicking it with a Wrench, you can change the orientation of the secondary axis")
						.pointAt(lGearboxVec);
			}
		}
		scene.idle(20);

	}

}

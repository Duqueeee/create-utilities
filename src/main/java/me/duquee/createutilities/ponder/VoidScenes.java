package me.duquee.createutilities.ponder;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllFluids;
import com.simibubi.create.foundation.ponder.ElementLink;
import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;

import com.simibubi.create.foundation.ponder.Selection;

import com.simibubi.create.foundation.ponder.element.EntityElement;
import com.simibubi.create.foundation.ponder.element.InputWindowElement;
import com.simibubi.create.foundation.utility.Pointing;

import me.duquee.createutilities.blocks.voidtypes.battery.VoidBatteryTileEntity;
import me.duquee.createutilities.blocks.voidtypes.chest.VoidChestTileEntity;
import me.duquee.createutilities.blocks.voidtypes.motor.VoidMotorTileEntity;
import me.duquee.createutilities.blocks.voidtypes.tank.VoidTankTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import java.util.function.Consumer;

public class VoidScenes {

	public static void voidMotor(SceneBuilder scene, SceneBuildingUtil util) {

		scene.title("void_motor", "Using Void Motors");
		scene.configureBasePlate(0, 0, 5);
		scene.showBasePlate();
		scene.world.showSection(util.select.position(5, 0, 2), Direction.UP);

		Selection source = util.select.fromTo(5, 1, 1, 4, 1, 1);
		Selection receiver = util.select.fromTo(1, 1, 2, 2, 1, 2);

		scene.world.showSection(source, Direction.DOWN);
		scene.idle(10);
		scene.world.showSection(receiver, Direction.DOWN);
		scene.idle(10);

		BlockPos sourcePos = util.grid.at(4, 1, 1);
		BlockPos receiverPos = util.grid.at(1, 1, 2);

		playVoidSequence(
				scene, util, VoidMotorTileEntity.class,
				.015f, 0,
				"Void Motor", "Rotational Force",
				sourcePos, receiverPos,
				Direction.WEST, Direction.WEST,
				(pos) -> scene.world.setKineticSpeed(receiver, 0),
				(pos) -> scene.world.setKineticSpeed(receiver, -32),
				false, false
		);

	}

	public static void voidChest(SceneBuilder scene, SceneBuildingUtil util) {

		scene.title("void_chest", "Using Void Chests");
		scene.configureBasePlate(1, 0, 5);
		scene.showBasePlate();
		scene.world.showSection(util.select.position(0, 0, 3)
				.add(util.select.position(6, 0, 3)), Direction.UP);

		Selection source = util.select.fromTo(4, 1, 0, 6, 2, 3);
		Selection receiver = util.select.fromTo(0, 1, 0, 2, 2, 3);

		scene.idle(10);
		scene.world.showSection(source, Direction.DOWN);
		scene.world.showSection(receiver, Direction.DOWN);
		scene.idle(10);

		ParallelInstruction parallel = new ParallelInstruction(scene);

		BlockPos sourceEntryBelt = util.grid.at(4, 1, 0);
		BlockPos sourceExitBelt = util.grid.at(4, 1, 2);
		BlockPos receiverEntryBelt = util.grid.at(2, 1, 2);
		ItemStack stack = AllBlocks.BRASS_BLOCK.asStack();

		Vec3 motion = new Vec3(0, -.2, 0);
		for (int i = 0; i < 27; i++) {

			ElementLink<EntityElement> item = parallel.scene.world.createItemEntity(
					util.vector.of(4.75, 3, 0.5), motion, stack);
			parallel.scene.idle(5);

			parallel.scene.world.modifyEntity(item, Entity::discard);
			parallel.scene.world.createItemOnBelt(sourceEntryBelt, Direction.EAST, stack);
			parallel.scene.idle(16);

			parallel.scene.world.removeItemsFromBelt(sourceExitBelt);
			parallel.scene.world.flapFunnel(sourceExitBelt.above(), false);

			if (i < 6 || i > 21) {
				parallel.scene.world.createItemOnBelt(receiverEntryBelt, Direction.EAST, stack);
				parallel.scene.world.flapFunnel(receiverEntryBelt.above(), true);
			}

		}

		scene.addInstruction(parallel);

		BlockPos sourcePos = util.grid.at(4, 2, 3);
		BlockPos receiverPos = util.grid.at(2, 2, 3);

		playVoidSequence(
				scene, util, VoidChestTileEntity.class,
				-.0475f, -.1875f,
				"Void Chest", "Items",
				sourcePos, receiverPos,
				Direction.SOUTH, Direction.SOUTH,
				(pos) -> {},
				(pos) -> {},
				true, false
		);

	}

	public static void voidTank(SceneBuilder scene, SceneBuildingUtil util) {

		scene.title("void_tank", "Using Void Tanks");
		scene.configureBasePlate(0, 0, 5);
		scene.showBasePlate();

		Selection pipes = util.select.fromTo(1, 0, 5, 3, 1, 5)
				.add(util.select.fromTo(1, 1, 4, 3, 1, 4));

		BlockPos sourcePos = util.grid.at(1, 1, 3);
		BlockPos secSourcePos = util.grid.at(3, 1, 3);
		BlockPos receiverPos = util.grid.at(2, 1, 1);

		Selection source = util.select.position(sourcePos);
		Selection secSource = util.select.position(secSourcePos);
		Selection receiver = util.select.position(receiverPos);

		scene.world.modifyBlockEntity(sourcePos, VoidTankTileEntity.class,
				te -> te.getFluidStorage().setFluid(FluidStack.EMPTY));

		scene.world.modifyBlockEntity(secSourcePos, VoidTankTileEntity.class,
				te -> te.getFluidStorage().setFluid(FluidStack.EMPTY));

		scene.idle(10);
		scene.world.showSection(pipes, Direction.NORTH);
		scene.world.showSection(source, Direction.SOUTH);
		scene.world.showSection(secSource, Direction.SOUTH);
		scene.idle(10);
		scene.world.showSection(receiver, Direction.DOWN);
		scene.idle(10);

		ParallelInstruction parallel = new ParallelInstruction(scene);

		FluidStack honey = new FluidStack(AllFluids.HONEY.get(), 500);
		FluidStack lava = new FluidStack(Fluids.LAVA, 500);
		for (int i = 0; i < 8; i++) {

			parallel.scene.world.modifyBlockEntity(sourcePos, VoidTankTileEntity.class,
					te -> te.getFluidStorage().fill(honey, IFluidHandler.FluidAction.EXECUTE));

			parallel.scene.world.modifyBlockEntity(secSourcePos, VoidTankTileEntity.class,
					te -> te.getFluidStorage().fill(lava, IFluidHandler.FluidAction.EXECUTE));

			parallel.scene.idle(15);
		}

		scene.addInstruction(parallel);

		playVoidSequence(
				scene, util, VoidTankTileEntity.class,
				.015f, 0,
				"Void Tank", "Fluids",
				sourcePos, receiverPos,
				Direction.UP, Direction.UP,
				(pos) -> {},
				(pos) -> scene.world.modifyBlockEntity(pos, VoidTankTileEntity.class,
						te -> {
							lava.setAmount(4000);
							te.getFluidStorage().setFluid(lava);
						}),
				false, true
		);

	}

	public static void voidBattery(SceneBuilder scene, SceneBuildingUtil util) {

		scene.title("void_battery", "Using Void Batteries");
		scene.showBasePlate();

		BlockPos sourcePos = util.grid.at(3, 1, 2);
		BlockPos receiverPos = util.grid.at(1, 1, 2);

		scene.world.showSection(util.select.position(sourcePos), Direction.DOWN);
		scene.idle(10);
		scene.world.showSection(util.select.position(receiverPos), Direction.DOWN);
		scene.idle(10);

		playVoidSequence(
				scene, util, VoidBatteryTileEntity.class,
				-.0475f, -.1875f,
				"Void Battery", "Energy",
				sourcePos, receiverPos,
				Direction.SOUTH, Direction.SOUTH,
				(pos) -> {},
				(pos) -> {},
				true, false
		);

	}

	private static void playVoidSequence(SceneBuilder scene,
										 SceneBuildingUtil util,
										 Class<? extends BlockEntity> beType,
										 float shift, float yOffset,
										 String blockName,
										 String transmittedName,
										 BlockPos firstPos,
										 BlockPos secondPos,
										 Direction firstDirection,
										 Direction secondDirection,
										 Consumer<BlockPos> onDisconnect,
										 Consumer<BlockPos> onConnect,
										 boolean rotate,
										 boolean isTank) {

		Selection firstBlock = util.select.position(firstPos);
		Vec3 firstVec = util.vector.blockSurface(firstPos, firstDirection);

		Selection secondBlock = util.select.position(secondPos);
		Vec3 secondVec = util.vector.blockSurface(secondPos, secondDirection);

		scene.overlay.showText(50)
				.text(blockName + " can transmit " + transmittedName + " across distances")
				.pointAt(firstVec);
		scene.idle(50);

		if (rotate) scene.rotateCameraY(-90);
		scene.addKeyframe();

		Vec3 firstBackFreq = getFirstFrequency(firstVec, firstDirection, shift, yOffset);
		Vec3 firstFrontFreq = getLastFrequency(firstVec, firstDirection, shift, yOffset);
		Vec3 firstOwner = getOwner(firstVec, firstDirection, shift, yOffset);

		Vec3 secondBackFreq = getFirstFrequency(secondVec, secondDirection, shift, yOffset);
		Vec3 secondFrontFreq = getLastFrequency(secondVec, secondDirection, shift, yOffset);

		scene.idle(10);
		scene.overlay.showFilterSlotInput(firstBackFreq, firstDirection, 100);
		scene.overlay.showFilterSlotInput(firstFrontFreq, firstDirection, 100);
		scene.idle(10);

		scene.overlay.showText(50)
				.text("Placing items in the two upper slots can specify a Frequency")
				.placeNearTarget()
				.pointAt(firstFrontFreq);
		scene.idle(60);

		ItemStack iron = new ItemStack(Items.IRON_INGOT);
		ItemStack sapling = new ItemStack(Items.OAK_SAPLING);

		showFrequency(scene, firstBlock, beType, firstFrontFreq, "FrequencyLast", Pointing.LEFT, iron);
		onDisconnect.accept(secondPos);
		showFrequency(scene, firstBlock, beType, firstBackFreq, "FrequencyFirst", Pointing.RIGHT, sapling);

		if (isTank) onConnect.accept(firstPos);

		scene.idle(30);

		scene.addKeyframe();
		scene.idle(10);
		scene.overlay.showFilterSlotInput(firstOwner, firstDirection, 100);
		scene.idle(10);

		scene.overlay.showControls(new InputWindowElement(firstOwner, Pointing.UP).rightClick(), 40);
		scene.idle(7);
		scene.world.modifyBlockEntityNBT(firstBlock, beType, nbt -> nbt.remove("Owner"));

		scene.overlay.showText(50)
				.text("Right-click the bottom slot to unclaim the " + blockName)
				.placeNearTarget()
				.pointAt(firstOwner);
		scene.idle(60);

		scene.overlay.showControls(new InputWindowElement(firstOwner, Pointing.UP).rightClick(), 40);
		scene.idle(7);
		scene.world.restoreBlocks(firstBlock);
		scene.world.modifyBlockEntityNBT(firstBlock, beType, nbt -> {
			nbt.put("FrequencyFirst", sapling.save(new CompoundTag()));
			nbt.put("FrequencyLast", iron.save(new CompoundTag()));
		});

		if (isTank) onConnect.accept(firstPos);

		scene.overlay.showText(50)
				.text("Right-click it again to re-claim it")
				.placeNearTarget()
				.pointAt(firstOwner);
		scene.idle(60);

		scene.overlay.showText(50)
				.text("If a " + blockName + " is claimed, only it's owner is able to edit it's Frequency")
				.placeNearTarget()
				.pointAt(firstVec);
		scene.idle(60);

		scene.addKeyframe();
		scene.idle(10);

		scene.overlay.showText(60)
				.text("A " + blockName + " will only receive " + transmittedName + " from " + blockName + "s with the same Frequency and Owner")
				.placeNearTarget()
				.pointAt(secondVec);
		scene.idle(70);

		showFrequency(scene, secondBlock, beType, secondFrontFreq, "FrequencyLast", Pointing.LEFT, iron);
		showFrequency(scene, secondBlock, beType, secondBackFreq, "FrequencyFirst", Pointing.RIGHT, sapling);
		onConnect.accept(secondPos);

		if (rotate) {
			scene.idle(20);
			scene.rotateCameraY(90);
			scene.idle(30);
		} else scene.idle(50);

	}

	private static void showFrequency(SceneBuilder scene,
									  Selection block,
									  Class<? extends BlockEntity> beType,
									  Vec3 slotPos,
									  String slotId,
									  Pointing pointing,
									  ItemStack item) {
		scene.overlay.showControls(new InputWindowElement(slotPos, pointing).withItem(item), 30);
		scene.idle(7);
		scene.world.modifyBlockEntityNBT(block, beType, nbt -> nbt.put(slotId, item.save(new CompoundTag())));
	}

	private static Vec3 getFirstFrequency(Vec3 faceVec, Direction face, float shift, float yOffset) {
		return switch (face) {
			case NORTH -> faceVec.add(.15625f, .15625f + yOffset, -shift);
			case EAST -> faceVec.add(shift, .15625f + yOffset, .15625f);
			case SOUTH -> faceVec.add(-.15625f, .15625f + yOffset, shift);
			case WEST -> faceVec.add(-shift, .15625f + yOffset, -.15625f);
			case UP -> faceVec.add(.15625f + yOffset, shift, -.15625f);
			case DOWN -> faceVec.add(.15625f + yOffset, -shift, .15625f);
		};
	}

	private static Vec3 getLastFrequency(Vec3 faceVec, Direction face, float shift, float yOffset) {
		return switch (face) {
			case NORTH -> faceVec.add(-.15625f, .15625f + yOffset, -shift);
			case EAST -> faceVec.add(shift, .15625f + yOffset, -.15625f);
			case SOUTH -> faceVec.add(.15625f, .15625f + yOffset, shift);
			case WEST -> faceVec.add(-shift, .15625f + yOffset, .15625f);
			case UP -> faceVec.add(.15625f + yOffset, shift, .15625f);
			case DOWN -> faceVec.add(.15625f + yOffset, -shift, -.15625f);
		};
	}

	private static Vec3 getOwner(Vec3 faceVec, Direction face, float shift, float yOffset) {
		return switch (face) {
			case NORTH -> faceVec.add(0, -.15625f + yOffset, -shift);
			case EAST -> faceVec.add(shift, -.15625f + yOffset, 0);
			case SOUTH -> faceVec.add(0, -.15625f + yOffset, shift);
			case WEST -> faceVec.add(-shift, -.15625f + yOffset, 0);
			case UP -> faceVec.add(-.15625f + yOffset, shift, 0);
			case DOWN -> faceVec.add(-.15625f + yOffset, -shift, 0);
		};
	}

}

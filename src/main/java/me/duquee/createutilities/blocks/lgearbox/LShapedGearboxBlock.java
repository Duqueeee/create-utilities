package me.duquee.createutilities.blocks.lgearbox;

import com.simibubi.create.content.contraptions.ITransformableBlock;
import com.simibubi.create.content.contraptions.StructureTransform;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.content.kinetics.gearbox.GearboxBlockEntity;
import com.simibubi.create.foundation.block.IBE;

import me.duquee.createutilities.blocks.CUTileEntities;
import net.fabricmc.fabric.api.block.BlockPickInteractionAware;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.HitResult;

public class LShapedGearboxBlock extends KineticBlock implements IBE<GearboxBlockEntity>, BlockPickInteractionAware, ITransformableBlock {

	public static final DirectionProperty FACING_1 = BlockStateProperties.HORIZONTAL_FACING;
	public static final DirectionProperty FACING_2 = DirectionProperty.create("facing_2",
			Direction.EAST, Direction.UP, Direction.WEST, Direction.DOWN);

	public LShapedGearboxBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState()
				.setValue(FACING_1, Direction.NORTH)
				.setValue(FACING_2, Direction.EAST));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING_1, FACING_2);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {

		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();

		BlockState state = defaultBlockState();
		for (Direction facing1 : FACING_1.getPossibleValues()) {

			if (!isNeighborValid(level, pos, facing1)) continue;
			state = state.setValue(FACING_1, facing1);

			for (Direction facing2 : FACING_2.getPossibleValues()) {
				if (!isNeighborValid(level, pos, getAbsolute(facing1, facing2))) continue;
				return state.setValue(FACING_2, facing2);
			}

			return state;

		}

		Direction facing1 = context.getHorizontalDirection();
		Player player = context.getPlayer();
		return state.setValue(FACING_1,
				player != null && player.isShiftKeyDown() ? facing1 : facing1.getOpposite());
	}

	private boolean isNeighborValid(Level level, BlockPos pos, Direction direction) {

		BlockPos neighborPos = pos.relative(direction);
		BlockState neighborState = level.getBlockState(neighborPos);

		if (!(neighborState.getBlock() instanceof IRotate neighbor)) return false;
		return neighbor.hasShaftTowards(level, neighborPos, neighborState, direction.getOpposite());

	}

	@Override
	public Direction.Axis getRotationAxis(BlockState state) {
		return Direction.Axis.Y;
	}

	@Override
	public ItemStack getPickedStack(BlockState state, BlockGetter view, BlockPos pos, Player player, HitResult result) {
		return new ItemStack(this);
	}

	@Override
	public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
		return hasShaftTowards(state, face);
	}

	public static boolean hasShaftTowards(BlockState state, Direction face) {
		Direction facing1 = state.getValue(FACING_1);
		return face == facing1 || face == getAbsolute(facing1, state.getValue(FACING_2));
	}

	@Override
	public BlockState getRotatedBlockState(BlockState state, Direction targetedFace) {
		return state.cycle(FACING_2);
	}

	@Override
	public BlockState transform(BlockState state, StructureTransform transform) {

		Direction facing1 = state.getValue(FACING_1);
		Direction facing2 = getAbsolute(facing1, state.getValue(FACING_2));

		facing1 = rotate(facing1, transform.rotationAxis, transform.rotation);
		facing2 = rotate(facing2, transform.rotationAxis, transform.rotation);

		if (facing1 == Direction.UP || facing1 == Direction.DOWN) {
			Direction placeHolder = facing1;
			facing1 = facing2;
			facing2 = placeHolder;
		}

		facing2 = getRelative(facing1, facing2);

		return state.setValue(FACING_1, facing1).setValue(FACING_2, facing2);
	}

	public static Direction rotate(Direction direction, Direction.Axis axis, Rotation rotation) {
		return switch (rotation) {
			case CLOCKWISE_90 -> direction.getClockWise(axis);
			case COUNTERCLOCKWISE_90 -> direction.getCounterClockWise(axis);
			case CLOCKWISE_180 -> axis == direction.getAxis() ? direction : direction.getOpposite();
			case NONE -> direction;
		};
	}

	public static Direction getAbsolute(Direction direction1, Direction direction2) {
		return rotate(direction2, Direction.Axis.Y, getRotation(direction1));
	}

	public static Direction getRelative(Direction direction1, Direction direction2) {
		return rotate(direction2, Direction.Axis.Y, getInverse(getRotation(direction1)));
	}

	public static Rotation getRotation(Direction direction1) {
		return switch (direction1) {
			case EAST -> Rotation.CLOCKWISE_90;
			case SOUTH -> Rotation.CLOCKWISE_180;
			case WEST -> Rotation.COUNTERCLOCKWISE_90;
			default -> Rotation.NONE;
		};
	}

	public static Rotation getInverse(Rotation rotation) {
		return switch (rotation) {
			case CLOCKWISE_90 -> Rotation.COUNTERCLOCKWISE_90;
			case COUNTERCLOCKWISE_90 -> Rotation.CLOCKWISE_90;
			default -> rotation;
		};
	}

	@Override
	public Class<GearboxBlockEntity> getBlockEntityClass() {
		return GearboxBlockEntity.class;
	}

	@Override
	public BlockEntityType<GearboxBlockEntity> getBlockEntityType() {
		return CUTileEntities.LSHAPED_GEARBOX.get();
	}

}

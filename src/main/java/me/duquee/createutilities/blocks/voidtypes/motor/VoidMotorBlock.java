package me.duquee.createutilities.blocks.voidtypes.motor;

import com.simibubi.create.AllShapes;

import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import me.duquee.createutilities.blocks.CUTileEntities;
import me.duquee.createutilities.blocks.voidtypes.VoidLinkBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

public class VoidMotorBlock extends DirectionalKineticBlock implements SimpleWaterloggedBlock, IBE<VoidMotorTileEntity> {

	public VoidMotorBlock(Properties properties) {
		super(properties);
		registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return AllShapes.MOTOR_BLOCK.get(state.getValue(FACING));
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState neighbourState, LevelAccessor world, BlockPos pos, BlockPos neighbourPos) {
		if (state.getValue(WATERLOGGED)) world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
		return state;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder.add(WATERLOGGED));
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {

		Direction preferred = getPreferredFacing(context);
		FluidState ifluidstate = context.getLevel().getFluidState(context.getClickedPos());

		if ((context.getPlayer() != null && context.getPlayer().isShiftKeyDown()) || preferred == null)
			return super.getStateForPlacement(context)
					.setValue(WATERLOGGED, ifluidstate.getType() == Fluids.WATER);

		return defaultBlockState().setValue(FACING, preferred)
				.setValue(WATERLOGGED, ifluidstate.getType() == Fluids.WATER);
	}

	// IRotate:

	@Override
	public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
		return face == state.getValue(FACING);
	}

	@Override
	public Direction.Axis getRotationAxis(BlockState state) {
		return state.getValue(FACING).getAxis();
	}

	@Override
	public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		super.setPlacedBy(worldIn, pos, state, placer, stack);
		if (worldIn.isClientSide()) return;
		VoidLinkBehaviour behaviour = BlockEntityBehaviour.get(worldIn, pos, VoidLinkBehaviour.TYPE);
		if (placer instanceof Player player) behaviour.setOwner(player.getGameProfile());
	}

	@Override
	public Class<VoidMotorTileEntity> getBlockEntityClass() {
		return VoidMotorTileEntity.class;
	}

	@Override
	public BlockEntityType<? extends VoidMotorTileEntity> getBlockEntityType() {
		return CUTileEntities.VOID_MOTOR.get();
	}
}

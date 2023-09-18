package me.duquee.createutilities.blocks.gearcube;

import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.content.kinetics.gearbox.GearboxBlockEntity;
import com.simibubi.create.foundation.block.IBE;
import me.duquee.createutilities.blocks.CUTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class GearcubeBlock extends KineticBlock implements IBE<GearboxBlockEntity> {


	public GearcubeBlock(Properties properties) {
		super(properties);
	}

	@Override
	public Direction.Axis getRotationAxis(BlockState state) {
		return Direction.Axis.Y;
	}

	@Override
	public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
		return true;
	}

	@Override
	public Class<GearboxBlockEntity> getBlockEntityClass() {
		return GearboxBlockEntity.class;
	}

	@Override
	public BlockEntityType<GearboxBlockEntity> getBlockEntityType() {
		return CUTileEntities.GEARCUBE.get();
	}

}

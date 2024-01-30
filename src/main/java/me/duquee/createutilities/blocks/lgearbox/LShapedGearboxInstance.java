package me.duquee.createutilities.blocks.lgearbox;

import com.jozufozu.flywheel.api.InstanceData;
import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.Material;
import com.jozufozu.flywheel.api.MaterialManager;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityInstance;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import com.simibubi.create.content.kinetics.gearbox.GearboxBlockEntity;
import com.simibubi.create.foundation.utility.Iterate;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LightLayer;

import java.util.EnumMap;
import java.util.Map;

public class LShapedGearboxInstance extends KineticBlockEntityInstance<GearboxBlockEntity> {

	protected final EnumMap<Direction, RotatingData> keys;
	protected Direction sourceFacing;

	public LShapedGearboxInstance(MaterialManager modelManager, GearboxBlockEntity tile) {
		super(modelManager, tile);

		keys = new EnumMap<>(Direction.class);

		int blockLight = world.getBrightness(LightLayer.BLOCK, pos);
		int skyLight = world.getBrightness(LightLayer.SKY, pos);
		updateSourceFacing();

		Material<RotatingData> rotatingMaterial = getRotatingMaterial();

		Direction facing1 = blockState.getValue(LShapedGearboxBlock.FACING_1);
		putShaft(rotatingMaterial, tile, facing1, blockLight, skyLight);

		Direction facing2 = LShapedGearboxBlock.getAbsolute(facing1, blockState.getValue(LShapedGearboxBlock.FACING_2));
		putShaft(rotatingMaterial, tile, facing2, blockLight, skyLight);

	}

	private void putShaft(Material<RotatingData> rotatingMaterial, GearboxBlockEntity tile,
						  Direction direction, int blockLight, int skyLight) {

		final Direction.Axis axis = direction.getAxis();

		Instancer<RotatingData> shaft = rotatingMaterial.getModel(AllPartialModels.SHAFT_HALF, blockState, direction);
		RotatingData key = shaft.createInstance();

		key.setRotationAxis(Direction.get(Direction.AxisDirection.POSITIVE, axis).step())
				.setRotationalSpeed(blockEntity.getSpeed())
				.setRotationOffset(getRotationOffset(axis)).setColor(tile)
				.setPosition(getInstancePosition())
				.setBlockLight(blockLight)
				.setSkyLight(skyLight);

		keys.put(direction, key);

	}

	protected void updateSourceFacing() {
		if (blockEntity.hasSource()) {
			BlockPos source = blockEntity.source.subtract(pos);
			sourceFacing = Direction.getNearest(source.getX(), source.getY(), source.getZ());
		} else {
			sourceFacing = null;
		}
	}

	@Override
	public void update() {
		updateSourceFacing();
		for (Map.Entry<Direction, RotatingData> key : keys.entrySet()) {
			Direction direction = key.getKey();
			Direction.Axis axis = direction.getAxis();
			updateRotation(key.getValue(), axis, blockEntity.getSpeed());
		}
	}

	@Override
	public void updateLight() {
		relight(pos, keys.values().stream());
	}

	@Override
	public void remove() {
		keys.values().forEach(InstanceData::delete);
		keys.clear();
	}
}

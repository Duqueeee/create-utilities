package me.duquee.createutilities.blocks.gearcube;

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

public class GearcubeInstance extends KineticBlockEntityInstance<GearboxBlockEntity> {

	protected final EnumMap<Direction, RotatingData> keys;
	protected Direction sourceFacing;

	public GearcubeInstance(MaterialManager modelManager, GearboxBlockEntity tile) {
		super(modelManager, tile);

		keys = new EnumMap<>(Direction.class);

		int blockLight = world.getBrightness(LightLayer.BLOCK, pos);
		int skyLight = world.getBrightness(LightLayer.SKY, pos);
		updateSourceFacing();

		Material<RotatingData> rotatingMaterial = getRotatingMaterial();

		for (Direction direction : Iterate.directions) {
			final Direction.Axis axis = direction.getAxis();

			Instancer<RotatingData> shaft = rotatingMaterial.getModel(AllPartialModels.SHAFT_HALF, blockState, direction);

			RotatingData key = shaft.createInstance();

			key.setRotationAxis(Direction.get(Direction.AxisDirection.POSITIVE, axis).step())
					.setRotationalSpeed(getSpeed(direction))
					.setRotationOffset(getRotationOffset(axis)).setColor(tile)
					.setPosition(getInstancePosition())
					.setBlockLight(blockLight)
					.setSkyLight(skyLight);

			keys.put(direction, key);
		}
	}

	private float getSpeed(Direction direction) {
		float speed = blockEntity.getSpeed();

		if (speed != 0 && sourceFacing != null) {
			if (sourceFacing.getAxis() == direction.getAxis())
				speed *= sourceFacing == direction ? 1 : -1;
			else if (sourceFacing.getAxisDirection() == direction.getAxisDirection())
				speed *= -1;
		}
		return speed;
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

			updateRotation(key.getValue(), axis, getSpeed(direction));
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

package me.duquee.createutilities.blocks.voidtypes.battery;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;

import com.simibubi.create.foundation.render.CachedBufferer;

import me.duquee.createutilities.blocks.CUPartialsModels;
import me.duquee.createutilities.blocks.voidtypes.VoidTileRenderer;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class VoidBatteryRenderer extends SafeBlockEntityRenderer<VoidBatteryTileEntity> implements VoidTileRenderer<VoidBatteryTileEntity> {

	private final SkullModelBase skullModelBase;

	public VoidBatteryRenderer(BlockEntityRendererProvider.Context context) {
		skullModelBase = new SkullModel(context.getModelSet().bakeLayer(ModelLayers.PLAYER_HEAD));
	}

	@Override
	protected void renderSafe(VoidBatteryTileEntity te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
		renderVoid(te, partialTicks, ms, buffer, light, overlay);
		renderDial(te, partialTicks, ms, buffer, light, overlay);
	}

	protected void renderDial(VoidBatteryTileEntity te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {

		BlockState state = te.getBlockState();
		VertexConsumer vb = buffer.getBuffer(RenderType.solid());

		VoidBattery battery = te.getBattery();
		float progress = (float) battery.amount / battery.capacity;

		Direction direction = state.getValue(VoidBatteryBlock.FACING);

		Vector3f dirVec = direction.step();
		dirVec.mul(.625f);

		Vector3f vec = new Vector3f(.5f, .375f, .5f);
		vec.add(dirVec);

		ms.pushPose();
		CachedBufferer.partial(CUPartialsModels.VOID_BATTERY_DIAL, state)
				.translate(vec)
				.rotateY(180 - direction.toYRot())
				.rotateZ(180 * progress)
				.light(light)
				.renderInto(ms, vb);
		ms.popPose();

	}

	@Override
	public SkullModelBase getSkullModelBase() {
		return skullModelBase;
	}

	@Override
	public boolean shouldRenderFrame(VoidBatteryTileEntity te, Direction direction) {
		return false;
	}

	@Override
	public float getFrameWidth() {
		return .0F;
	}

	@Override
	public float getFrameOffset(Direction direction) {
		return .0F;
	}
}

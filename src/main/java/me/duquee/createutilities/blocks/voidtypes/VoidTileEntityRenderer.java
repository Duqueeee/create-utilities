package me.duquee.createutilities.blocks.voidtypes;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;
import me.duquee.createutilities.voidlink.VoidLinkRenderer;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import org.joml.Matrix4f;

import java.util.function.BiFunction;
import java.util.function.Function;

public class VoidTileEntityRenderer<T extends SmartBlockEntity> extends SafeBlockEntityRenderer<T> {

	private final SkullModelBase skullModelBase;
	private final float width;
	private final Function<Direction, Float> offSet;
	private final BiFunction<T, Direction, Boolean> shouldRenderFrame;

	public VoidTileEntityRenderer(BlockEntityRendererProvider.Context context,
								  float width, Function<Direction, Float> offSet, BiFunction<T, Direction, Boolean> shouldRenderFrame) {
		skullModelBase = new SkullModel(context.getModelSet().bakeLayer(ModelLayers.PLAYER_HEAD));
		this.width = width;
		this.offSet = offSet;
		this.shouldRenderFrame = shouldRenderFrame;
	}

	@Override
	protected void renderSafe(T te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
		VoidLinkRenderer.renderOnTileEntity(te, partialTicks, ms, buffer, light, overlay, skullModelBase);
		renderPortal(te, ms.last().pose(), buffer.getBuffer(RenderType.endPortal()), width, offSet);
	}

	private void renderPortal(T te, Matrix4f pose, VertexConsumer consumer, float width, Function<Direction, Float> offSet) {

		float x = (1F - width)*0.5F;
		float z = (1F + width)*0.5F;

		for (Direction direction : Direction.values()) {

			if (!shouldRenderFrame.apply(te, direction)) continue;

			float offSetValue = offSet.apply(direction);
			switch (direction) {
				case DOWN -> renderFrame(pose, consumer, x, z, 1 - offSetValue, 1 - offSetValue, x, x, z, z);
				case UP -> renderFrame(pose, consumer, x, z, offSetValue, offSetValue, z, z, x, x);
				case NORTH -> renderFrame(pose, consumer, x, z, z, x, 1 - offSetValue, 1 - offSetValue, 1 - offSetValue, 1 - offSetValue);
				case SOUTH -> renderFrame(pose, consumer, x, z, x, z, offSetValue, offSetValue, offSetValue, offSetValue);
				case WEST -> renderFrame(pose, consumer, 1 - offSetValue, 1 - offSetValue, x, z, x, z, z, x);
				case EAST -> renderFrame(pose, consumer, offSetValue, offSetValue, z, x, x, z, z, x);
			}

		}

	}

	private void renderFrame(Matrix4f pose, VertexConsumer consumer,
							 float x0, float x1, float y0, float y1, float z0, float z1, float z2, float z3) {
		consumer.vertex(pose, x0, y0, z0).endVertex();
		consumer.vertex(pose, x1, y0, z1).endVertex();
		consumer.vertex(pose, x1, y1, z2).endVertex();
		consumer.vertex(pose, x0, y1, z3).endVertex();
	}

}

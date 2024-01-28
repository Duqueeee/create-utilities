package me.duquee.createutilities.blocks.voidtypes;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;

import me.duquee.createutilities.voidlink.VoidLinkRenderer;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;

import org.joml.Matrix4f;

public interface VoidTileRenderer<T extends SmartBlockEntity> {

	SkullModelBase getSkullModelBase();

	boolean shouldRenderFrame(T te, Direction direction);
	float getFrameWidth();
	float getFrameOffset(Direction direction);

	default void renderVoid(T te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
		VoidLinkRenderer.renderOnTileEntity(te, partialTicks, ms, buffer, light, overlay, getSkullModelBase());
		renderPortal(te, ms.last().pose(), buffer.getBuffer(RenderType.endPortal()));
	}

	private void renderPortal(T te, Matrix4f pose, VertexConsumer consumer) {

		float x = (1F - getFrameWidth())*0.5F;
		float z = (1F + getFrameWidth())*0.5F;

		for (Direction direction : Direction.values()) {

			if (!shouldRenderFrame(te, direction)) continue;

			float offSetValue = getFrameOffset(direction);
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

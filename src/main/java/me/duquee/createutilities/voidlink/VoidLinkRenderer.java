package me.duquee.createutilities.voidlink;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.simibubi.create.CreateClient;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBox;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxRenderer;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.utility.Components;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.foundation.utility.VecHelper;

import com.simibubi.create.infrastructure.config.AllConfigs;
import me.duquee.createutilities.CreateUtilities;
import me.duquee.createutilities.blocks.voidtypes.VoidLinkBehaviour;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class VoidLinkRenderer {

	public static void tick() {
		Minecraft mc = Minecraft.getInstance();
		HitResult target = mc.hitResult;
		if (!(target instanceof BlockHitResult result))
			return;

		ClientLevel world = mc.level;
		BlockPos pos = result.getBlockPos();

		VoidLinkBehaviour behaviour = BlockEntityBehaviour.get(world, pos, VoidLinkBehaviour.TYPE);
		if (behaviour == null)
			return;

		Component freq1 = Lang.translateDirect("logistics.firstFrequency");
		Component freq2 = Lang.translateDirect("logistics.secondFrequency");
		Component player = Components.translatable(CreateUtilities.ID + ".logistics.owner");

		for (int index : VoidLinkHandler.arr012) {
			AABB bb = new AABB(Vec3.ZERO, Vec3.ZERO).inflate(.25f);
			Component label = index < 2 ? (index == 0 ? freq1 : freq2) : player;
			boolean hit = behaviour.testHit(index, target.getLocation());
			ValueBoxTransform transform = behaviour.getSlot(index);

			ValueBox box = new ValueBox(label, bb, pos).passive(!hit).withColor(0x601F18);

			boolean isEmpty = index == 2 ? behaviour.getOwner() == null : behaviour.getFrequencyStack(index == 0).isEmpty();

			if (!isEmpty) box.wideOutline();
			CreateClient.OUTLINER.showValueBox(Pair.of(index, pos), box.transform(transform))
					.highlightFace(result.getDirection());

			if (!hit) continue;

			List<MutableComponent> tip = new ArrayList<>();
			if (index < 2) {
				tip.add(label.copy());
				tip.add(Lang.translateDirect(isEmpty ? "logistics.filter.click_to_set" : "logistics.filter.click_to_replace"));
			} else {
				tip.add(label.copy());
				tip.add(Components.translatable(CreateUtilities.ID +
						(isEmpty ? ".logistics.void.click_to_set_owner" : ".logistics.void.click_to_remove_owner")));
			}

			CreateClient.VALUE_SETTINGS_HANDLER.showHoverTip(tip);

		}
	}

	public static void renderOnTileEntity(SmartBlockEntity te, float partialTicks, PoseStack ms,
										  MultiBufferSource buffer, int light, int overlay, SkullModelBase skullModelBase) {

		if (te == null || te.isRemoved()) return;

		Entity cameraEntity = Minecraft.getInstance().cameraEntity;
		float max = AllConfigs.client().filterItemRenderDistance.getF();
		if (!te.isVirtual() && cameraEntity != null && cameraEntity.position()
				.distanceToSqr(VecHelper.getCenterOf(te.getBlockPos())) > (max * max))
			return;

		VoidLinkBehaviour behaviour = te.getBehaviour(VoidLinkBehaviour.TYPE);
		if (behaviour == null) return;

		for (int index : VoidLinkHandler.arr012) {
			ValueBoxTransform transform = behaviour.getSlot(index);

			if (index < 2) {

				ItemStack stack = behaviour.getFrequencyStack(index == 0);

				ms.pushPose();
				transform.transform(te.getBlockState(), ms);
				ValueBoxRenderer.renderItemIntoValueBox(stack, ms, buffer, light, overlay);
				ms.popPose();

			} else {

				GameProfile owner = behaviour.getOwner();
				if (owner == null) continue;

				ms.pushPose();

				transform.transform(te.getBlockState(), ms);
				float scale = 1.01f;
				ms.scale(scale, scale, scale);
				ms.translate(0, -.25f, 0);

				renderSkull(owner, ms, buffer, light, skullModelBase);

				ms.popPose();

			}

		}

	}

	public static void renderSkull(GameProfile owner, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, SkullModelBase model) {
		RenderType renderType = SkullBlockRenderer.getRenderType(SkullBlock.Types.PLAYER, owner);
		poseStack.scale(-1.0F, -1.0F, 1.0F);
		VertexConsumer vertexConsumer = bufferSource.getBuffer(renderType);
		model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
	}

}

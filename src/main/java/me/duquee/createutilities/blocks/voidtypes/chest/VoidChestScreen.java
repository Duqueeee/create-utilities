package me.duquee.createutilities.blocks.voidtypes.chest;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import com.simibubi.create.foundation.gui.menu.AbstractSimiContainerScreen;

import me.duquee.createutilities.CreateUtilities;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import org.jetbrains.annotations.NotNull;

public class VoidChestScreen extends AbstractSimiContainerScreen<VoidChestContainer> {

	private static final ResourceLocation TEXTURE = CreateUtilities.asResource("textures/gui/void_chest.png");

	public VoidChestScreen(VoidChestContainer container, Inventory inv, Component title) {
		super(container, inv, title);
	}

	@Override
	protected void renderBg(@NotNull PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
		RenderSystem.setShaderTexture(0, TEXTURE);
		int startX = (width - imageWidth)/2;
		int startY = (height - imageHeight)/2;
		blit(poseStack, startX, startY, 0, 0, imageWidth, imageHeight);
		font.draw(poseStack, title, startX + 8, startY + 7, 0x404040);
		font.draw(poseStack, playerInventoryTitle, startX + 8, startY + 74, 0x404040);
	}

}

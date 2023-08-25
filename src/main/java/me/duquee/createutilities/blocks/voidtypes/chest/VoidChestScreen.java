package me.duquee.createutilities.blocks.voidtypes.chest;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import com.simibubi.create.foundation.gui.menu.AbstractSimiContainerScreen;

import me.duquee.createutilities.CreateUtilities;
import net.minecraft.client.gui.GuiGraphics;
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
	protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {

		int startX = (width - imageWidth)/2;
		int startY = (height - imageHeight)/2;

		guiGraphics.blit(TEXTURE, startX, startY, 0, 0, imageWidth, imageHeight);
		guiGraphics.drawString(font, title, startX + 8, startY + 7, 0x404040);
		guiGraphics.drawString(font, playerInventoryTitle, startX + 8, startY + 74, 0x404040);

	}

}

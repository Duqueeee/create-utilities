package me.duquee.createutilities.blocks.voidtypes.chest;

import com.simibubi.create.foundation.gui.menu.AbstractSimiContainerScreen;
import me.duquee.createutilities.CreateUtilities;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class VoidChestScreen extends AbstractSimiContainerScreen<VoidChestContainer> {

	private static final ResourceLocation TEXTURE = CreateUtilities.asResource("textures/gui/void_chest.png");

	public VoidChestScreen(VoidChestContainer container, Inventory inv, Component title) {
		super(container, inv, title);
	}

	@Override
	protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
		int startX = (width - imageWidth)/2;
		int startY = (height - imageHeight)/2;
		graphics.blit(TEXTURE, startX, startY, 0, 0, imageWidth, imageHeight);
		graphics.drawString(font, title, startX + 8, startY + 7, 0x404040);
		graphics.drawString(font, playerInventoryTitle, startX + 8, startY + 74, 0x404040);
	}

}

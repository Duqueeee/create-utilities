package me.duquee.createutilities.tabs;

import me.duquee.createutilities.CreateUtilities;
import me.duquee.createutilities.blocks.CUBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class CUCreativeTabs {

	public static final CreativeModeTab BASE = new CreativeModeTab(CreateUtilities.ID + ".base") {
		@Override
		public ItemStack makeIcon() {
			return CUBlocks.VOID_MOTOR.asStack();
		}
	};

}

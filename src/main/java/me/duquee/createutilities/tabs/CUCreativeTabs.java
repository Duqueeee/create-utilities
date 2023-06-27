package me.duquee.createutilities.tabs;

import me.duquee.createutilities.CreateUtilities;
import me.duquee.createutilities.blocks.CUBlocks;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.world.item.CreativeModeTab;

public class CUCreativeTabs {

	public static final CreativeModeTab BASE = FabricItemGroupBuilder.build(
			CreateUtilities.asResource("base"),
			CUBlocks.VOID_MOTOR::asStack
	);

}

package me.duquee.createutilities.items;

import com.tterrag.registrate.util.entry.ItemEntry;

import me.duquee.createutilities.tabs.CUCreativeTabs;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.Item;

import static me.duquee.createutilities.CreateUtilities.REGISTRATE;

public class CUItems {

	public static final ItemEntry<Item>
			VOID_STEEL_INGOT = ingredient("void_steel_ingot"),
			VOID_STEEL_SHEET = ingredient("void_steel_sheet"),
			POLISHED_AMETHYST = ingredient("polished_amethyst"),
			GRAVITON_TUBE = ingredient("graviton_tube");

	static {
		ItemGroupEvents.modifyEntriesEvent(CUCreativeTabs.BASE.key()).register(content -> {
			content.accept(VOID_STEEL_INGOT);
			content.accept(VOID_STEEL_SHEET);
			content.accept(POLISHED_AMETHYST);
			content.accept(GRAVITON_TUBE);
		});
	}

	private static ItemEntry<Item> ingredient(String name) {
		return REGISTRATE.item(name, Item::new)
				.register();
	}

	public static void register() {}

}

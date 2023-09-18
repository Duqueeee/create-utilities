package me.duquee.createutilities.tabs;

import com.tterrag.registrate.util.entry.RegistryEntry;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import it.unimi.dsi.fastutil.objects.ReferenceLinkedOpenHashSet;
import me.duquee.createutilities.CreateUtilities;
import me.duquee.createutilities.blocks.CUBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedList;
import java.util.List;

import static me.duquee.createutilities.CreateUtilities.REGISTRATE;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CUCreativeTabs {
	private static final DeferredRegister<CreativeModeTab> TAB_REGISTER =
			DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateUtilities.ID);

	public static final RegistryObject<CreativeModeTab> BASE = TAB_REGISTER.register("base",
			() -> CreativeModeTab.builder()
					.title(Component.translatable("itemGroup.createutilities.base"))
					.icon(CUBlocks.VOID_MOTOR::asStack)
					.displayItems(new RegistrateDisplayItemsGenerator())
					.build());

	public static void register(IEventBus modEventBus) {
		TAB_REGISTER.register(modEventBus);
	}

	public static class RegistrateDisplayItemsGenerator implements CreativeModeTab.DisplayItemsGenerator {

		private List<Item> collectBlocks(RegistryObject<CreativeModeTab> tab) {

			List<Item> items = new ReferenceArrayList<>();
			for (RegistryEntry<Block> entry : REGISTRATE.getAll(Registries.BLOCK)) {

				if (!REGISTRATE.isInCreativeTab(entry, tab)) continue;

				Item item = entry.get().asItem();
				if (item != Items.AIR) items.add(item);

			}
			items = new ReferenceArrayList<>(new ReferenceLinkedOpenHashSet<>(items));
			return items;
		}

		private List<Item> collectItems(RegistryObject<CreativeModeTab> tab) {

			List<Item> items = new ReferenceArrayList<>();
			for (RegistryEntry<Item> entry : REGISTRATE.getAll(Registries.ITEM)) {

				if (!REGISTRATE.isInCreativeTab(entry, tab)) continue;

				Item item = entry.get();
				if (!(item instanceof BlockItem)) items.add(item);

			}
			return items;
		}

		@Override
		public void accept(CreativeModeTab.ItemDisplayParameters params, CreativeModeTab.Output output) {

			List<Item> items = new LinkedList<>();
			items.addAll(collectBlocks(BASE));
			items.addAll(collectItems(BASE));

			items.forEach(output::accept);

		}
	}
}

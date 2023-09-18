package me.duquee.createutilities.blocks.voidtypes;

import com.tterrag.registrate.builders.MenuBuilder;
import com.tterrag.registrate.util.entry.MenuEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;

import me.duquee.createutilities.blocks.voidtypes.chest.VoidChestContainer;
import me.duquee.createutilities.blocks.voidtypes.chest.VoidChestScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;

import static me.duquee.createutilities.CreateUtilities.REGISTRATE;

public class CUContainerTypes {

	public static final MenuEntry<VoidChestContainer> VOID_CHEST =
			register("void_chest", VoidChestContainer::new, () -> VoidChestScreen::new);

	private static <C extends AbstractContainerMenu, S extends Screen & MenuAccess<C>> MenuEntry<C> register(
			String name, MenuBuilder.ForgeMenuFactory<C> factory, NonNullSupplier<MenuBuilder.ScreenFactory<C, S>> screenFactory) {
		return REGISTRATE.menu(name, factory, screenFactory).register();
	}

	public static void register() {}

}

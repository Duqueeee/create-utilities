package me.duquee.createutilities.tabs;

import com.simibubi.create.AllCreativeModeTabs;

import com.simibubi.create.Create;

import me.duquee.createutilities.CreateUtilities;
import me.duquee.createutilities.blocks.CUBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

import java.util.function.Supplier;

public class CUCreativeTabs {

	public static final AllCreativeModeTabs.TabInfo BASE = register("createutilities",
			() -> FabricItemGroup.builder()
					.title(Component.translatable("itemGroup.createutilities.base"))
					.icon(() -> CUBlocks.VOID_MOTOR.asStack())
					.build());

	private static AllCreativeModeTabs.TabInfo register(String name, Supplier<CreativeModeTab> supplier) {
		ResourceLocation id = CreateUtilities.asResource(name);
		ResourceKey<CreativeModeTab> key = ResourceKey.create(Registries.CREATIVE_MODE_TAB, id);
		CreativeModeTab tab = supplier.get();
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, key, tab);
		return new AllCreativeModeTabs.TabInfo(key, tab);
	}

	public static void register() {}

}

package me.duquee.createutilities;

import com.simibubi.create.foundation.data.CreateRegistrate;

import me.duquee.createutilities.blocks.CUBlocks;
import me.duquee.createutilities.blocks.CUPartialsModels;
import me.duquee.createutilities.blocks.CUTileEntities;
import me.duquee.createutilities.blocks.voidtypes.CUContainerTypes;
import me.duquee.createutilities.blocks.voidtypes.chest.VoidChestInventoriesData;
import me.duquee.createutilities.blocks.voidtypes.tank.VoidTanksData;
import me.duquee.createutilities.items.CUItems;
import me.duquee.createutilities.blocks.voidtypes.motor.VoidMotorNetworkHandler;
import me.duquee.createutilities.networking.CUPackets;

import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(CreateUtilities.ID)
public class CreateUtilities {

	public static final String ID = "createutilities";
	public static final String NAME = "Create Utilities";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

	public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(ID);

	public static final VoidMotorNetworkHandler VOID_MOTOR_LINK_NETWORK_HANDLER = new VoidMotorNetworkHandler();
	public static VoidChestInventoriesData VOID_CHEST_INVENTORIES_DATA;
	public static VoidTanksData VOID_TANKS_DATA;

	public CreateUtilities() {
		onCtor();
	}

	public static void onCtor() {

		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		REGISTRATE.registerEventListeners(modEventBus);

		CUBlocks.register();
		CUItems.register();
		CUTileEntities.register();
		CUContainerTypes.register();

		modEventBus.addListener(CreateUtilities::init);
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> CUPartialsModels::init);

	}

	public static void init(final FMLCommonSetupEvent event) {
		CUPackets.registerPackets();
	}

	public static ResourceLocation asResource(String path) {
		return new ResourceLocation(ID, path);
	}
}

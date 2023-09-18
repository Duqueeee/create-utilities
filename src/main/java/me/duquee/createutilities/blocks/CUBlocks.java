package me.duquee.createutilities.blocks;

import com.simibubi.create.content.decoration.encasing.CasingBlock;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.BuilderTransformers;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;

import me.duquee.createutilities.blocks.voidtypes.chest.VoidChestBlock;
import me.duquee.createutilities.blocks.voidtypes.motor.VoidMotorBlock;
import me.duquee.createutilities.blocks.gearcube.GearcubeBlock;
import me.duquee.createutilities.blocks.voidtypes.tank.VoidTankBlock;
import me.duquee.createutilities.tabs.CUCreativeTabs;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;
import static me.duquee.createutilities.CreateUtilities.REGISTRATE;

public class CUBlocks {

	static {
		REGISTRATE.useCreativeTab(CUCreativeTabs.BASE);
	}

	public static final BlockEntry<CasingBlock> VOID_CASING = REGISTRATE.block("void_casing", CasingBlock::new)
			.transform(BuilderTransformers.casing(() -> CUSpriteShifts.VOID_CASING))
			.properties(p -> p.mapColor(MapColor.COLOR_BLACK))
			.properties(p -> p.strength(55.0F, 1200.0F))
			.properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
			.transform(pickaxeOnly())
			.register();

	public static final BlockEntry<VoidMotorBlock> VOID_MOTOR = REGISTRATE.block("void_motor", VoidMotorBlock::new)
			.initialProperties(SharedProperties::stone)
			.properties(p -> p.mapColor(MapColor.COLOR_BLACK))
			.properties(p -> p.strength(30F, 600.0F))
			.transform(pickaxeOnly())
			.transform(BlockStressDefaults.setNoImpact())
			.item()
			.properties(p -> p.rarity(Rarity.EPIC))
			.transform(customItemModel())
			.register();

	public static final BlockEntry<VoidChestBlock> VOID_CHEST = REGISTRATE.block("void_chest", VoidChestBlock::new)
			.initialProperties(SharedProperties::stone)
			.properties(BlockBehaviour.Properties::noOcclusion)
			.properties(p -> p.mapColor(MapColor.COLOR_BLACK))
			.properties(p -> p.strength(30F, 600.0F))
			.transform(pickaxeOnly())
			.item()
			.properties(p -> p.rarity(Rarity.EPIC))
			.transform(customItemModel())
			.register();

	public static final BlockEntry<VoidTankBlock> VOID_TANK = REGISTRATE.block("void_tank", VoidTankBlock::new)
			.initialProperties(SharedProperties::stone)
			.properties(BlockBehaviour.Properties::noOcclusion)
			.properties(p -> p.mapColor(MapColor.COLOR_BLACK))
			.properties(p -> p.strength(30F, 600.0F))
			.properties(p -> p.isRedstoneConductor((p1, p2, p3) -> true))
			.transform(pickaxeOnly())
			.item()
			.properties(p -> p.rarity(Rarity.EPIC))
			.transform(customItemModel())
			.register();

	public static final BlockEntry<GearcubeBlock> GEARCUBE = REGISTRATE.block("gearcube", GearcubeBlock::new)
			.initialProperties(SharedProperties::stone)
			.properties(BlockBehaviour.Properties::noOcclusion)
			.properties(p -> p.mapColor(MapColor.PODZOL))
			.transform(BlockStressDefaults.setNoImpact())
			.transform(axeOrPickaxe())
			.simpleItem()
			.register();

	public static void register() {}

}

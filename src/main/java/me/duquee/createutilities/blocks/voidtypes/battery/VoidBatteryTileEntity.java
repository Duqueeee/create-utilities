package me.duquee.createutilities.blocks.voidtypes.battery;

import java.util.List;

import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;

import com.simibubi.create.foundation.utility.Lang;

import com.simibubi.create.foundation.utility.LangBuilder;

import me.duquee.createutilities.CreateUtilitiesClient;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;

import net.minecraft.network.chat.Component;

import org.apache.commons.lang3.tuple.Triple;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.utility.VecHelper;

import me.duquee.createutilities.CreateUtilities;
import me.duquee.createutilities.blocks.voidtypes.VoidLinkBehaviour;
import me.duquee.createutilities.voidlink.VoidLinkSlot;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class VoidBatteryTileEntity extends SmartBlockEntity implements IHaveGoggleInformation {

	VoidLinkBehaviour link;

	public VoidBatteryTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
		super(type, pos, blockState);
	}

	public void createLink() {

		Triple<VoidLinkSlot, VoidLinkSlot, VoidLinkSlot> slots = VoidLinkSlot.makeSlots(
				index -> new VoidLinkSlot(index,
						state -> state.getValue(VoidBatteryBlock.FACING),
						VecHelper.voxelSpace(5.5F, 10.5F, -.001F)));

		link = new VoidLinkBehaviour(this, slots);
	}

	@Override
	public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
		createLink();
		behaviours.add(link);
	}

	private boolean hasPersistentData() {
		return level != null && !level.isClientSide;
	}

	private static VoidBatteryData getPersistentData() {
		return CreateUtilities.VOID_BATTERIES_DATA;
	}

	public VoidBattery getBattery() {
		return hasPersistentData() ?
				getPersistentData().computeStorageIfAbsent(link.getNetworkKey()) :
				CreateUtilitiesClient.VOID_BATTERIES.computeStorageIfAbsent(link.getNetworkKey());
	}

	@Override
	protected void read(CompoundTag tag, boolean clientPacket) {
		super.read(tag, clientPacket);
		if (clientPacket) getBattery().deserializeNBT(tag.getCompound("Battery"));
	}

	@Override
	protected void write(CompoundTag tag, boolean clientPacket) {
		if (clientPacket) tag.put("Battery", getBattery().serializeNBT());
		super.write(tag, clientPacket);
	}

	@Override
	public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {

		VoidBattery battery = getBattery();

		new LangBuilder(CreateUtilities.ID)
				.translate("tooltip.void_battery.header")
				.forGoggles(tooltip);

		new LangBuilder(CreateUtilities.ID)
				.translate("tooltip.void_battery.energy")
				.style(ChatFormatting.GRAY)
				.forGoggles(tooltip, 1);

		new LangBuilder(CreateUtilities.ID)
				.add(new LangBuilder(CreateUtilities.ID)
						.text(battery.amount+ "fe")
						.style(ChatFormatting.GOLD))
				.add(new LangBuilder(CreateUtilities.ID)
						.text(" / ")
						.style(ChatFormatting.GRAY))
				.add(new LangBuilder(CreateUtilities.ID)
						.text(battery.capacity + "fe")
						.style(ChatFormatting.DARK_GRAY))
				.forGoggles(tooltip, 1);

		return true;
	}

}

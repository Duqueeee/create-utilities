package me.duquee.createutilities.blocks.voidtypes.chest;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;

import com.simibubi.create.foundation.utility.VecHelper;
import com.simibubi.create.foundation.utility.animation.LerpedFloat;

import io.github.fabricators_of_create.porting_lib.transfer.item.ItemTransferable;
import me.duquee.createutilities.CreateUtilities;
import me.duquee.createutilities.blocks.voidtypes.VoidLinkBehaviour;
import me.duquee.createutilities.voidlink.VoidLinkSlot;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.world.level.gameevent.GameEvent;

import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class VoidChestTileEntity extends SmartBlockEntity implements MenuProvider, ItemTransferable {

	VoidLinkBehaviour link;
	VoidChestInventory inventory;

	private int openCount;
	public LerpedFloat lid = LerpedFloat.linear().startWithValue(0);

	public VoidChestTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public void createLink() {

		Triple<VoidLinkSlot, VoidLinkSlot, VoidLinkSlot> slots = VoidLinkSlot.makeSlots(
				index -> new VoidLinkSlot(index,
						state -> state.getValue(VoidChestBlock.FACING),
						VecHelper.voxelSpace(5.5F, 7.5F, .999F)) );

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

	private static VoidChestInventoriesData getPersistentData() {
		return CreateUtilities.VOID_CHEST_INVENTORIES_DATA;
	}

	@Override
	public VoidChestInventory getItemStorage(Direction side) {
		return hasPersistentData() ? getPersistentData().computeStorageIfAbsent(link.getNetworkKey()) : inventory;
	}

	public VoidChestInventory getItemStorage() {
		return getItemStorage(null);
	}

	@Override
	protected void read(CompoundTag tag, boolean clientPacket) {
		if (clientPacket) {
			inventory = new VoidChestInventory(link.getNetworkKey());
			inventory.deserializeNBT(tag.getCompound("Inventory"));
			openCount = tag.getInt("OpenCount");
		}
		super.read(tag, clientPacket);
	}

	@Override
	protected void write(CompoundTag tag, boolean clientPacket) {

		if (hasPersistentData()) tag.put("Inventory", getItemStorage().serializeNBT());
		else if (inventory != null) tag.put("Inventory", inventory.serializeNBT());

		if (clientPacket) tag.putInt("OpenCount", openCount);

		super.write(tag, clientPacket);
	}

	@Override
	public @NotNull Component getDisplayName() {
		return new TranslatableComponent("block.createutilities.void_chest");
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
		return VoidChestContainer.create(id, inventory, this);
	}

	@Override
	public void tick() {
		lid.chase(openCount > 0 ? 1 : 0, 0.1f, LerpedFloat.Chaser.LINEAR);
		lid.tickChaser();
	}

	public boolean isClosed() {
		return lid.settled() && lid.getChaseTarget() == 0;
	}

	public void startOpen(Player player) {
		if (this.openCount < 0) this.openCount = 0;

		this.openCount++;
		sendData();

		if (this.openCount == 1) {
			this.level.gameEvent(player, GameEvent.CONTAINER_OPEN, this.worldPosition);
			this.level.playSound(null, this.worldPosition, SoundEvents.CHEST_OPEN, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
		}

	}

	public void stopOpen(Player player) {
		this.openCount--;
		sendData();

		if (this.openCount <= 0) {
			this.level.gameEvent(player, GameEvent.CONTAINER_CLOSE, this.worldPosition);
			this.level.playSound(null, this.worldPosition, SoundEvents.CHEST_CLOSE, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
		}
	}

}

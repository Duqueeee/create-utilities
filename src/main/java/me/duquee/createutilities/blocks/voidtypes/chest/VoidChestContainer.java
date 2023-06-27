package me.duquee.createutilities.blocks.voidtypes.chest;

import com.simibubi.create.foundation.gui.menu.MenuBase;

import io.github.fabricators_of_create.porting_lib.transfer.item.SlotItemHandler;
import me.duquee.createutilities.blocks.voidtypes.CUContainerTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public class VoidChestContainer extends MenuBase<VoidChestTileEntity> {


	public VoidChestContainer(MenuType<?> type, int id, Inventory inv, FriendlyByteBuf extraData) {
		super(type, id, inv, extraData);
	}

	public VoidChestContainer(MenuType<?> type, int id, Inventory inv, VoidChestTileEntity te) {
		super(type, id, inv, te);
		contentHolder.startOpen(player);
	}

	public static VoidChestContainer create(int id, Inventory inv, VoidChestTileEntity te) {
		return new VoidChestContainer(CUContainerTypes.VOID_CHEST.get(), id, inv, te);
	}

	@Override
	protected VoidChestTileEntity createOnClient(FriendlyByteBuf extraData) {
		BlockPos readBlockPos = extraData.readBlockPos();
		CompoundTag readNbt = extraData.readNbt();

		ClientLevel world = Minecraft.getInstance().level;
		assert world != null;
		BlockEntity tileEntity = world.getBlockEntity(readBlockPos);
		if (tileEntity instanceof VoidChestTileEntity voidChest) {
			voidChest.read(readNbt, true);
			return voidChest;
		}

		return null;
	}

	@Override
	protected void initAndReadInventory(VoidChestTileEntity contentHolder) {}

	@Override
	protected void addSlots() {
		addChestSlots();
		addPlayerSlots(8, 85);
	}

	private void addChestSlots() {
		VoidChestInventory inventory = contentHolder.getItemStorage();
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				addSlot(new SlotItemHandler(inventory, y*9 + x, 8 + x*18, 18 + y*18));
			}
		}
	}

	@Override
	protected void saveData(VoidChestTileEntity contentHolder) {}

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		Slot clickedSlot = getSlot(index);
		if (!clickedSlot.hasItem()) return ItemStack.EMPTY;

		ItemStack stack = clickedSlot.getItem();
		int size = contentHolder.getItemStorage().getSlots();
		boolean success;
		if (index < size) {
			success = !moveItemStackTo(stack, size, slots.size(), false);
			contentHolder.getItemStorage().onContentsChanged(index);
		} else success = !moveItemStackTo(stack, 0, size - 1, false);

		return success ? ItemStack.EMPTY : stack;
	}

	@Override
	public void removed(Player player) {
		super.removed(player);
		if (!player.level.isClientSide) contentHolder.stopOpen(player);
	}
}

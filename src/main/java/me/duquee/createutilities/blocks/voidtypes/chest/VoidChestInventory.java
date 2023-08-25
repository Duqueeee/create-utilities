package me.duquee.createutilities.blocks.voidtypes.chest;

import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandlerSlot;
import me.duquee.createutilities.CreateUtilities;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;

public class VoidChestInventory extends ItemStackHandler {

	public VoidChestInventory() {
		super(27);
	}

	@Override
	protected void onContentsChanged(int slot) {
		if (CreateUtilities.VOID_CHEST_INVENTORIES_DATA != null) CreateUtilities.VOID_CHEST_INVENTORIES_DATA.setDirty();
	}

}

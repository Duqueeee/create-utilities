package me.duquee.createutilities.blocks.voidtypes.chest;

import me.duquee.createutilities.CreateUtilities;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class VoidChestInventory extends ItemStackHandler {

	public VoidChestInventory() {
		super(27);
	}

	@Override
	protected void onContentsChanged(int slot) {
		if (CreateUtilities.VOID_CHEST_INVENTORIES_DATA != null) CreateUtilities.VOID_CHEST_INVENTORIES_DATA.setDirty();
	}

	public boolean isEmpty() {
		return stacks.stream().allMatch(ItemStack::isEmpty);
	}

}

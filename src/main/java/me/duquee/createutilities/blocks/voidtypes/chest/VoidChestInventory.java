package me.duquee.createutilities.blocks.voidtypes.chest;

import me.duquee.createutilities.CreateUtilities;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import me.duquee.createutilities.blocks.voidtypes.motor.VoidMotorNetworkHandler.NetworkKey;

public class VoidChestInventory extends ItemStackHandler {

	private final NetworkKey key;

	public VoidChestInventory(NetworkKey key) {
		super(27);
		this.key = key;
	}

	@Override
	protected void onContentsChanged(int slot) {
		if (CreateUtilities.VOID_CHEST_INVENTORIES_DATA != null) CreateUtilities.VOID_CHEST_INVENTORIES_DATA.setDirty();
	}

	public boolean isEmpty() {
		return stacks.stream().allMatch(ItemStack::isEmpty);
	}

	public NetworkKey getKey() {
		return key;
	}

}

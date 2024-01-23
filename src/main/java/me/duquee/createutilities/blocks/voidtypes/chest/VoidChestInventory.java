package me.duquee.createutilities.blocks.voidtypes.chest;

import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import me.duquee.createutilities.CreateUtilities;
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

	public NetworkKey getKey() {
		return key;
	}

}

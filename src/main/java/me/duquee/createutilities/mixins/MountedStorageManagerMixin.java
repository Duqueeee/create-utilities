package me.duquee.createutilities.mixins;

import com.simibubi.create.content.contraptions.Contraption.ContraptionInvWrapper;
import com.simibubi.create.content.contraptions.MountedStorageManager;

import com.simibubi.create.foundation.fluid.CombinedTankWrapper;

import io.github.fabricators_of_create.porting_lib.transfer.TransferUtil;
import me.duquee.createutilities.blocks.voidtypes.chest.VoidChestInventory;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MountedStorageManager.class)
public class MountedStorageManagerMixin {

	@Shadow
	protected ContraptionInvWrapper inventory;
	@Shadow
	protected CombinedTankWrapper fluidInventory;

	@Inject(method = "clear()V", at = @At("HEAD"), remap = false, cancellable = true)
	private void continueForVoidChest(CallbackInfo ci) {

		for (Storage<ItemVariant> storage : inventory.parts) {
			if (storage instanceof VoidChestInventory) continue;
			if (!(storage instanceof ContraptionInvWrapper wrapper) || !isExternal(wrapper)) {
				TransferUtil.clearStorage(storage);
			}
		}

		TransferUtil.clearStorage(fluidInventory);

		ci.cancel();
	}

	private boolean isExternal(ContraptionInvWrapper wrapper) {
		return ((ContraptionInvWrapperAccessor) wrapper).getIsExternal();
	}

}

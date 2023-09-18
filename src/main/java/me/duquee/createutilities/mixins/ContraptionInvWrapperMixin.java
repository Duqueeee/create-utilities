package me.duquee.createutilities.mixins;

import com.simibubi.create.content.contraptions.Contraption.ContraptionInvWrapper;

import me.duquee.createutilities.blocks.voidtypes.chest.VoidChestInventory;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ContraptionInvWrapper.class)
public abstract class ContraptionInvWrapperMixin extends CombinedInvWrapper {

	@Final
	@Shadow
	protected boolean isExternal;

	@Inject(method = "isSlotExternal(I)Z", at = @At("HEAD"), cancellable = true, remap = false)
	private void makeVoidChestExternal(int slot, CallbackInfoReturnable<Boolean> cir) {

		if (isExternal)
			cir.setReturnValue(true);

		IItemHandlerModifiable handler = getHandlerFromIndex(getIndexForSlot(slot));
		cir.setReturnValue(handler instanceof VoidChestInventory ||
				handler instanceof ContraptionInvWrapper && ((ContraptionInvWrapper) handler).isSlotExternal(slot));
	}

}

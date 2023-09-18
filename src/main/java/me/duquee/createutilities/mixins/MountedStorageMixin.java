package me.duquee.createutilities.mixins;

import com.simibubi.create.content.contraptions.MountedStorage;
import me.duquee.createutilities.blocks.voidtypes.chest.VoidChestTileEntity;
import net.minecraft.world.level.block.entity.BlockEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MountedStorage.class)
public class MountedStorageMixin {

	@Inject(method = "addStorageToWorld(Lnet/minecraft/world/level/block/entity/BlockEntity;)V", at = @At("HEAD"), cancellable = true, remap = false)
	private void returnForVoidChest(BlockEntity te, CallbackInfo ci) {
		if (te instanceof VoidChestTileEntity) ci.cancel();
	}

}

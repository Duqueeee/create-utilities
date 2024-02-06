package me.duquee.createutilities.mixins;

import com.simibubi.create.content.contraptions.MountedStorage;

import com.simibubi.create.foundation.utility.NBTHelper;

import me.duquee.createutilities.CreateUtilities;
import me.duquee.createutilities.blocks.voidtypes.chest.VoidChestInventory;
import me.duquee.createutilities.blocks.voidtypes.chest.VoidChestTileEntity;
import me.duquee.createutilities.blocks.voidtypes.motor.VoidMotorNetworkHandler.NetworkKey;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;

import net.minecraftforge.items.ItemStackHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MountedStorage.class)
public class MountedStorageMixin {

	@Shadow
	ItemStackHandler handler;
	@Shadow boolean valid;
	@Shadow boolean noFuel;

	@Inject(method = "addStorageToWorld(Lnet/minecraft/world/level/block/entity/BlockEntity;)V", at = @At("HEAD"), cancellable = true, remap = false)
	private void returnForVoidChest(BlockEntity te, CallbackInfo ci) {
		if (te instanceof VoidChestTileEntity) ci.cancel();
	}

	@Inject(method = "serialize()Lnet/minecraft/nbt/CompoundTag;", at = @At("HEAD"), cancellable = true, remap = false)
	private void serializeVoidChest(CallbackInfoReturnable<CompoundTag> cir) {

		if (!(handler instanceof VoidChestInventory voidChest)) return;

		if (!valid) {
			cir.setReturnValue(null);
			return;
		}

		CompoundTag tag = handler.serializeNBT();
		if (noFuel) NBTHelper.putMarker(tag, "NoFuel");

		NBTHelper.putMarker(tag, "VoidChest");
		tag.put("NetworkKey", voidChest.getKey().serialize());

		cir.setReturnValue(tag);

	}

	@Inject(method = "deserialize(Lnet/minecraft/nbt/CompoundTag;)Lcom/simibubi/create/content/contraptions/MountedStorage;", at = @At("HEAD"), cancellable = true, remap = false)
	private static void deserializeVoidChest(CompoundTag nbt, CallbackInfoReturnable<MountedStorage> cir) {

		if (nbt == null) return;
		if (!nbt.contains("VoidChest")) return;

		MountedStorageAccessor storage = (MountedStorageAccessor) new MountedStorage(null);
		NetworkKey key = NetworkKey.deserialize(nbt.getCompound("NetworkKey"));
		VoidChestInventory voidChest = CreateUtilities.VOID_CHEST_INVENTORIES_DATA.computeStorageIfAbsent(key);
		storage.setHandler(voidChest);
		storage.setValid(true);
		storage.setNoFuel(nbt.contains("NoFuel"));

		cir.setReturnValue((MountedStorage) storage);

	}

	@Inject(method = "canUseAsStorage(Lnet/minecraft/world/level/block/entity/BlockEntity;)Z", at = @At("HEAD"), cancellable = true, remap = false)
	private static void canUseVoidChestAsStorage(BlockEntity be, CallbackInfoReturnable<Boolean> cir) {
		if (be instanceof VoidChestTileEntity)
			cir.setReturnValue(true);
	}

}

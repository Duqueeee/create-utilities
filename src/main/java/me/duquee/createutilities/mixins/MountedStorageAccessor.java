package me.duquee.createutilities.mixins;

import com.simibubi.create.content.contraptions.MountedStorage;

import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MountedStorage.class)
public interface MountedStorageAccessor {
	@Accessor void setHandler(ItemStackHandler handler);
	@Accessor void setValid(boolean valid);
	@Accessor void setNoFuel(boolean noFuel);
}

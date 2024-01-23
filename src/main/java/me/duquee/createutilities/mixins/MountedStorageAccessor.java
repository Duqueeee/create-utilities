package me.duquee.createutilities.mixins;

import com.simibubi.create.content.contraptions.MountedStorage;

import net.minecraftforge.items.ItemStackHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MountedStorage.class)
public interface MountedStorageAccessor {
	@Accessor void setHandler(ItemStackHandler handler);
	@Accessor void setValid(boolean valid);
	@Accessor void setNoFuel(boolean noFuel);
}

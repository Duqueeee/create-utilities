package me.duquee.createutilities.mixins;

import com.simibubi.create.content.contraptions.Contraption.ContraptionInvWrapper;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ContraptionInvWrapper.class)
public interface ContraptionInvWrapperAccessor {
	@Accessor
	boolean getIsExternal();
}

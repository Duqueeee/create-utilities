package me.duquee.createutilities.blocks.voidtypes.motor;

import com.mojang.authlib.GameProfile;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import me.duquee.createutilities.CreateUtilities;
import me.duquee.createutilities.blocks.voidtypes.VoidLinkBehaviour;

import me.duquee.createutilities.voidlink.VoidLinkSlot;
import net.minecraft.core.BlockPos;

import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Set;

public class VoidMotorLinkBehaviour extends VoidLinkBehaviour {

	public VoidMotorLinkBehaviour(SmartBlockEntity te,
								  Triple<VoidLinkSlot, VoidLinkSlot, VoidLinkSlot> slots) {
		super(te, slots);
	}

	@Override
	public void initialize() {
		super.initialize();
		if (getWorld().isClientSide) return;
		getHandler().addToNetwork(getWorld(), this);
	}

	@Override
	public void unload() {
		super.unload();
		if (getWorld().isClientSide) return;
		getHandler().removeFromNetwork(getWorld(), this);
	}

	public Set<BlockPos> getNetwork() {
		return getHandler().getNetworkOf(getWorld(), this);
	}

	@Override
	public void setOwner(@Nullable GameProfile owner) {
		super.setOwner(owner);
		if (!Objects.equals(getOwner(), owner)) {
			getHandler().removeFromNetwork(getWorld(), this);
		}
	}

	@Override
	protected void onJoinNetwork() {
		getHandler().addToNetwork(getWorld(), this);
	}

	@Override
	protected void onLeaveNetwork() {
		getHandler().removeFromNetwork(getWorld(), this);
	}

	private VoidMotorNetworkHandler getHandler() {
		return CreateUtilities.VOID_MOTOR_LINK_NETWORK_HANDLER;
	}

}

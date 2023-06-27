package me.duquee.createutilities.voidlink;

import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.utility.RaycastHelper;

import me.duquee.createutilities.blocks.voidtypes.VoidLinkBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public class VoidLinkHandler {

	public static final int[] arr012 = {0, 1, 2};

	public static InteractionResult onBlockActivated(Player player, Level world, InteractionHand hand, BlockHitResult blockRayTraceResult) {
		BlockPos pos = blockRayTraceResult.getBlockPos();

		if (player.isShiftKeyDown() || player.isSpectator())
			return InteractionResult.PASS;

		VoidLinkBehaviour behaviour = BlockEntityBehaviour.get(world, pos, VoidLinkBehaviour.TYPE);
		if (behaviour == null)
			return InteractionResult.PASS;
		if (!behaviour.canInteract(player))
			return InteractionResult.PASS;

		ItemStack heldItem = player.getItemInHand(hand);
		BlockHitResult ray = RaycastHelper.rayTraceRange(world, player, 10);
		if (ray == null)
			return InteractionResult.PASS;
		if (AllItems.WRENCH.isIn(heldItem))
			return InteractionResult.PASS;

		for (int index : arr012) {
			if (!behaviour.testHit(index, ray.getLocation())) continue;
			if (!world.isClientSide) {
				if (index < 2) {
					behaviour.setFrequency(index == 0, heldItem);
				} else {
					behaviour.setOwner(behaviour.getOwner() == null ? player.getGameProfile() : null);
				}
			}
			world.playSound(null, pos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, .25f, .1f);
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}

}

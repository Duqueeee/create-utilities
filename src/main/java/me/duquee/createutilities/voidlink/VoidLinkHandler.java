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
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class VoidLinkHandler {

	public static final int[] arr012 = {0, 1, 2};

	@SubscribeEvent
	public static void onBlockActivated(PlayerInteractEvent.RightClickBlock event) {

		Level world = event.getWorld();
		BlockPos pos = event.getPos();
		Player player = event.getPlayer();
		InteractionHand hand = event.getHand();

		if (player.isShiftKeyDown() || player.isSpectator())
			return;

		VoidLinkBehaviour behaviour = BlockEntityBehaviour.get(world, pos, VoidLinkBehaviour.TYPE);
		if (behaviour == null)
			return;
		if (!behaviour.canInteract(player))
			return;

		ItemStack heldItem = player.getItemInHand(hand);
		BlockHitResult ray = RaycastHelper.rayTraceRange(world, player, 10);
		if (ray == null)
			return;
		if (AllItems.WRENCH.isIn(heldItem))
			return;

		for (int index : arr012) {
			if (!behaviour.testHit(index, ray.getLocation())) continue;
			if (!world.isClientSide) {
				if (index < 2) {
					behaviour.setFrequency(index == 0, heldItem);
				} else {
					behaviour.setOwner(behaviour.getOwner() == null ? player.getGameProfile() : null);
				}
			}
			event.setCanceled(true);
			event.setCancellationResult(InteractionResult.SUCCESS);
			world.playSound(null, pos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, .25f, .1f);
		}
	}

}

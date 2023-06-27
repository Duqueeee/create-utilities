package me.duquee.createutilities.events;

import me.duquee.createutilities.voidlink.VoidLinkRenderer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;

public class ClientEvents {

	public static void onTick(Minecraft client) {
		if (!isGameActive())
			return;

		VoidLinkRenderer.tick();
	}

	protected static boolean isGameActive() {
		return !(Minecraft.getInstance().level == null || Minecraft.getInstance().player == null);
	}

	public static void register() {
		ClientTickEvents.END_CLIENT_TICK.register(ClientEvents::onTick);
	}

}

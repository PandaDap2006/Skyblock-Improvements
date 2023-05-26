package me.focusdev.sbi.client.gui.component;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.resources.ResourceLocation;

public class CommandButton extends ImageButton {
	@Override
	public void playDownSound(SoundManager soundManager) {
	}

	public CommandButton(int x, int y, int scaleX, int scaleY, int xTex, int yTex, ResourceLocation textureLocation, String command) {
		super(x, y, scaleX, scaleY, xTex, yTex, 20, textureLocation, 128, 128, button -> {
			Minecraft minecraft = Minecraft.getInstance();
			LocalPlayer player = minecraft.player;

			if (player != null) {
				player.connection.sendCommand(command);
			}
		});
	}
}

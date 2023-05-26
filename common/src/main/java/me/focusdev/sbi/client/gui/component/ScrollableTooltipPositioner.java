package me.focusdev.sbi.client.gui.component;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import org.joml.Math;
import org.joml.Vector2i;
import org.joml.Vector2ic;

public class ScrollableTooltipPositioner implements ClientTooltipPositioner {
	public static int scrollPosition = 0;

	@Override
	public Vector2ic positionTooltip(Screen screen, int mouseX, int mouseY, int width, int height) {
		int screenWidth = screen.width;
		int screenHeight = screen.height;

		int x = mouseX + 10;
		int y = mouseY - 5;

		if (y >= screenHeight-height-6)
			y = screenHeight-height-6;

		scrollPosition = Math.clamp(0, (height+12)-screenHeight, scrollPosition);
		if (height > screenHeight)
			y += scrollPosition;

		if (x+width+4 > screenWidth)
			x -= width+20;

		return new Vector2i(x, y);
	}
}

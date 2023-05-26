package me.focusdev.sbi.client.gui.component;

import com.mojang.blaze3d.vertex.PoseStack;
import org.joml.Vector3f;

public abstract class GridItem {
	public int x = 0;
	public int y = 0;
	public int width = 0;
	public int height = 0;
	public int index = 0;

	public abstract void render(PoseStack poseStack, int mouseX, int mouseY);
	public abstract void clicked(double mouseX, double mouseY, int button);

	public boolean isHovered(PoseStack poseStack, int mouseX, int mouseY) {
		Vector3f translation = poseStack.last().pose().getTranslation(new Vector3f());
		return (mouseX > x && mouseX < x+width) && (mouseY + translation.y > y && mouseY + translation.y < y+height);
	}
}

package me.focusdev.sbi.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.Style;
import org.jetbrains.annotations.Nullable;

public class CustomChatHud extends ChatComponent {
	public CustomChatHud(Minecraft minecraft) {
		super(minecraft);
	}

	int chatY = 40;

	@Override
	public void render(PoseStack poseStack, int i, int j, int k) {
		poseStack.pushPose();
		poseStack.translate(0, -chatY, 0);
		super.render(poseStack, i, j, k);
		poseStack.popPose();
	}

	@Nullable
	@Override
	public Style getClickedComponentStyleAt(double d, double e) {
		return super.getClickedComponentStyleAt(d, e+chatY);
	}
}

package me.focusdev.sbi.client.gui.component;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import me.focusdev.sbi.Skyblock;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Math;
import org.joml.Vector2i;

import java.awt.*;

public class ProgressBar {
	private final ResourceLocation resourceLocation;
	private final Vector2i uvPos;
	private final Vector2i uvSize;
	private Color color;
	private float amount = 1f;

	public ProgressBar(ResourceLocation resourceLocation, Vector2i uvPos, Vector2i uvSize) {
		this.resourceLocation = resourceLocation;
		this.uvPos = uvPos;
		this.uvSize = uvSize;
	}

	public void render(PoseStack poseStack, Vector2i position, Vector2i size) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, resourceLocation);
		RenderSystem.setShaderColor((float) color.getRed()/255, (float) color.getGreen()/255, (float) color.getBlue()/255, (float) color.getAlpha()/255);

		GuiComponent.blit(poseStack,
				position.x, position.y,
				uvPos.x, uvPos.y,
				size.x/2, size.y
		);
		GuiComponent.blit(poseStack,
				position.x+(size.x/2), position.y,
				uvPos.x+uvSize.x-size.x/2, uvPos.y,
				size.x/2, size.y
		);

		GuiComponent.blit(poseStack,
				position.x, position.y,
				uvPos.x, uvPos.y+uvSize.y,
				(int) Math.clamp(0, (float) size.x /2, size.x*amount), size.y
		);
		GuiComponent.blit(poseStack,
				position.x+(size.x/2), position.y,
				uvPos.x+uvSize.x-size.x/2, uvPos.y+uvSize.y,
				(int) Math.clamp(0, (float) size.x /2, size.x*amount-((float) size.x /2)), size.y
		);
	}

	public void setProgress(float amount) {
		this.amount = amount;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}

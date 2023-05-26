package me.focusdev.sbi.client.gui.component;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import me.focusdev.sbi.client.gui.menu.AuctionMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractScrollWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import org.joml.Math;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Grid<I extends GridItem> extends AbstractScrollWidget {
	private final int itemWidth;
	private final int itemHeight;
	@NotNull
	public List<I> items = new ArrayList<>();

	public Grid(int x, int y, int width, int height, int itemWidth, int itemHeight) {
		super(x, y, width, height, Component.empty());
		this.itemWidth = itemWidth;
		this.itemHeight = itemHeight;
	}

	@Override
	protected void renderContents(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
		Vector3f translation = poseStack.last().pose().getTranslation(new Vector3f());
		for (I item : this.items) {
			if ((item.y + item.height - translation.y) > this.getY() && item.y - translation.y < (this.getY() + this.height))
				item.render(poseStack, mouseX, mouseY);
		}
	}

	public void updateContents() {
		for (int y = 0; y < this.items.size()/getColumns(); y++) {
			for (int x = 0; x < getColumns(); x++) {
				GridItem item = this.items.get(x+y*getColumns());
				item.x = this.getX()+x*this.itemWidth;
				item.y = this.getY()+y*this.itemHeight;
				item.width = this.itemWidth;
				item.height = this.itemHeight;
				item.index = x+y*getColumns();
			}
		}
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		for (I item : this.items) {
			if ((mouseX > item.x && mouseX < item.x+item.width) &&
					(mouseY + this.scrollAmount() > item.y && mouseY + this.scrollAmount() < item.y+item.height))
				item.clicked(mouseX, mouseY + this.scrollAmount(), button);
		}
		return super.mouseClicked(mouseX, mouseY, button);
	}

	public int getColumns() {
		return this.width/this.itemWidth;
	}

	@Override
	protected int getInnerHeight() {
		return (this.items.size()/getColumns()) * this.itemHeight;
	}

	@Override
	protected boolean scrollbarVisible() {
		return getInnerHeight() > this.height;
	}

	@Override
	protected double scrollRate() {
		return 20;
	}

	@Override
	protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

	}

	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
		if (this.visible) {
			enableScissor(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height);
			poseStack.pushPose();
			poseStack.translate(0.0, -this.scrollAmount(), 0.0);
			this.renderContents(poseStack, mouseX, mouseY, partialTick);
			poseStack.popPose();
			disableScissor();
			this.renderDecorations(poseStack);
		}
	}

	@Override
	protected void renderDecorations(PoseStack poseStack) {
		if (this.scrollbarVisible()) {
			int x = this.getX()+this.width+1;
			int scrollBarHeight = Math.clamp(30, 256, this.height*(this.height/this.getInnerHeight()));
			int y = this.getY();

			double scrollAmount = (this.height-scrollBarHeight)*(scrollAmount()/this.getInnerHeight());

			poseStack.pushPose();
			poseStack.translate(0, scrollAmount, 0);
			RenderSystem.setShaderTexture(0, AuctionMenu.AuctionItem.WIDGET_LOCATION);
			blit(poseStack, x, y,
					251, 0,
					5, scrollBarHeight/2);

			blit(poseStack, x, y+scrollBarHeight/2,
					251, 256-scrollBarHeight/2,
					5, scrollBarHeight/2);
			poseStack.popPose();
		}
	}
}

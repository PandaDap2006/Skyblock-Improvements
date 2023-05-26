package me.focusdev.sbi.client.gui.menu;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import me.focusdev.sbi.Skyblock;
import me.focusdev.sbi.api.AuctionApi;
import me.focusdev.sbi.client.gui.component.Grid;
import me.focusdev.sbi.client.gui.component.GridItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.joml.Vector2i;

public class AuctionMenu extends Screen {
	public static final ResourceLocation WIDGET_LOCATION = new ResourceLocation(Skyblock.MODID, "textures/gui/container/auction.png");

	Vector2i widgetSize = new Vector2i(256, 234);
	Vector2i widgetPosition = new Vector2i(0, 0);

	Grid<AuctionItem> auctionGrid;

	public AuctionMenu() {
		super(Component.empty());
	}

	@Override
	protected void init() {
		widgetPosition = new Vector2i(
				Minecraft.getInstance().getWindow().getGuiScaledWidth()/2 - widgetSize.x/2,
				Minecraft.getInstance().getWindow().getGuiScaledHeight()/2 - widgetSize.y/2
		);

		this.auctionGrid = this.addRenderableWidget(new Grid<>(widgetPosition.x+10, widgetPosition.y+40,
				236, 185, 236/2, 40));

		AuctionApi.products.forEach(product -> this.auctionGrid.items.add(new AuctionItem(product)));
		this.auctionGrid.updateContents();
	}

	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
		renderBackground(poseStack);
		renderBG(poseStack);
		super.render(poseStack, mouseX, mouseY, partialTick);
	}

	private void renderBG(PoseStack poseStack) {
		RenderSystem.setShaderTexture(0, WIDGET_LOCATION);
		blit(poseStack, widgetPosition.x, widgetPosition.y, 0, 0, widgetSize.x, widgetSize.y);
	}

	public static class AuctionItem extends GridItem {
		public static final ResourceLocation WIDGET_LOCATION = new ResourceLocation(Skyblock.MODID, "textures/gui/container/auction_widgets.png");
		private final AuctionApi.Product product;

		public AuctionItem(AuctionApi.Product product) {
			this.product = product;
		}

		@Override
		public void render(PoseStack poseStack, int mouseX, int mouseY) {
			RenderSystem.setShaderTexture(0, WIDGET_LOCATION);
			blit(poseStack, this.x, this.y, 0, 0, this.width, this.height);

			Component name = product.item.getHoverName();
			GuiComponent.drawString(poseStack, Minecraft.getInstance().font, name, this.x+22, this.y+3, 0xFFFFFF);

			Minecraft.getInstance().getItemRenderer().renderGuiItem(poseStack, product.item, this.x+3, this.y+3);

			if (isHovered(poseStack, mouseX, mouseY))
				fillGradient(poseStack, x, y, x+width, y+height, -2130706433, -2130706433, 0);
		}

		@Override
		public void clicked(double mouseX, double mouseY, int button) {
		}
	}
}

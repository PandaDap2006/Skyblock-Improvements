package me.focusdev.sbi.client.gui;

import me.focusdev.sbi.Skyblock;
import me.focusdev.sbi.client.gui.component.CommandButton;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class CustomInventoryScreen extends InventoryScreen {
	public static final ResourceLocation WIDGETS_LOCATION = new ResourceLocation(Skyblock.MODID, "textures/gui/inventory_buttons.png");

	public CustomInventoryScreen(Player player) {
		super(player);
	}

	@Override
	protected void init() {
		createExtraButton();

		super.init();
	}

	void createExtraButton() {
		int centerX = this.minecraft.getWindow().getGuiScaledWidth()/2;
		int centerY = this.minecraft.getWindow().getGuiScaledHeight()/2;
		int x = centerX+90;
		int y = centerY-80;

		this.addRenderableWidget(new CommandButton(x, y, 20, 20, 60, 0, WIDGETS_LOCATION, "warp"));
		this.addRenderableWidget(new CommandButton(x, y+22, 20, 20, 0, 0, WIDGETS_LOCATION, "craft"));
		this.addRenderableWidget(new CommandButton(x, y+22*2, 20, 20, 20, 0, WIDGETS_LOCATION, "storage"));
		this.addRenderableWidget(new CommandButton(x, y+22*3, 20, 20, 40, 0, WIDGETS_LOCATION, "wardrobe"));
		this.addRenderableWidget(new CommandButton(x, y+22*4, 20, 20, 80, 0, WIDGETS_LOCATION, "skills"));
		this.addRenderableWidget(new CommandButton(x, y+22*5, 20, 20, 100, 0, WIDGETS_LOCATION, "collection"));
	}
}

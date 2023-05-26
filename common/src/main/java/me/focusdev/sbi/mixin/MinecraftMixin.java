package me.focusdev.sbi.mixin;

import me.focusdev.sbi.client.gui.CustomInventoryScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
	@ModifyVariable(method = "setScreen", at = @At("HEAD"), ordinal = 0, argsOnly = true)
	private Screen setScreen(Screen screen) {
		if (screen instanceof InventoryScreen) {
			return new CustomInventoryScreen(Minecraft.getInstance().player);
		}

//		if (screen instanceof ContainerScreen containerScreen) {
//			if (containerScreen.getTitle().getString().equalsIgnoreCase("craft item")) {
//				return new CustomMenu(containerScreen.getMenu(), containerScreen.getTitle());
//			}
//		}

		return screen;
	}
}

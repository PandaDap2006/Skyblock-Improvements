package me.focusdev.sbi.mixin.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.architectury.event.events.client.ClientGuiEvent;
import me.focusdev.sbi.client.gui.CustomChatHud;
import me.focusdev.sbi.client.gui.DungeonHud;
import me.focusdev.sbi.client.gui.HudGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class InGameHudMixin {
	@Mutable
	@Shadow @Final private ChatComponent chat;

	ClientGuiEvent.RenderHud[] overlays = new ClientGuiEvent.RenderHud[] {new HudGui(), new DungeonHud()};

	@Inject(method = "render", at = @At("HEAD"))
	public void render(PoseStack poseStack, float partialTick, CallbackInfo ci) {
		for (ClientGuiEvent.RenderHud overlay : overlays) {
			overlay.renderHud(poseStack, partialTick);
		}
	}

	@Inject(method = "renderPlayerHealth", at = @At("HEAD"), cancellable = true)
	public void renderHealth(PoseStack poseStack, CallbackInfo ci) {
		ci.cancel();
	}

	@Inject(method = "renderVehicleHealth", at = @At("HEAD"), cancellable = true)
	public void renderVehicleHealth(PoseStack poseStack, CallbackInfo ci) {
		ci.cancel();
	}

	@Inject(method = "setOverlayMessage", at = @At("HEAD"), cancellable = true)
	public void getOverlayText(Component component, boolean bl, CallbackInfo ci) {
		HudGui.update(component.getString());
		ci.cancel();
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void setCustomChat(Minecraft minecraft, ItemRenderer itemRenderer, CallbackInfo ci) {
		this.chat = new CustomChatHud(minecraft);
	}
}

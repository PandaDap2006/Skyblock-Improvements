package me.focusdev.sbi.mixin.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import me.focusdev.sbi.Skyblock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractContainerScreen.class)
public abstract class AbstractContainerScreenMixin {
	private static final ResourceLocation widgets = new ResourceLocation(Skyblock.MODID, "textures/gui/container_widgets.png");

	@Inject(method = "renderSlot", at = @At("HEAD"), cancellable = true)
	public void renderSlotBackground(PoseStack poseStack, Slot slot, CallbackInfo ci) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, widgets);
		RenderSystem.setShaderColor(1, 1, 1, 1);

		if (slot.getItem().getHoverName().getString().isBlank()) {
			GuiComponent.blit(poseStack, slot.x - 1, slot.y - 1, 0, 0, 18, 18);
			ci.cancel();
		}
	}

	@Inject(method = "isHovering(Lnet/minecraft/world/inventory/Slot;DD)Z", at = @At("HEAD"), cancellable = true)
	public void renderSlotHighlight(Slot slot, double mouseX, double mouseY, CallbackInfoReturnable<Boolean> cir) {
		if (slot.getItem().getHoverName().getString().isBlank())
			cir.setReturnValue(false);
	}
}

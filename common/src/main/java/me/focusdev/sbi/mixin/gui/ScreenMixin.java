package me.focusdev.sbi.mixin.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import me.focusdev.sbi.api.BazaarApi;
import me.focusdev.sbi.client.gui.component.ScrollableTooltipPositioner;
import me.focusdev.sbi.utils.ItemUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(Screen.class)
public abstract class ScreenMixin extends AbstractContainerEventHandler {
	@Shadow @Nullable protected Minecraft minecraft;

	@Inject(method = "renderTooltip(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/item/ItemStack;II)V", at = @At("HEAD"), cancellable = true)
	public void renderTooltip(PoseStack poseStack, ItemStack itemStack, int i, int j, CallbackInfo ci) {
		if (itemStack.getHoverName().getString().isBlank()) {
			ci.cancel();
		}
	}

	private int scrollPosition = 0;
	@ModifyVariable(method = "renderTooltipInternal",
			at = @At("HEAD"), ordinal = 0, argsOnly = true)
	private ClientTooltipPositioner setTooltipScroll(ClientTooltipPositioner positioner) {
		return new ScrollableTooltipPositioner();
	}

	@Override
	public boolean mouseScrolled(double d, double e, double f) {
		ScrollableTooltipPositioner.scrollPosition += Minecraft.getInstance().font.lineHeight*f;
		return super.mouseScrolled(d, e, f);
	}

	@Inject(method = "getTooltipFromItem", at = @At("HEAD"), cancellable = true)
	public void renderTooltipLines(ItemStack itemStack, CallbackInfoReturnable<List<Component>> cir) {
		Minecraft minecraft = Minecraft.getInstance();
		List<Component> lines = new ArrayList<>(itemStack.getTooltipLines(minecraft.player,
				Minecraft.getInstance().options.advancedItemTooltips ? TooltipFlag.ADVANCED : TooltipFlag.NORMAL));

		String itemID = ItemUtils.getItemID(itemStack);
		if (BazaarApi.products.containsKey(itemID)) {
			BazaarApi.Product product = BazaarApi.products.get(itemID);
			if (lines.size() > 1)
				lines.add(Component.empty());
			lines.addAll(product.getLore(itemStack.getCount() > 1 ? itemStack.getCount() : itemStack.getMaxStackSize()));
		}
//		lines.addAll(AuctionApi.getLore(itemID));

		cir.setReturnValue(lines);
	}
}

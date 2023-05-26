package me.focusdev.sbi.mixin.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.ArmorStandArmorModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ArmorStandRenderer.class)
public abstract class ArmorstandRendererMixin extends LivingEntityRenderer<ArmorStand, ArmorStandArmorModel> {
	public ArmorstandRendererMixin(EntityRendererProvider.Context context, ArmorStandArmorModel entityModel, float f) {
		super(context, entityModel, f);
	}

	@Override
	protected void renderNameTag(ArmorStand entity, Component component, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
		if (entity.isInvisible() && entity.isCustomNameVisible() && entity.hasCustomName()) {
			double d = this.entityRenderDispatcher.distanceToSqr(entity);
			if (d > 4096.0) {
				return;
			}
			poseStack.pushPose();
			poseStack.translate(0.0f, 2, 0.0f);
			poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
			poseStack.scale(-0.025f, -0.025f, 0.025f);
			Matrix4f matrix4f = poseStack.last().pose();
			float g = Minecraft.getInstance().options.getBackgroundOpacity(0.25f);
			int k = (int) (g * 255.0f) << 24;
			Font font = this.getFont();
			float h = (float) -font.width(component) / 2;
			font.drawInBatch(component, h, (float) 0, 0x20FFFFFF, false, matrix4f, multiBufferSource, Font.DisplayMode.NORMAL, k, i);
			poseStack.popPose();
		} else {
			super.renderNameTag(entity, component, poseStack, multiBufferSource, i);
		}
	}
}

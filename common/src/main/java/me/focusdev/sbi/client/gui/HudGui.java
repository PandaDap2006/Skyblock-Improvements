package me.focusdev.sbi.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.architectury.event.events.client.ClientGuiEvent;
import me.focusdev.sbi.Skyblock;
import me.focusdev.sbi.client.gui.component.ProgressBar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.StringUtils;
import org.joml.Vector2i;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HudGui implements ClientGuiEvent.RenderHud {
	private static final ResourceLocation GUI_BARS_LOCATION = new ResourceLocation(Skyblock.MODID, "textures/gui/hud.png");

	final ProgressBar healthBar;
	final ProgressBar manaBar;
	final ProgressBar skillBar;

	public static int health = 100;
	public static int maxHealth = 100;

	public static int mana = 100;
	public static int maxMana = 100;

	public static String skillText = "";
	public static int skill = 100;
	public static int maxSkill = 100;
	public static int skillDelay = 5;
	public static float skillTime = 0;
	public static String skillSuffix = "";

	public HudGui() {
		this.healthBar = new ProgressBar(GUI_BARS_LOCATION, new Vector2i(0, 0), new Vector2i(256, 5));
		this.healthBar.setColor(new Color(255, 0, 0, 255));

		this.manaBar = new ProgressBar(GUI_BARS_LOCATION, new Vector2i(0, 0), new Vector2i(256, 5));
		this.manaBar.setColor(new Color(70, 70, 255, 255));

		this.skillBar = new ProgressBar(GUI_BARS_LOCATION, new Vector2i(0, 0), new Vector2i(256, 5));
		this.skillBar.setColor(new Color(50, 255, 50, 255));
	}


	@Override
	public void renderHud(PoseStack poseStack, float tickDelta) {
		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.player == null)
			return;
		int sWidth = minecraft.getWindow().getGuiScaledWidth();
		int sHeight = minecraft.getWindow().getGuiScaledHeight();

		int spacing = 7*2;
		int x = sWidth/2;
		int y = sHeight - 36;
		int width = 182/2-spacing;

		RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
		RenderSystem.setShaderTexture(0, GUI_BARS_LOCATION);

		DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
		symbols.setGroupingSeparator(',');
		DecimalFormat formatter = new DecimalFormat("###,###", symbols);

		// Health
		int[] health = new int[] {HudGui.health, HudGui.maxHealth};
		float healthProcent = minecraft.player.getHealth() / minecraft.player.getMaxHealth();
		this.healthBar.setProgress(healthProcent);

		this.healthBar.render(poseStack, new Vector2i(x-91, y), new Vector2i(width, 5));

		String healthString = formatter.format(health[0]) + "/" + formatter.format(health[1]);
		GuiComponent.drawCenteredString(poseStack, minecraft.font, healthString,
				x-width/2-spacing, y-minecraft.font.lineHeight, 0xFFFFFF);

		//Mana
		int[] mana = new int[] {HudGui.mana, HudGui.maxMana};
		float manaProcent = (float) mana[0] / (float) mana[1];
		this.manaBar.setProgress(manaProcent);

		this.manaBar.render(poseStack, new Vector2i(x+spacing, y), new Vector2i(width, 5));

		String manaString = formatter.format(mana[0]) + "/" + formatter.format(mana[1]);
		GuiComponent.drawCenteredString(poseStack, minecraft.font, manaString,
				x+width/2+spacing, y-minecraft.font.lineHeight, 0xFFFFFF);


		// Skill
		float deltasecond = (float) 1 /Minecraft.getInstance().getFps();
		if (HudGui.skillTime > 0) {
			HudGui.skillTime -= 1*deltasecond;

			int skillWidth = 182;
			int skillY = 30;
			int[] skill = new int[] {HudGui.skill, HudGui.maxSkill};
			float skillProcent = (float) skill[0] / skill[1];
			this.skillBar.setProgress(skillProcent);

			this.skillBar.render(poseStack, new Vector2i(x-91, y-skillY), new Vector2i(skillWidth, 5));

			String skillString = HudGui.skillText + " " + formatter.format(skill[0]) + "/" + formatter.format(skill[1]) + " " + skillSuffix;
			GuiComponent.drawCenteredString(poseStack, minecraft.font, skillString,
					x, y-minecraft.font.lineHeight-skillY, 0xFFFFFF);
		}

		RenderSystem.setShaderColor(1, 1, 1, 1);
	}

	public static void update(String subtitle) {
		List<String> subtitles = new ArrayList<>(List.of(subtitle.split(" ")));
		if (subtitles.size() >= 3) {
			subtitles.removeIf(Objects::isNull);
			subtitles.removeIf(String::isEmpty);
			subtitles.removeIf(String::isBlank);

			for (String s : subtitles) {
				if (s.contains("❤")) {
					String[] health = s.replace(",", "").replace("❤", "").replace("§c", "").split("/");
					HudGui.health = Integer.parseInt(health[0]);
					HudGui.maxHealth = Integer.parseInt(health[1]);
				} if (s.contains("✎")) {
					String[] mana = s.replace(",", "").replace("✎", "").replace("§b", "").split("/");
					HudGui.mana = Integer.parseInt(mana[0]);
					HudGui.maxMana = Integer.parseInt(mana[1]);
				}
			}

			String skillResult = StringUtils.substringBetween(subtitles.get(3), "(", ")");
			if (skillResult != null) {
				String[] skill = skillResult.replace("k", "000").replace("m", "000000").replace(",", "")
						.replace(".", "").split("/");
				HudGui.skill = Integer.parseInt(skill[0]);
				HudGui.maxSkill = Integer.parseInt(skill[1]);
				HudGui.skillTime = HudGui.skillDelay;
				HudGui.skillText = subtitles.get(2);
				HudGui.skillSuffix = subtitles.get(1);
			}
		}
	}
}

package me.focusdev.sbi.api;

import com.google.gson.JsonObject;
import me.focusdev.sbi.utils.ComponentStyles;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BazaarApi extends API {
	public static Map<String, Product> products = new HashMap<>();

	public BazaarApi() {
		super("https://api.hypixel.net/skyblock/bazaar");
	}

	@Override
	void registerApiJson(JsonObject jsonObject) {
		products.clear();
		jsonObject.getAsJsonObject("products").asMap().forEach((s, jsonElement) ->
				products.put(s, new Product(jsonElement.getAsJsonObject())));
	}

	public static class Product {
		public final float instantSell;
		public final float instantBuy;

		public Product(JsonObject jsonObject) {
			JsonObject quickStatus = jsonObject.getAsJsonObject("quick_status");
			this.instantSell = quickStatus.get("sellPrice").getAsFloat();
			this.instantBuy = quickStatus.get("buyPrice").getAsFloat();
		}

		public List<Component> getLore(int maxAmount) {
			int amount = 1;
			if (Screen.hasShiftDown())
				amount = maxAmount;

			DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
			symbols.setGroupingSeparator(',');
			DecimalFormat df = new DecimalFormat("###,###", symbols);

			List<Component> lore = new ArrayList<>();
			lore.add(Component.literal("Bazaar: ").setStyle(ComponentStyles.PRICING.withBold(true)).append(
					Component.literal(String.valueOf(amount)).setStyle(ComponentStyles.LOW_PRIORITY.withBold(false))));
			lore.add(Component.empty()
					.append(Component.literal(" ■ ").setStyle(ComponentStyles.LOW_PRIORITY))
					.append(Component.literal("Inst-Buy: " + df.format(this.instantBuy*amount)).setStyle(ComponentStyles.PRICING)));
			lore.add(Component.empty()
					.append(Component.literal(" ■ ").setStyle(ComponentStyles.LOW_PRIORITY))
					.append(Component.literal("Inst-Sell: " + df.format(this.instantSell*amount)).setStyle(ComponentStyles.PRICING)));
			return lore;
		}
	}
}

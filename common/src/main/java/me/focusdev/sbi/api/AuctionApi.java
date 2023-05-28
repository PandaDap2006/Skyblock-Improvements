package me.focusdev.sbi.api;

import com.google.common.io.ByteSource;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.focusdev.sbi.utils.ComponentStyles;
import me.focusdev.sbi.utils.ItemUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class AuctionApi extends API<JsonObject> {
	public static List<Product> products = new ArrayList<>();

	public AuctionApi() {
		super("https://api.hypixel.net/skyblock/auctions");
	}

	@Override
	void registerApiJson(JsonObject jsonObject) {
		for (JsonElement auction : jsonObject.getAsJsonArray("auctions")) {
			products.add(new Product(auction.getAsJsonObject()));
		}

		if ((jsonObject.get("page").getAsInt() + 1) < jsonObject.get("totalPages").getAsInt()) {
			this.downloadJSON("https://api.hypixel.net/skyblock/auctions?page=" + (jsonObject.get("page").getAsInt() + 1));
		}
	}

	public static float lowestBin(String itemID) {
		float price = -1;
		for (Product product : products) {
			if (product.itemID.equalsIgnoreCase(itemID) && product.isBin) {
				if (price < 0 || product.startingPrice < price) {
					price = product.startingPrice;
				}
			}
		}
		return price;
	}

	public static List<Component> getLore(String itemID) {
		DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
		symbols.setGroupingSeparator(',');
		DecimalFormat df = new DecimalFormat("###,###", symbols);

		List<Component> lore = new ArrayList<>();

		float lowestBin = lowestBin(itemID);
		if (lowestBin >= 0) {
			lore.add(Component.literal("Auction: ").setStyle(ComponentStyles.PRICING.withBold(true)));
			lore.add(Component.empty()
					.append(Component.literal(" ■ ").setStyle(ComponentStyles.LOW_PRIORITY))
					.append(Component.literal("Lowest Bin: " + df.format(lowestBin)).setStyle(ComponentStyles.PRICING)));
		}
		return lore;
	}

	public static class Product {
		public final String itemID;
		public final boolean isBin;
		public final float startingPrice;

		public Product(JsonObject jsonObject) {
			byte[] nbtBytes = Base64.getDecoder().decode(jsonObject.get("item_bytes").getAsString());
			try {
				CompoundTag nbt = NbtIo.readCompressed(ByteSource.wrap(nbtBytes).openStream());
				nbt = ((ListTag)nbt.get("i")).getCompound(0);

				CompoundTag extraAttributes = nbt.getCompound("tag").getCompound("ExtraAttributes");
				if (extraAttributes.contains("id"))
					this.itemID = extraAttributes.getString("id").toLowerCase();
				else
					this.itemID = "null";
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

			this.isBin = jsonObject.get("bin").getAsBoolean();
			this.startingPrice = jsonObject.get("starting_bid").getAsFloat();
		}
	}
}

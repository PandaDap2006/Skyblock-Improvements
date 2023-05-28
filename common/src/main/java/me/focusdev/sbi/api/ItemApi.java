package me.focusdev.sbi.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class ItemApi extends API<JsonObject> {
	public static final Map<String, Item> items = new HashMap<>();


	public ItemApi() {
		super("https://api.hypixel.net/resources/skyblock/items");
	}

	@Override
	void registerApiJson(JsonObject jsonObject) {
		for (JsonElement jsonElement : jsonObject.getAsJsonArray("items")) {
			items.put(jsonElement.getAsJsonObject().get("id").getAsString().toLowerCase(),
					new Item(jsonElement.getAsJsonObject()));
		}
	}

	public static class Item {
		public final String materialId;

		public Item(JsonObject jsonObject) {
			String materialId = jsonObject.get("material").getAsString().toLowerCase();
			if (jsonObject.has("durability"))
				materialId += ":" + jsonObject.get("durability").getAsInt();
			this.materialId = MaterialApi.itemIds.getOrDefault(materialId, materialId);
		}
	}
}

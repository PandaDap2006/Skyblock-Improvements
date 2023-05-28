package me.focusdev.sbi.api;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaterialApi extends API<JsonObject> {
	public static final Map<String, String> itemIds = new HashMap<>();


	public MaterialApi() {
		super("https://raw.githubusercontent.com/PandaDap2006/Skyblock-Improvements/master/api/material_ids.json");
	}

	@Override
	void registerApiJson(JsonObject jsonObject) {
		jsonObject.asMap().forEach((s, jsonElement) -> itemIds.put(s, jsonElement.getAsString()));
	}
}

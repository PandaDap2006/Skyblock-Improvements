package me.focusdev.sbi.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class API {
	public API(String url) {
		downloadJSON(url);
	}

	public void downloadJSON(String url) {
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setRequestMethod("GET");

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();

			String json = content.toString();
			Gson gson = new GsonBuilder().disableHtmlEscaping().create();
			JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
			registerApiJson(jsonObject);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	abstract void registerApiJson(JsonObject jsonObject);
}

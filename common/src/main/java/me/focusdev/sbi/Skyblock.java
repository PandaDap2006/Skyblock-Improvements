package me.focusdev.sbi;

import me.focusdev.sbi.api.*;

public class Skyblock {
	public static final String MODID = "sbi";

	public static ItemApi ITEMS = new ItemApi();
	public static MaterialApi MATERIAL_IDS = new MaterialApi();
	public static BazaarApi BAZAAR = new BazaarApi();

	public static void init() {
		ITEMS.downloadJSON();
		MATERIAL_IDS.downloadJSON();
		BAZAAR.downloadJSON();
	}
}
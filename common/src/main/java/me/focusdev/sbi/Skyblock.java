package me.focusdev.sbi;

import me.focusdev.sbi.api.AuctionApi;
import me.focusdev.sbi.api.BazaarApi;
import me.focusdev.sbi.api.ItemApi;
import me.focusdev.sbi.api.MaterialApi;

public class Skyblock {
	public static final String MODID = "sbi";

	public static ItemApi ITEMS = new ItemApi();
	public static MaterialApi ITEM_IDS = new MaterialApi();
	public static BazaarApi BAZAAR = new BazaarApi();
	public static AuctionApi AUCTION = new AuctionApi();

	public static void init() {
		ITEMS.downloadJSON();
		ITEM_IDS.downloadJSON();
		BAZAAR.downloadJSON();
		AUCTION.downloadJSON();
	}
}
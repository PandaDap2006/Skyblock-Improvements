package me.focusdev.sbi;

import me.focusdev.sbi.api.AuctionApi;
import me.focusdev.sbi.api.BazaarApi;

public class Skyblock {
	public static final String MODID = "sbi";

	public static BazaarApi BAZAAR = new BazaarApi();
	public static AuctionApi AUCTION = new AuctionApi();

	public static void init() {}
}
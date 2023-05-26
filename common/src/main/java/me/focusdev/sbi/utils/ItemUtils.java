package me.focusdev.sbi.utils;

import net.minecraft.world.item.ItemStack;

public class ItemUtils {
	public static String getItemID(ItemStack itemStack) {
		return itemStack.getOrCreateTagElement("ExtraAttributes").getString("id");
	}
}

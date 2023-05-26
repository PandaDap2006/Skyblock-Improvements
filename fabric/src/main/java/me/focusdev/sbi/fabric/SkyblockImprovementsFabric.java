package me.focusdev.sbi.fabric;

import me.focusdev.sbi.Skyblock;
import net.fabricmc.api.ModInitializer;

public class SkyblockImprovementsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Skyblock.init();
    }
}
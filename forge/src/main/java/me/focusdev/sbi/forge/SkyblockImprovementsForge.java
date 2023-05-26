package me.focusdev.sbi.forge;

import dev.architectury.platform.forge.EventBuses;
import me.focusdev.sbi.Skyblock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Skyblock.MODID)
public class SkyblockImprovementsForge {
    public SkyblockImprovementsForge() {
		// Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(Skyblock.MODID, FMLJavaModLoadingContext.get().getModEventBus());
            Skyblock.init();
    }
}
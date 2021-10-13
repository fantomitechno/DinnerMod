package fr.fantomitechno.dinnermod;

import net.fabricmc.api.ModInitializer;

import org.apache.logging.log4j.LogManager;

public class DinnerMod implements ModInitializer {

    @Override
    public void onInitialize() {
        LogManager.getLogger("dinnermod").info("DinnerMod succefuly loaded");
    }
}

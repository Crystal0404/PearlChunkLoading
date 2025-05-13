package com.github.crystal0404.mods.pearl;

import com.github.crystal0404.mods.pearl.config.PearlSave;
import com.github.crystal0404.mods.pearl.config.PearlSettings;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PearlChunkLoadingMod implements ModInitializer {
    public static final String MOD_NAME = "Pearl Chunk Loading";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
    @Override
    public void onInitialize() {
        PearlSettings.init();
        PearlSave.init();
        PearlCommand.register();
    }
}

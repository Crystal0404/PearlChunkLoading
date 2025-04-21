package crystal0404.pearlchunkloading;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PearlChunkLoadingMod implements ModInitializer {
	public static final String MOD_ID = "pearl";
	public static final String MOD_NAME = "PearlChunkLoading";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
	public static String version;
	public static Boolean shouldKeepPearl;

	@Override
	public void onInitialize() {
		version = FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow().getMetadata().getVersion().getFriendlyString();
		shouldKeepPearl = Boolean.getBoolean("pearl.keep");
		LOGGER.info("Pearl chunk loading!");
	}
}
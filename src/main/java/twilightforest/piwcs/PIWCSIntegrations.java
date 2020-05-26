package twilightforest.piwcs;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;

public class PIWCSIntegrations {
	
	public static void setup() {
		
		if (Loader.isModLoaded("mineragenesis")) {
			MineraGenesisIntegration.setup();
		} else {
			FMLLog.info("[TwilightForest] Did not find MineraGenesis, did not load MineraGenesis integration.");
			return;
		}
		
		if (Loader.isModLoaded("harvestcraft")) {
			HarvestCraftIntegration.setup();
		} else {
			FMLLog.info("[TwilightForest] Did not find Pam's HarvestCraft, did not load Pam's HarvestCraft integration.");
			return;
		}
		
		if (Loader.isModLoaded("Mariculture")) {
			MaricultureIntegration.setup();
		} else {
			FMLLog.info("[TwilightForest] Did not find Mariculture, did not load Mariculture integration.");
			return;
		}
		
	}

}

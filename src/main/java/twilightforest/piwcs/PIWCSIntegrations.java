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
		
	}

}

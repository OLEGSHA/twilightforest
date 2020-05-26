package twilightforest.piwcs;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.item.ItemStack;

public class MaricultureIntegration {
	
	private static boolean integrationAvailable = false;
	
	public static void setup() {
		try {
			
			integrationAvailable = true;
			FMLLog.info("[TwilightForest] Loaded Mariculture integration.");
			
		} catch (NoClassDefFoundError e) {
			// Do nothing
			e.printStackTrace();
			FMLLog.info("[TwilightForest] Failed to load Mariculture, did not load Mariculture integration.");
		}
	}
	
	public static boolean isIntegrationAvailable() {
		return integrationAvailable;
	}
	
	public static ItemStack getSpiderfishTreasure() {
		return mariculture.api.fishery.Fishing.fishHelper.makePureFish(
				mariculture.fishery.Fish.spider
		);
	}

}

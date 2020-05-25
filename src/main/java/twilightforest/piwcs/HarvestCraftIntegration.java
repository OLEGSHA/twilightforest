package twilightforest.piwcs;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.block.Block;

public class HarvestCraftIntegration {
	
	private static boolean integrationAvailable = false;
	
	public static void setup() {
		try {
			
			integrationAvailable = true;
			FMLLog.info("[TwilightForest] Loaded Pam's HarvestCraft integration.");
			
		} catch (NoClassDefFoundError e) {
			// Do nothing
			e.printStackTrace();
			FMLLog.info("[TwilightForest] Failed to load Pam's HarvestCraft, did not load Pam's HarvestCraft integration.");
		}
	}
	
	public static boolean isIntegrationAvailable() {
		return integrationAvailable;
	}
	
	public static Block getBeehiveBlock() {
		return com.pam.harvestcraft.BlockRegistry.pamBeehive;
	}
	
	public static int getBeehiveBlockMetaValue() {
		return 0;
	}

}

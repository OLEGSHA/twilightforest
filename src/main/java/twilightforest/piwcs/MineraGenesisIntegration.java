package twilightforest.piwcs;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import ru.windcorp.mineragenesis.MGConfig;
import ru.windcorp.mineragenesis.forge.MineraGenesisChunkProcessRequest;
import twilightforest.TwilightForestMod;

public class MineraGenesisIntegration {
	
	private static boolean integrationAvailable = false;
	
	public static void setup() {
		try {
			
			if (!MGConfig.isDimensionHandled(TwilightForestMod.dimensionID)) {
				FMLLog.info("[TwilightForest] MineraGenesis does not handle the Twilight Forest, did not load MineraGenesis integration.");
				return;
			}
			
			integrationAvailable = true;
			FMLLog.info("[TwilightForest] Loaded MineraGenesis integration.");
			
		} catch (NoClassDefFoundError e) {
			// Do nothing
			e.printStackTrace();
			FMLLog.info("[TwilightForest] Failed to load MineraGenesis, did not load MineraGenesis integration.");
		}
	}
	
	public static void requestChunkProcessing(World world, int chunkX, int chunkZ) {
		if (!integrationAvailable) {
			return;
		}
		
		MinecraftForge.EVENT_BUS.post(new MineraGenesisChunkProcessRequest(world, chunkX, chunkZ));
	}

}

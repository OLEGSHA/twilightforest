package twilightforest.world;

import java.util.Random;

import net.minecraft.world.World;
import twilightforest.block.BlockTFTrollRoot;
import twilightforest.block.TFBlocks;



public class TFGenTrollRoots extends TFGenerator
{
    public boolean generate(World par1World, Random par2Random, int x, int y, int z)
    {
        int copyX = x;
        int copyZ = z;
        
        for (; y > 5; --y)
        {
            if (par1World.isAirBlock(x, y, z) && BlockTFTrollRoot.canPlaceRootBelow(par1World, x, y + 1, z) && par2Random.nextInt(6) > 0)
            {
            	if (par2Random.nextInt(10) == 0) {
                    par1World.setBlock(x, y, z, TFBlocks.unripeTrollBer);
            	} else {
                    par1World.setBlock(x, y, z, TFBlocks.trollVidr);
            	}
            }
            else
            {
                x = copyX + par2Random.nextInt(4) - par2Random.nextInt(4);
                z = copyZ + par2Random.nextInt(4) - par2Random.nextInt(4);
            }
        }

        return true;
    }
}

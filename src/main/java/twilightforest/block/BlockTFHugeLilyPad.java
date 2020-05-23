package twilightforest.block;

import java.util.List;

import twilightforest.TwilightForestMod;
import twilightforest.item.TFItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTFHugeLilyPad extends BlockBush {
	
	private IIcon pad1;
	private IIcon pad2;
	private IIcon pad3;
	private IIcon blank;
	private boolean isSelfDestructing = false;


	protected BlockTFHugeLilyPad() {
		super(Material.plants);
		
        float f = 0.5F;
        float f1 = 0.015625F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
		this.setStepSound(soundTypeGrass);

		this.setCreativeTab(TFItems.creativeTab);
		
		@SuppressWarnings("unused")
		Item lily;

	}
	
    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
    	return TwilightForestMod.proxy.getHugeLilyPadBlockRenderID();
    }

	
    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        return AxisAlignedBB.getBoundingBox((double)p_149668_2_ + this.minX, (double)p_149668_3_ + this.minY, (double)p_149668_4_ + this.minZ, (double)p_149668_2_ + this.maxX, (double)p_149668_3_ + this.maxY, (double)p_149668_4_ + this.maxZ);
    }
    
	/**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
    }
    

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
	@Override
	public IIcon getIcon(int side, int meta) {
		// sides blank
		if (side > 1) {
			return this.blank;
		}
		
		int orient = meta >> 2;
		int piece = meta & 3;
		
        // why can't this just be simple?
		if (orient == 1) {
			orient = 3;
		} else if (orient == 3) {
			orient = 1;
		}
		
		
		int display = (piece + orient) % 4;
		

		switch (display) {
		case 0:
		default:
			return this.blockIcon;
		case 1:
			return this.pad1;
		case 2:
			return this.pad2;
		case 3:
			return this.pad3;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		this.blockIcon = par1IconRegister.registerIcon(TwilightForestMod.ID + ":huge_lilypad_0");
		this.pad1 = par1IconRegister.registerIcon(TwilightForestMod.ID + ":huge_lilypad_1");
		this.pad2 = par1IconRegister.registerIcon(TwilightForestMod.ID + ":huge_lilypad_2");
		this.pad3 = par1IconRegister.registerIcon(TwilightForestMod.ID + ":huge_lilypad_3");
		this.blank = par1IconRegister.registerIcon(TwilightForestMod.ID + ":blank");
	}
	
    /**
     * is the block grass, dirt or farmland
     */
    protected boolean canPlaceBlockOn(Block block)
    {
        return block == Blocks.water;
    }

	/**
     * Called when the block is attempted to be harvested
     */
    public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player) {
		this.setGiantBlockToAir(world, x, y, z);
    }
    
    /**
     * Called when the block is destroyed by an explosion.
     * Useful for allowing the block to take into account tile entities,
     * metadata, etc. when exploded, before it is removed.
     *
     * @param world The current world
     * @param x X Position
     * @param y Y Position
     * @param z Z Position
     * @param Explosion The explosion instance affecting the block
     */
    public void onBlockExploded(World world, int x, int y, int z, Explosion explosion)
    {
        world.setBlockToAir(x, y, z);
		this.setGiantBlockToAir(world, x, y, z);
    }

    
    /**
     * Called on server worlds only when the block is about to be replaced by a different block or the same block with a
     * different metadata value. Args: world, x, y, z, old metadata
     */
    public void onBlockPreDestroy(World world, int x, int y, int z, int meta) {
    	
		int orient = meta >> 2;
		int piece = meta & 3;

		@SuppressWarnings("unused")
		int display = (piece + orient) % 4;
    	
    	if (!this.isSelfDestructing  && !canBlockStay(world, x, y, z)) {
    		this.setGiantBlockToAir(world, x, y, z);
    	}
    }
    
    /**
     * Set the whole giant block area to air
     */
    private void setGiantBlockToAir(World world, int x, int y, int z) {
    	// this flag is maybe not totally perfect
    	this.isSelfDestructing = true;
    	
    	int bx = (x >> 1) << 1;
    	int bz = (z >> 1) << 1;

    	// this is the best loop over 3 items that I've ever programmed!
    	for (int dx = 0; dx < 2; dx++) {
    		for (int dz = 0; dz < 2; dz++) {
    			if (!(x == bx + dx && z == bz + dz)) {
    				if (world.getBlock(bx + dx, y, bz + dz) == this) {
    					world.setBlock(bx + dx, y, bz + dz, Blocks.air, 0, 2);
    				}
    			}
    		}
    	}

    	this.isSelfDestructing = false;
	}


	/**
     * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
     */
    public boolean canBlockStay(World world, int x, int y, int z)  {
        boolean allThisBlock = true;
        boolean allWater = true;
        
    	int bx = (x >> 1) << 1;
    	int bz = (z >> 1) << 1;

    	for (int dx = 0; dx < 2; dx++) {
    		for (int dz = 0; dz < 2; dz++) {
    			allThisBlock &= world.getBlock(bx + dx, y, bz + dz) == this;
    			allWater &= (world.getBlock(bx + dx, y - 1, bz + dz).getMaterial() == Material.water && world.getBlockMetadata(bx + dx, y - 1, bz + dz) == 0);
    		}
    	}

    	return allThisBlock && allWater;
    }
    
    /**
     * checks if the block can stay, if not drop as item
     */
    protected void checkAndDropBlock(World p_149855_1_, int p_149855_2_, int p_149855_3_, int p_149855_4_)
    {
        if (!this.canBlockStay(p_149855_1_, p_149855_2_, p_149855_3_, p_149855_4_))
        {
            //this.dropBlockAsItem(p_149855_1_, p_149855_2_, p_149855_3_, p_149855_4_, p_149855_1_.getBlockMetadata(p_149855_2_, p_149855_3_, p_149855_4_), 0);
            p_149855_1_.setBlock(p_149855_2_, p_149855_3_, p_149855_4_, getBlockById(0), 0, 2);
        }
    }
    
    /**
     * Returns the mobility information of the block, 0 = free, 1 = can't push but can move over, 2 = total immobility
     * and stop pistons
     */
    public int getMobilityFlag()
    {
        return 2;
    }
    
    @SideOnly(Side.CLIENT)
    public int getBlockColor()
    {
        return 2129968;
    }

    /**
     * Returns the color this block should be rendered. Used by leaves.
     */
    @SideOnly(Side.CLIENT)
    public int getRenderColor(int p_149741_1_)
    {
        return 2129968;
    }

    /**
     * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
     * when first determining what to render.
     */
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_)
    {
        return 2129968;
    }
}

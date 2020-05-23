package twilightforest.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import twilightforest.TwilightForestMod;
import twilightforest.block.BlockTFRoots;
import twilightforest.block.TFBlocks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;



public class ItemTFOreMeter extends ItemTF {

	protected ItemTFOreMeter() {
		super();
		this.setCreativeTab(TFItems.creativeTab);
	}
	
	

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World world, EntityPlayer player) {
		
		int useX = MathHelper.floor_double(player.posX);
		int useZ = MathHelper.floor_double(player.posZ);
		
		if (!world.isRemote) {
			countOreInArea(player, world, useX, useZ, 3);
		}
		
//		player.addChatMessage("func_72971_b = " + player.worldObj.func_72971_b(1.0f));
//		player.addChatMessage("calculateSkylightSubtracted = " + player.worldObj.calculateSkylightSubtracted(1.0f));

//		player.addChatMessage("player health =" + player.getHealth());

		
		return super.onItemRightClick(par1ItemStack, world, player);
	}

	@SuppressWarnings("unused")
	private void countOreInChunk(EntityPlayer player, World world, int useX, int useZ) {
		int chunkX = useX >> 4;
		int chunkZ = useZ >> 4;

		int countStone = countBlockInChunk(world, Blocks.stone, chunkX, chunkZ);
		int countDirt = countBlockInChunk(world, Blocks.dirt, chunkX, chunkZ);
		int countGravel = countBlockInChunk(world, Blocks.gravel, chunkX, chunkZ);
		
		int countCoal = countBlockInChunk(world, Blocks.coal_ore, chunkX, chunkZ);
		int countIron = countBlockInChunk(world, Blocks.iron_ore, chunkX, chunkZ);
		int countGold = countBlockInChunk(world, Blocks.gold_ore, chunkX, chunkZ);
		int countDiamond = countBlockInChunk(world, Blocks.diamond_ore, chunkX, chunkZ);
		int countLapis = countBlockInChunk(world, Blocks.lapis_ore, chunkX, chunkZ);
		int countRedstone = countBlockInChunk(world, Blocks.redstone_ore, chunkX, chunkZ);
		
		int countRoots = countBlockInChunk(world, TFBlocks.root, BlockTFRoots.ROOT_META, chunkX, chunkZ);
		int countOreRoots = countBlockInChunk(world, TFBlocks.root, BlockTFRoots.OREROOT_META, chunkX, chunkZ);
		
		int total = countStone + countDirt + countGravel + countCoal + countIron + countGold + countDiamond + countLapis + countRedstone + countRoots + countOreRoots;
		
		
//		player.addChatMessage("Ore Meter!");
//		player.addChatMessage("Metering chunk  [" + chunkX + ", " + chunkZ + "]");
//		player.addChatMessage("Coal - " + countCoal + " " + percent(countCoal, total));
//		player.addChatMessage("Iron - " + countIron + " " + percent(countIron, total));
//		player.addChatMessage("Gold - " + countGold + " " + percent(countGold, total));
//		player.addChatMessage("Diamond - " + countDiamond + " " + percent(countDiamond, total));
//		player.addChatMessage("Lapis - " + countLapis + " " + percent(countLapis, total));
//		player.addChatMessage("Redstone - " + countRedstone + " " + percent(countRedstone, total));
//		player.addChatMessage("Roots - " + countRoots + " " + percent(countRoots, total));
//		player.addChatMessage("Ore Roots - " + countOreRoots + " " + percent(countOreRoots, total));
	}
	
	private void countOreInArea(EntityPlayer player, World world, int useX, int useZ, int radius) {
		int chunkX = useX >> 4;
		int chunkZ = useZ >> 4;
		

		int countStone = 0;
		int countDirt = 0;
		int countGravel = 0;
		
		int countCoal = 0;
		int countIron = 0;
		int countGold = 0;
		int countDiamond = 0;
		int countLapis = 0;
		int countRedstone = 0;
		@SuppressWarnings("unused")
		int countExposedDiamond = 0;

		int countRoots = 0;
		int countOreRoots = 0;

		@SuppressWarnings("unused")
		int total = 0;


		for (int cx = chunkX - radius; cx <= chunkX + radius; cx++) {
			for (int cz = chunkZ - radius; cz <= chunkZ + radius; cz++) {

				countStone += countBlockInChunk(world, Blocks.stone, cx, cz);
				countDirt += countBlockInChunk(world, Blocks.dirt, cx, cz);
				countGravel += countBlockInChunk(world, Blocks.gravel, cx, cz);

				countCoal += countBlockInChunk(world, Blocks.coal_ore, cx, cz);
				countIron += countBlockInChunk(world, Blocks.iron_ore, cx, cz);
				countGold += countBlockInChunk(world, Blocks.gold_ore, cx, cz);
				countDiamond += countBlockInChunk(world, Blocks.diamond_ore, cx, cz);
				countLapis += countBlockInChunk(world, Blocks.lapis_ore, cx, cz);
				countRedstone += countBlockInChunk(world, Blocks.redstone_ore, cx, cz);
				countExposedDiamond += countExposedBlockInChunk(world, Blocks.diamond_ore, cx, cz);

				
				countRoots += countBlockInChunk(world, TFBlocks.root, BlockTFRoots.ROOT_META, cx, cz);
				countOreRoots += countBlockInChunk(world, TFBlocks.root, BlockTFRoots.OREROOT_META, cx, cz);


			}
		}

		total = countStone + countDirt + countGravel + countCoal + countIron + countGold + countDiamond + countLapis + countRedstone + countRoots + countOreRoots;


//		player.addChatMessage("Ore Meter!");
//		player.addChatMessage("Metering chunks in radius " + radius + " around chunk [" + chunkX + ", " + chunkZ + "]");
//		player.addChatMessage("Coal - " + countCoal + " " + percent(countCoal, total));
//		player.addChatMessage("Iron - " + countIron + " " + percent(countIron, total));
//		player.addChatMessage("Gold - " + countGold + " " + percent(countGold, total));
//		player.addChatMessage("Diamond - " + countDiamond + " " + percent(countDiamond, total) + ", exposed - " + countExposedDiamond);
//		player.addChatMessage("Lapis - " + countLapis + " " + percent(countLapis, total));
//		player.addChatMessage("Redstone - " + countRedstone + " " + percent(countRedstone, total));
//		player.addChatMessage("Roots - " + countRoots + " " + percent(countRoots, total));
//		player.addChatMessage("Ore Roots - " + countOreRoots + " " + percent(countOreRoots, total));
	}
	
	public float percent(int count, int total) {
		return (float)count / (float)total * 100F;
	}
	
	public int countBlockInChunk(World world, Block stone, int cx, int cz) {

		Chunk chunk = world.getChunkFromChunkCoords(cx, cz);
		
		int count = 0;
		
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				for (int y = 0; y < 256; y++) {
					if (chunk.getBlock(x, y, z) == stone) {
						count++;
					}
				}
			}
		}
		
		return count;
	}	
	
	public int countBlockInChunk(World world, Block blockID, int meta, int cx, int cz) {

		Chunk chunk = world.getChunkFromChunkCoords(cx, cz);
		
		int count = 0;
		
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				for (int y = 0; y < 256; y++) {
					if (chunk.getBlock(x, y, z) == blockID && chunk.getBlockMetadata(x, y, z) == meta) {
						count++;
					}
				}
			}
		}
		
		return count;
	}

	private int countExposedBlockInChunk(World world, Block blockID, int cx, int cz) {
		
		int count = 0;

		for (int x = cx << 4; x < (cx << 4) + 16; x++) {
			for (int z = cz << 4; z < (cz << 4) + 16; z++) {
				for (int y = 0; y < 256; y++) {
					if (world.getBlock(x, y, z) == blockID) {
						// check if exposed
						if (world.isAirBlock(x + 1, y, z) || world.isAirBlock(x - 1, y, z)
								||world.isAirBlock(x, y + 1, z) || world.isAirBlock(x, y - 1, z)
								||world.isAirBlock(x, y + 1, z) || world.isAirBlock(x, y - 1, z))
						{
							count++;
						}
					}
				}
			}
		}
		
		return count;

	}



	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
	}
	
	/**
	 * Properly register icon source
	 */
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon(TwilightForestMod.ID + ":" + this.getUnlocalizedName().substring(5));
    }
}

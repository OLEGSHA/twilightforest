package twilightforest.structures.lichtower;

import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import twilightforest.structures.StructureTFComponent;


public class ComponentTFTowerBridge extends ComponentTFTowerWing {


	int dSize;
	int dHeight;
	
	public ComponentTFTowerBridge() {
		super();
		// TODO Auto-generated constructor stub
	}


	protected ComponentTFTowerBridge(int i, int x, int y, int z, int pSize, int pHeight, int direction) {
		super(i, x, y, z, 3, 3, direction);
		
		this.dSize = pSize;
		this.dHeight = pHeight;
	}

	
	@Override
	public void buildComponent(StructureComponent parent, List list, Random rand) {
		
		
		int[] dest = new int[] {2, 1, 1};//getValidOpening(rand, 0);
		
		makeTowerWing(list, rand, 1, dest[0], dest[1], dest[2], dSize, dHeight, 0);
	}
	
	/**
	 * Gets the bounding box of the tower wing we would like to make.
	 * @return
	 */
	public StructureBoundingBox getWingBB() {
		int[] dest = offsetTowerCoords(2, 1, 1, dSize, this.getCoordBaseMode());
		return StructureTFComponent.getComponentToAddBoundingBox(dest[0], dest[1], dest[2], 0, 0, 0, dSize - 1, dHeight - 1, dSize - 1, this.getCoordBaseMode());
	}

	
	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {

		// make walls
		for (int x = 0; x < 3; x++) {
			placeBlockAtCurrentPosition(world, Blocks.fence, 0, x, 2, 0, sbb);
			placeBlockAtCurrentPosition(world, Blocks.fence, 0, x, 2, 2, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stonebrick, 0, x, 1, 0, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stonebrick, 0, x, 1, 2, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stonebrick, 0, x, 0, 0, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stonebrick, 0, x, 0, 1, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stonebrick, 0, x, 0, 2, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stonebrick, 0, x, -1, 1, sbb);
		}
		
		// try two blocks outside the boundries
		placeBlockAtCurrentPosition(world, Blocks.stonebrick, 0, -1, -1, 1, sbb);
		placeBlockAtCurrentPosition(world, Blocks.stonebrick, 0, 3, -1, 1, sbb);
		
		// clear bridge walkway
		this.fillWithAir(world, sbb, 0, 1, 1, 2, 2, 1);


        // marker blocks
//        placeBlockAtCurrentPosition(world, Blocks.wool, this.coordBaseMode, size / 2, 2, size / 2, sbb);
//        placeBlockAtCurrentPosition(world, Blocks.gold_block, 0, 0, 0, 0, sbb);
        
        // door opening?
//        makeDoorOpening(world, sbb);
        
        
        
		return true;
	}
}

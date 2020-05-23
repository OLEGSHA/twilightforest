package twilightforest.structures.stronghold;

import java.util.List;
import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class ComponentTFStrongholdEntrance extends StructureTFStrongholdComponent {
	
	public TFStrongholdPieces lowerPieces;

	public ComponentTFStrongholdEntrance() {
		super();
	}

	public ComponentTFStrongholdEntrance(World world, Random rand, int i, int x, int y, int z) {
		super(i, 0, x, y - 10, z);
		
		this.deco = new StructureTFDecoratorStronghold();
		
		lowerPieces = new TFStrongholdPieces();
	}
	
    /**
     * Initiates construction of the Structure Component picked, at the current Location of StructGen
     */
	@Override
	public void buildComponent(StructureComponent parent, List list, Random random) {
		super.buildComponent(parent, list, random);

		// make a random component in each direction
		lowerPieces.prepareStructurePieces();
		addNewComponent(parent, list, random, 0, 4, 1, 18);
		lowerPieces.prepareStructurePieces();
		if (!listContainsBossRoom(list))
		{
			//System.out.println("Did not find boss room from exit 0");
		}
		else
		{
			lowerPieces.markBossRoomUsed();
		}
		addNewComponent(parent, list, random, 1, -1, 1, 13);
		lowerPieces.prepareStructurePieces();
		if (!listContainsBossRoom(list))
		{
			//System.out.println("Did not find boss room from exit 1");
		}
		else
		{
			lowerPieces.markBossRoomUsed();
		}
		addNewComponent(parent, list, random, 2, 13, 1, -1);
		lowerPieces.prepareStructurePieces();
		if (!listContainsBossRoom(list))
		{
			//System.out.println("Did not find boss room from exit 2");
		}
		else
		{
			lowerPieces.markBossRoomUsed();
		}
		addNewComponent(parent, list, random, 3, 18, 1, 4);
		if (!listContainsBossRoom(list))
		{
			System.out.println("Did not find boss room from exit 3 - EPIC FAIL");
		}
		List<StructureTFStrongholdComponent> pieceList = (List<StructureTFStrongholdComponent>)list;
		StructureBoundingBox shieldBox = new StructureBoundingBox(this.boundingBox);
		
		@SuppressWarnings("unused")
		int tStairs = 0;
		@SuppressWarnings("unused")
		int tCorridors = 0;
		@SuppressWarnings("unused")
		int deadEnd = 0;
		@SuppressWarnings("unused")
		int tRooms = 0;
		@SuppressWarnings("unused")
		int bossRooms = 0;
		
		// compute and generate MEGASHIELD
		for (StructureTFStrongholdComponent component : pieceList)
		{
			shieldBox.expandTo(component.getBoundingBox());
			
			
			if (component instanceof ComponentTFStrongholdSmallStairs && ((ComponentTFStrongholdSmallStairs)component).hasTreasure)
			{
				tStairs++;
			}
			if (component instanceof ComponentTFStrongholdTreasureCorridor)
			{
				tCorridors++;
			}
			if (component instanceof ComponentTFStrongholdDeadEnd)
			{
				deadEnd++;
			}
			if (component instanceof ComponentTFStrongholdTreasureRoom)
			{
				tRooms++;
			}
			if (component instanceof ComponentTFStrongholdBossRoom)
			{
				bossRooms++;
			}
		}
		
//		System.out.printf("MEGASHIELD computed!  %d, %d, %d to %d, %d, %d.\n", shieldBox.minX, shieldBox.minY, shieldBox.minZ, shieldBox.maxX, shieldBox.maxY, shieldBox.maxZ);
//		System.out.printf("Stronghold at this point contains %d elements.\n", pieceList.size());
//		
//		System.out.printf("Room count! TStairs = %d, TCorr = %d, Dead End = %d, TRoom = %d, Boss = %d \n", tStairs, tCorridors, deadEnd, tRooms, bossRooms);
		
//		StructureTFStrongholdShield shield = new StructureTFStrongholdShield(shieldBox.minX - 1, shieldBox.minY, shieldBox.minZ - 1, shieldBox.maxX, shieldBox.maxY, shieldBox.maxZ);
//		list.add(shield);
		
		// add the upper stronghold
		StructureTFStrongholdComponent accessChamber = new ComponentTFStrongholdAccessChamber(2, this.getCoordBaseMode(), boundingBox.minX + 8, boundingBox.minY + 7, boundingBox.minZ + 4);
		list.add(accessChamber);
		accessChamber.buildComponent(this, list, random);
		

	}

	private boolean listContainsBossRoom(List<StructureTFStrongholdComponent> list) {
		for (StructureTFStrongholdComponent component : list)
		{
			if (component instanceof ComponentTFStrongholdBossRoom)
			{
				return true;
			}
		}
		
		return false;
	}

	/**
	 * Make a bounding box for this room
	 */
	public StructureBoundingBox generateBoundingBox(int facing, int x, int y, int z)
	{
		return StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, -1, -1, 0, 18, 7, 18, facing);
	}

	/**
	 * Generate the blocks that go here
	 */
	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		placeStrongholdWalls(world, sbb, 0, 0, 0, 17, 6, 17, rand, deco.randomBlocks);

		@SuppressWarnings("unused")
		int var8 = this.getXWithOffset(0, 0);
		@SuppressWarnings("unused")
		int var9 = this.getYWithOffset(0);
		@SuppressWarnings("unused")
		int var10 = this.getZWithOffset(0, 0);

		//System.out.println("Drawing STRONGHOLD entrance at " + var8 + ", " + var9 + ", " + var10);
		
		// statues
		placeCornerStatue(world, 5, 1, 5, 0, sbb);
		placeCornerStatue(world, 5, 1, 12, 1, sbb);
		placeCornerStatue(world, 12, 1, 5, 2, sbb);
		placeCornerStatue(world, 12, 1, 12, 3, sbb);
		
		// statues
		this.placeWallStatue(world, 9, 1, 16, 0, sbb);
		this.placeWallStatue(world, 1, 1, 9, 1, sbb);
		this.placeWallStatue(world, 8, 1, 1, 2, sbb);
		this.placeWallStatue(world, 16, 1, 8, 3, sbb);
		
		// doors
		this.placeDoors(world, rand, sbb);

		return true;
	}

}

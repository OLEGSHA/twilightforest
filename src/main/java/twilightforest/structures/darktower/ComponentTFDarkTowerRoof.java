package twilightforest.structures.darktower;

import java.util.List;
import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import twilightforest.structures.StructureTFComponent;
import twilightforest.structures.lichtower.ComponentTFTowerRoof;
import twilightforest.structures.lichtower.ComponentTFTowerWing;

public class ComponentTFDarkTowerRoof extends ComponentTFTowerRoof 
{
	public ComponentTFDarkTowerRoof() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ComponentTFDarkTowerRoof(int i, ComponentTFTowerWing wing) {
		super(i, wing);

		// same alignment
		this.setCoordBaseMode(wing.getCoordBaseMode());
		// same size
		this.size = wing.size; // assuming only square towers and roofs right now.
		this.height = 12;
		
		// just hang out at the very top of the tower
		makeCapBB(wing);
		
		// spawn list!
		this.spawnListIndex = 1;
	}
	
	@Override
	public void buildComponent(StructureComponent parent, List list, Random rand) 
	{
		if (parent != null && parent instanceof StructureTFComponent)
		{
			this.deco = ((StructureTFComponent)parent).deco;
		}
	}

	/**
	 * A fence around the roof!
	 */
	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) 
	{
		// fence
		for (int x = 0; x <= size - 1; x++) {
			for (int z = 0; z <= size - 1; z++) {
				if (x == 0 || x == size - 1 || z == 0 || z == size - 1) {
					placeBlockAtCurrentPosition(world, deco.fenceID, deco.fenceMeta, x, 1, z, sbb);
				}
			}
		}
		
		placeBlockAtCurrentPosition(world, deco.accentID, deco.accentMeta, 0, 1, 0, sbb);
		placeBlockAtCurrentPosition(world, deco.accentID, deco.accentMeta, size - 1, 1, 0, sbb);
		placeBlockAtCurrentPosition(world, deco.accentID, deco.accentMeta, 0, 1, size - 1, sbb);
		placeBlockAtCurrentPosition(world, deco.accentID, deco.accentMeta, size - 1, 1, size - 1, sbb);
		
		return true;
	}		

}

package twilightforest.entity.ai;

import java.util.List;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import twilightforest.entity.passive.EntityTFQuestRam;


public class EntityAITFFindLoose extends EntityAIBase {
	
    /** The entity using this AI that is tempted by the player. */
    private EntityCreature temptedEntity;
    
    private Item temptID;
    private float pursueSpeed;

	private int delayTemptCounter;

	private EntityItem temptingItem;

	public EntityAITFFindLoose(EntityTFQuestRam entityTFQuestRam, float speed, Item blockID) {
		this.temptedEntity = entityTFQuestRam;
		this.pursueSpeed = speed;
		this.temptID = blockID;
        this.setMutexBits(3);
	}

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
	@Override
	public boolean shouldExecute()
    {
        if (this.delayTemptCounter > 0)
        {
            --this.delayTemptCounter;
            return false;
        }
        else
        {
        	this.temptingItem = null;
        	
            List<EntityItem> nearbyItems = this.temptedEntity.worldObj.getEntitiesWithinAABB(EntityItem.class, this.temptedEntity.boundingBox.expand(16.0D, 4.0D, 16.0D));
            
            for (EntityItem itemNearby : nearbyItems) {
            	if (itemNearby.getEntityItem().getItem() == temptID && itemNearby.isEntityAlive()) {
            		this.temptingItem = itemNearby;
            		break;
            	}
            }
            
            if (this.temptingItem == null) {
            	return false;
            }
            else {
            	return true;
            }
        }
    }

    
    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
	public boolean continueExecuting()
    {
        return this.shouldExecute();
    }
    
    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
	public void startExecuting()
    {
    	;
    }

    /**
     * Resets the task
     */
    @Override
	public void resetTask()
    {
        this.temptingItem = null;
        this.temptedEntity.getNavigator().clearPathEntity();
        this.delayTemptCounter = 100;
    }

    /**
     * Updates the task
     */
    @Override
	public void updateTask()
    {
        this.temptedEntity.getLookHelper().setLookPositionWithEntity(this.temptingItem, 30.0F, this.temptedEntity.getVerticalFaceSpeed());

        if (this.temptedEntity.getDistanceSqToEntity(this.temptingItem) < 6.25D)
        {
            this.temptedEntity.getNavigator().clearPathEntity();
        }
        else
        {
            this.temptedEntity.getNavigator().tryMoveToXYZ(temptingItem.posX, temptingItem.posY, temptingItem.posZ, this.pursueSpeed);
        }
    }
}

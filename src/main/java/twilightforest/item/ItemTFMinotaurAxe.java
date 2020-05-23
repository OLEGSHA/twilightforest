package twilightforest.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import twilightforest.TwilightForestMod;

import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTFMinotaurAxe extends ItemAxe {

	public static final int BONUS_CHARGING_DAMAGE = 7;
	private Entity bonusDamageEntity;
	private EntityPlayer bonusDamagePlayer;
	private float damageVsEntity;

	protected ItemTFMinotaurAxe(Item.ToolMaterial par2EnumToolMaterial) {
		super(par2EnumToolMaterial);
		this.damageVsEntity = 4 + par2EnumToolMaterial.getDamageVsEntity();
		this.setCreativeTab(TFItems.creativeTab);
	}

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    @Override
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
    	ItemStack istack = new ItemStack(par1, 1, 0);
    	//istack.addEnchantment(Enchantment.efficiency, 2);
        par3List.add(istack);
    }
    
    /**
     * Called when the player Left Clicks (attacks) an entity.
     * Processed before damage is done, if return value is true further processing is canceled
     * and the entity is not attacked.
     * 
     * @param stack The Item being used
     * @param player The player that is attacking
     * @param entity The entity being attacked
     * @return True to cancel the rest of the interaction.
     */
    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) 
    {
    	// if the player is sprinting, keep the entity, we will do extra damage to it
    	if (player.isSprinting())
    	{
    		this.bonusDamageEntity = entity;
    		this.bonusDamagePlayer = player;
    	}
    	
        return false;
    }
    
    /**
     * Returns the damage against a given entity.
     */
    public float getDamageVsEntity(Entity par1Entity, ItemStack itemStack)
    {
       	if (this.bonusDamagePlayer != null && this.bonusDamageEntity != null && par1Entity == this.bonusDamageEntity)
       	{
       		//System.out.println("Minotaur Axe extra damage!");
       		this.bonusDamagePlayer.onEnchantmentCritical(par1Entity);
       		this.bonusDamagePlayer = null;
       		this.bonusDamageEntity = null;
       		return damageVsEntity + BONUS_CHARGING_DAMAGE;
       	}
       	else
       	{
       		return damageVsEntity;
       	}
    }
    
    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    @Override
    public int getItemEnchantability()
    {
        return Item.ToolMaterial.GOLD.getEnchantability();
    }
    
    /**
     * allows items to add custom lines of information to the mouseover description
     */
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		par3List.add(StatCollector.translateToLocal(getUnlocalizedName() + ".tooltip"));
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
    
    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
    public Multimap getItemAttributeModifiers()
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers();
        // remove old damage value
        multimap.removeAll(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName());
        // add new one
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", (double)this.damageVsEntity, 0));
        return multimap;
    }
}


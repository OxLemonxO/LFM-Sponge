package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemCarrotOnAStick extends Item
{
    public ItemCarrotOnAStick()
    {
        this.setCreativeTab(CreativeTabs.TRANSPORTATION);
        this.setMaxStackSize(1);
        this.setMaxDamage(25);
    }

    /**
     * Called when the equipped item is right clicked.
     */
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        if (playerIn.isRiding() && playerIn.getRidingEntity() instanceof EntityPig)
        {
            EntityPig entitypig = (EntityPig)playerIn.getRidingEntity();

            if (itemStackIn.getMaxDamage() - itemStackIn.getMetadata() >= 7 && entitypig.boost())
            {
                itemStackIn.damageItem(7, playerIn);

                if (itemStackIn.stackSize == 0)
                {
                    ItemStack itemstack = new ItemStack(Items.FISHING_ROD);
                    itemstack.setTagCompound(itemStackIn.getTagCompound());
                    return new ActionResult(EnumActionResult.SUCCESS, itemstack);
                }

                return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
            }
        }

        playerIn.addStat(StatList.getObjectUseStats(this));
        return new ActionResult(EnumActionResult.PASS, itemStackIn);
    }
}
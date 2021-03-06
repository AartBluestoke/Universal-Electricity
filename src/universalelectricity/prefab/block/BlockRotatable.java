package universalelectricity.prefab.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import universalelectricity.prefab.implement.IRotatable;

/**
 * A block that can rotate based on placed position and wrenching.
 * 
 * @author Calclavia
 * 
 */
public abstract class BlockRotatable extends BlockAdvanced implements IRotatable
{
	public BlockRotatable(int id, Material material)
	{
		super(id, material);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack)
	{
		int angle = MathHelper.floor_double((entityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		int change = 3;

		switch (angle)
		{
			case 0:
				change = 2;
				break;

			case 1:
				change = 5;
				break;

			case 2:
				change = 3;
				break;

			case 3:
				change = 4;
				break;
		}

		this.setDirection(world, x, y, z, ForgeDirection.getOrientation(change));
	}

	@Override
	public boolean onUseWrench(World world, int x, int y, int z, EntityPlayer par5EntityPlayer, int side, float hitX, float hitY, float hitZ)
	{
		this.rotateBlock(world, x, y, z, ForgeDirection.getOrientation(side));
		return true;
	}

    public static boolean rotateBlock(World worldObj, int x, int y, int z, ForgeDirection axis, int mask)
    {
        int rotMeta = worldObj.getBlockMetadata(x, y, z);
        int masked = rotMeta & ~mask;
        ForgeDirection orientation = ForgeDirection.getOrientation(rotMeta & mask);
        ForgeDirection rotated = orientation.getRotation(axis);
        worldObj.setBlockMetadataWithNotify(x,y,z,rotated.ordinal() & mask | masked,3);
        return true;
    }
    
	@Override
	public ForgeDirection getDirection(IBlockAccess world, int x, int y, int z)
	{
		return ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z));
	}

	@Override
	public void setDirection(World world, int x, int y, int z, ForgeDirection facingDirection)
	{
		world.setBlockMetadataWithNotify(x, y, z, facingDirection.ordinal(), 3);
	}
}
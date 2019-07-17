package com.mic.randomloot.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mic.randomloot.RandomLoot;
import com.mic.randomloot.init.ModBlocks;
import com.mic.randomloot.init.ModItems;
import com.mic.randomloot.util.IReforgeable;
import com.mic.randomloot.util.handlers.ConfigHandler;
import com.mic.randomloot.util.handlers.NetworkHandler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RandomAnvil extends BlockFalling {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	protected static final AxisAlignedBB X_AXIS_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.125D, 1.0D, 1.0D, 0.875D);
	protected static final AxisAlignedBB Z_AXIS_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.0D, 0.875D, 1.0D, 1.0D);
	protected static final Logger LOGGER = LogManager.getLogger();

	public RandomAnvil(String name, CreativeTabs tab) {
		super(Material.ANVIL);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		setUnlocalizedName(name); // Used for localization (en_US.lang)
		setRegistryName(name);
		this.setLightOpacity(0);
		setCreativeTab(tab);
		this.blockSoundType = SoundType.ANVIL;
		this.blockHardness = 15;
		this.blockResistance = 3.0f;
		this.setHarvestLevel("pickaxe", 1);

		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(name));
	}

	// @Override
	// public Item getItemDropped(IBlockState state, Random rand, int fortune) {
	// // TODO Auto-generated method stub
	// return new ItemBlock(this);
	// }

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state,
			int fortune) {

		// drops.add(new ItemStack(this));
		super.getDrops(drops, world, pos, state, fortune);
	}

	public boolean isFullCube(IBlockState state) {
		return false;
	}

	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}

	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer) {
		EnumFacing enumfacing = placer.getHorizontalFacing().rotateY();

		try {
			return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(FACING,
					enumfacing);
		} catch (IllegalArgumentException var11) {
			if (!worldIn.isRemote) {
				LOGGER.warn(String.format("Invalid damage property for anvil at %s. Found %d, must be in [0, 1, 2]",
						pos, meta >> 2));

				if (placer instanceof EntityPlayer) {
					placer.sendMessage(new TextComponentTranslation("Invalid damage property. Please pick in [0, 1, 2]",
							new Object[0]));
				}
			}

			return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, 0, placer).withProperty(FACING,
					enumfacing);
		}
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);
		return enumfacing.getAxis() == EnumFacing.Axis.X ? X_AXIS_AABB : Z_AXIS_AABB;
	}

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood
	 * returns 4 blocks)
	 */
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		items.add(new ItemStack(this));
		items.add(new ItemStack(this, 1, 1));
		items.add(new ItemStack(this, 1, 2));
	}

	protected void onStartFalling(EntityFallingBlock fallingEntity) {
		fallingEntity.setHurtEntities(true);
	}

	public void onEndFalling(World worldIn, BlockPos pos, IBlockState p_176502_3_, IBlockState p_176502_4_) {
		worldIn.playEvent(1031, pos, 0);
	}

	public void onBroken(World worldIn, BlockPos pos) {
		worldIn.playEvent(1029, pos, 0);
	}

	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos,
			EnumFacing side) {
		return true;
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 3));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	public int getMetaFromState(IBlockState state) {
		int i = 0;
		i = i | ((EnumFacing) state.getValue(FACING)).getHorizontalIndex();
		return i;
	}

	/**
	 * Returns the blockstate with the given rotation from the passed blockstate. If
	 * inapplicable, returns the passed blockstate.
	 */
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.getBlock() != this ? state
				: state.withProperty(FACING, rot.rotate((EnumFacing) state.getValue(FACING)));
	}

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (hand.equals(EnumHand.MAIN_HAND)) {
			if (worldIn.isRemote) {

				if (playerIn.inventory.getCurrentItem().getItem() instanceof IReforgeable) {
					ItemStack offHand = playerIn.getHeldItem(EnumHand.OFF_HAND);

					int count = ConfigHandler.reforgeItemCount;
					String itemName = ConfigHandler.reforgeItemName;
					Item itemToUse = Item.REGISTRY
							.getObject(new ResourceLocation(itemName.substring(0, itemName.indexOf(":")),
									itemName.substring(itemName.indexOf(':') + 1)));
					if ((offHand.getItem().equals(itemToUse) && offHand.getCount() > count) || count == 0) {
						NetworkHandler.reforge();
						playerIn.sendMessage(new TextComponentString("Item reforged."));
						return true;
					} else {
						playerIn.sendMessage(new TextComponentString(
								"Not enough " + itemName.substring(itemName.indexOf(':') + 1).replaceAll("_", " ")
										+ " in off-hand."));
						return true;
					}

				} else {

					playerIn.sendMessage(new TextComponentString("This item cannot be reforged."));
					return true;

				}
			}
		}
		// return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing,
		// hitX, hitY, hitZ);
		return true;
	}

//	@SideOnly(Side.CLIENT)
//	public void initModel() {
//		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0,
//				new ModelResourceLocation(getRegistryName(), "inventory"));
//	}

}

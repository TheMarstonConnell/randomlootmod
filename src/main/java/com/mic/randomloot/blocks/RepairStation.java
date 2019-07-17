package com.mic.randomloot.blocks;

import java.util.List;
import java.util.Random;

import com.mic.randomloot.RandomLoot;
import com.mic.randomloot.blocks.tileentities.RepairTileEntity;
import com.mic.randomloot.init.ModBlocks;
import com.mic.randomloot.init.ModItems;
import com.mic.randomloot.util.IReforgeable;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class RepairStation extends BlockContainer {

	public RepairStation(String name) {
		super(Material.IRON);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(RandomLoot.randomlootTab);
		this.setLightOpacity(0);
		this.blockSoundType = SoundType.STONE;
		this.blockHardness = 3;
		this.blockResistance = 1.0f;
		this.setHarvestLevel("pickaxe", 0);
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(name));
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		RepairTileEntity tileentity = (RepairTileEntity) worldIn.getTileEntity(pos);
		InventoryHelper.dropInventoryItems(worldIn, pos, tileentity);
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		RepairTileEntity rte = (RepairTileEntity) worldIn.getTileEntity(pos);

		
		
		if (rte.activated && worldIn.isBlockPowered(pos)) {
			System.out.println("Active");
			for (int countparticles = 0; countparticles <= 25; ++countparticles) {
				worldIn.spawnParticle(EnumParticleTypes.CRIT, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
						0.1D * getNegOrPos(), 0.1D * getNegOrPos(), 0.1D * getNegOrPos());
			}

			List<EntityPlayer> players = worldIn.playerEntities;
			for (EntityPlayer player : players) {
				worldIn.playSound(player, pos, SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.BLOCKS, 0.2F, 1.0F);
			}
		}
		super.randomDisplayTick(stateIn, worldIn, pos, rand);
	}
	
	@Override
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		// TODO Auto-generated method stub
		return true;
	}

	public int getNegOrPos() {
		Random rand = new Random();

		switch (rand.nextInt(3)) {
		case 0:
			return -1;
		case 1:
			return 1;
		case 2:
			return 0;
		}
		return 1;

	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
//		if (!worldIn.isRemote) {
			if (hand.equals(EnumHand.MAIN_HAND)) {
				System.out.println("Activating repair station");
				if (playerIn.inventory.getCurrentItem().getItem().isDamageable()
						|| playerIn.inventory.getCurrentItem().getItem().equals(Items.AIR)) {
					System.out.println("Putting item in");

					RepairTileEntity tileentity = (RepairTileEntity) worldIn.getTileEntity(pos);
					tileentity.addItem(playerIn, playerIn.inventory.getCurrentItem());
				} else {
					playerIn.sendMessage(new TextComponentString("Not a repairable item."));

				}
			}
//		}

		return true;
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		if (stack.hasDisplayName()) {
			TileEntity tileentity = worldIn.getTileEntity(pos);

			if (tileentity instanceof RepairTileEntity) {
				((RepairTileEntity) tileentity).setCustomName(stack.getDisplayName());
			}
		}
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new RepairTileEntity();
	}

}

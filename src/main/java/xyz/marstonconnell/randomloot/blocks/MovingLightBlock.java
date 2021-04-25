package xyz.marstonconnell.randomloot.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import xyz.marstonconnell.randomloot.entity.MovingLightTileEntity;
import xyz.marstonconnell.randomloot.utils.Registration;

import java.util.function.Supplier;

import javax.annotation.Nullable;

public class MovingLightBlock extends Block {


	public MovingLightBlock() {
		super(AbstractBlock.Properties.create(Material.AIR).zeroHardnessAndResistance().doesNotBlockMovement()
				.notSolid().setOpaque(MovingLightBlock::isntSolid));
	}
	
	@Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new MovingLightTileEntity();
    }

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {

	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return VoxelShapes.empty();
	}

	/**
	 * The type of render function called. MODEL for mixed tesr and static model,
	 * MODELBLOCK_ANIMATED for TESR-only, LIQUID for vanilla liquids, INVISIBLE to
	 * skip all rendering
	 * 
	 * @deprecated call via {@link IBlockState#getRenderType()} whenever possible.
	 *             Implementing/overriding is fine.
	 */
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.INVISIBLE;
	}

	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
		return 8;
	}

	private static boolean isntSolid(BlockState state, IBlockReader reader, BlockPos pos) {
		return false;
	}

}

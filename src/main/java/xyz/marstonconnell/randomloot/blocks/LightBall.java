package xyz.marstonconnell.randomloot.blocks;

import java.util.function.ToIntFunction;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class LightBall extends Block{

	public LightBall() {
		super(AbstractBlock.Properties.create(Material.ROCK).zeroHardnessAndResistance().doesNotBlockMovement().notSolid().setOpaque(LightBall::isntSolid));
	
	}
	
	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
		return 8;
	}
	
	private static boolean isntSolid(BlockState state, IBlockReader reader, BlockPos pos) {
	      return false;
	}
	
}



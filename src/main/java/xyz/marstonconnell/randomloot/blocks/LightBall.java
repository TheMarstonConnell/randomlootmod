package xyz.marstonconnell.randomloot.blocks;

import java.util.function.ToIntFunction;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class LightBall extends Block{

	public LightBall() {
		super(BlockBehaviour.Properties.of(Material.STONE).explosionResistance(0).instabreak().noCollission());
	
	}
	
	@Override
	public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
		return 8;
	}
	
	
	
}



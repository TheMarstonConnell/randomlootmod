package xyz.marstonconnell.randomloot.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import xyz.marstonconnell.randomloot.RandomLootMod;
import xyz.marstonconnell.randomloot.container.RLRepairContainer;
import xyz.marstonconnell.randomloot.utils.Registration;

public class RLAnvil extends Block{
	   private static final TranslationTextComponent field_235575_a_ = new TranslationTextComponent("container.randomloot.edit");

	   
	public RLAnvil() {
		super(AbstractBlock.Properties.create(Material.ANVIL, MaterialColor.IRON));
	}
	
	public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
	      return new SimpleNamedContainerProvider((p_235576_2_, p_235576_3_, p_235576_4_) -> {
	         return new RLRepairContainer(Registration.EDITOR_CONTAINER.get(), p_235576_2_, p_235576_3_, IWorldPosCallable.of(worldIn, pos));
	      }, field_235575_a_);
	   }
	
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
	      if (worldIn.isRemote) {
	         return ActionResultType.SUCCESS;
	      } else {
	    	  
	    	  System.out.println("Opening Editor");
	    	  
	         player.openContainer(state.getContainer(worldIn, pos));
	         return ActionResultType.CONSUME;
	      }
	   }
	
}

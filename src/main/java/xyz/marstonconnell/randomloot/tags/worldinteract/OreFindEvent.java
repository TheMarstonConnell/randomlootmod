package xyz.marstonconnell.randomloot.tags.worldinteract;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.ShulkerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.marstonconnell.randomloot.RandomLootMod;
import xyz.marstonconnell.randomloot.tags.WorldInteractEvent;

@Mod.EventBusSubscriber(modid = RandomLootMod.MODID)
public class OreFindEvent extends WorldInteractEvent{

	static int maxTime = 10;
	static int time = 0;
	static int maxShulkerLife = 10;
	
	private static ArrayList<ShulkerEntity> shulkers = new ArrayList<ShulkerEntity>();
	private static ArrayList<Integer> timings = new ArrayList<Integer>();

	@SubscribeEvent
	public static void tickEvent(ServerTickEvent event) {
		time ++;
		time = time % maxTime;
		
		
		if(time == 0) {
			int off = 0;
			for(int i = 0; i < shulkers.size(); i ++) {
				int iOff = i - off;
				int tick = timings.get(iOff) + 1;
				timings.set(iOff, tick);
				ShulkerEntity sh = shulkers.get(iOff);
				
				if(tick > maxShulkerLife || sh.getEntityWorld().getBlockState(sh.getPosition()).getBlock().equals(Blocks.AIR)) {
					shulkers.get(iOff).setPosition(0, -64, 0);
					shulkers.get(iOff).setHealth(0);
					shulkers.remove(iOff);
					timings.remove(iOff);
					off ++;
				}
			}
		}
	}

	@Override
	public void effect(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state,
			BlockPos pos, Entity target) {

		String name = state.getBlock().getRegistryName().getPath();
		
		if(name.contains("ore") || name.contains("Ore")) {
			return;
		}
		
		int size = 10;
		for(int i = -size; i < size; i ++) {
			for(int j = -size; j < size; j ++) {
				for(int k = -size; k < size; k ++) {
					BlockPos p = new BlockPos(pos.getX() + i, pos.getY() + j , pos.getZ() + k);
					Block b = worldIn.getBlockState(p).getBlock();
					name = b.getRegistryName().getPath();
					
					if(name.contains("ore") || name.contains("Ore")) {
						System.out.println("Block is Ore!");
						
						ShulkerEntity se = new ShulkerEntity(EntityType.SHULKER, worldIn);
						se.setGlowing(true);
						se.setInvulnerable(true);
						se.setInvisible(true);
						se.setPositionAndRotation(p.getX(), p.getY(), p.getZ(), 0, 0);
						se.setNoAI(true);
						worldIn.addEntity(se);
						se.addPotionEffect(new EffectInstance(Effects.INVISIBILITY, 1200, 0, false ,false));


						shulkers.add(se);
						timings.add(-1);
						
						
					}
				}
			}
		}
		
		
		
		
		
	}

	@Override
	public void onAdd(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state,
			BlockPos pos, Entity target) {
		// TODO Auto-generated method stub
		
	}
	
}

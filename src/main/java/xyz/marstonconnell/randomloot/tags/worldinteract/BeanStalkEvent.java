package xyz.marstonconnell.randomloot.tags.worldinteract;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import xyz.marstonconnell.randomloot.RandomLootMod;
import xyz.marstonconnell.randomloot.tags.WorldInteractEvent;

@Mod.EventBusSubscriber(modid = RandomLootMod.MODID)
public class BeanStalkEvent extends WorldInteractEvent {

	static int timer = 0;
	static int height = 3;
	static int maxTime = 5;

	static List<BlockPos> spots = new ArrayList<BlockPos>();
	static List<Integer> heights = new ArrayList<Integer>();
	static List<World> worlds = new ArrayList<World>();
	static List<Entity> targets = new ArrayList<Entity>();

	@SubscribeEvent
	public static void tickEvent(ServerTickEvent event) {
		if (event.side == LogicalSide.SERVER) {

			timer++;

			if (timer > maxTime) {
				timer = 0;

				int off = 0;
				for (int i = 0; i < spots.size(); i++) {
					int iOff = i - off;

					System.out.println("Applying Stem at" + spots.get(iOff));
					heights.set(iOff, heights.get(iOff) + 1);

					if (heights.get(iOff) > height) {
						heights.remove(iOff);
						worlds.remove(iOff);
						spots.remove(iOff);
						targets.remove(iOff);
						off++;
						System.out.println("Finished growth!");
						continue;
					}

					BlockPos pos = spots.get(iOff);

					BlockPos newPos = new BlockPos(pos.getX(), pos.getY() + heights.get(iOff), pos.getZ());
					if (worlds.get(iOff).getBlockState(newPos).getBlock().equals(Blocks.AIR)) {
						worlds.get(iOff).setBlockState(newPos, Blocks.OAK_LEAVES.getDefaultState());
						if (targets.get(iOff).isAlive()) {
							targets.get(iOff).setPosition(newPos.getX(), newPos.getY() + 1, newPos.getZ());
						}
					}
				}

			}

		}
	}

	@Override
	public void effect(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state,
			BlockPos pos, Entity target) {
		if(target == null) {
			return;
		}
		spots.add(target.getPosition());
		heights.add(-1);
		worlds.add(worldIn);
		targets.add(target);

	}

	@Override
	public void onAdd(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state,
			BlockPos pos, Entity target) {
		// TODO Auto-generated method stub
		
	}

}

package xyz.marstonconnell.randomloot.tags.worldinteract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ibm.icu.text.DecimalFormat;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import xyz.marstonconnell.randomloot.RandomLootMod;
import xyz.marstonconnell.randomloot.tags.WorldInteractEvent;
import xyz.marstonconnell.randomloot.tools.BaseTool;

@Mod.EventBusSubscriber(modid = RandomLootMod.MODID)
public class ChargingEvent extends WorldInteractEvent{

	
	
	static int maxCharge = 5;
	static int time = 0;
	static int maxTime = 100;
	
	static Map<String, Float> extras;
	
	
	static ArrayList<ItemStack> items = new ArrayList<ItemStack>();
	
	public void addExtras(Map<String, Float> extrasIn) {
		extras = extrasIn;
	}
	
	@SubscribeEvent
	public static void tickEvent(ServerTickEvent event) {
		if (event.side == LogicalSide.SERVER) {

			
			
			time++;

			if (time > maxTime) {
				time = 0;

				for(ItemStack s : items) {
					float charge = BaseTool.getFloatNBT(s, "rl_charge") + 1;
					if(charge > maxCharge) {
						charge = maxCharge;
					}
					
					BaseTool.setFloatNBT(s, "rl_charge", charge);
					DecimalFormat df = new DecimalFormat("#0");
					BaseTool.setLore(s, TextFormatting.AQUA + df.format(charge / maxCharge * 100) + "% charged", null);
				}

			}

		}
	}
	
	@Override
	public void effect(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state,
			BlockPos pos, LivingEntity target) {

			if(!items.contains(stack)) {
				onAdd(level, stack, worldIn, entityLiving, state, pos, target);
			}
		
			float chargeLevel = BaseTool.getFloatNBT(stack, "rl_charge");
			
			target.hurtResistantTime = 0;
			target.hurtTime = 0;

			target.attackEntityFrom(new IndirectEntityDamageSource("indirectMagic", entityLiving, null).setDamageBypassesArmor().setMagicDamage(), chargeLevel);
			BaseTool.setFloatNBT(stack, "rl_charge", 0.0f);

		
	}

	@Override
	public void onAdd(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state,
			BlockPos pos, LivingEntity target) {
		
		items.add(stack);
		BaseTool.setFloatNBT(stack, "rl_charge", 0.0f);
		
		
	}

}

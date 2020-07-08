package xyz.marstonconnell.randomloot.tools;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.loading.FMLEnvironment;
import xyz.marstonconnell.randomloot.init.RLItems;

public class TextureProxy {

	public static void setModelProperties(RLShootableItem rlShootableItem) {
		if(FMLEnvironment.dist == Dist.CLIENT) {
			ItemModelsProperties.func_239418_a_(rlShootableItem, new ResourceLocation("model"), new IItemPropertyGetter() {

				@Override
				public float call(ItemStack stack, ClientWorld p_call_2_, LivingEntity p_call_3_) {
					float model = 1.0F;

					model = (float) rlShootableItem.getTexture(stack);

					return model;
				}
			});
			
			if(rlShootableItem instanceof RLBowItem) {
				System.out.println("Texturing bow");
				
				ItemModelsProperties.func_239418_a_(rlShootableItem, new ResourceLocation("rl_pull"), new IItemPropertyGetter() {

					@Override
					public float call(ItemStack stack, ClientWorld world, LivingEntity entity) {
						float pull = 0.0F;

						if (entity == null) {
							return pull;
						} else {
							ItemStack itemstack = entity.getActiveItemStack();
							pull = itemstack != null && itemstack.getItem() == RLItems.random_bow
									? (float) (stack.getUseDuration() - entity.getItemInUseCount()) / 20.0F : 0.0F;

							return pull;
						}
					}
				});
				
				ItemModelsProperties.func_239418_a_(rlShootableItem, new ResourceLocation("rl_pulling"), new IItemPropertyGetter() {

					@Override
					public float call(ItemStack stack, ClientWorld world, LivingEntity entity) {
						return entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack ? 1.0F : 0.0F;
					}
				});
			}
			
		}
		
		
		
	}
	
	public static void setModelProperties(BaseTool rlShootableItem) {
		if(FMLEnvironment.dist == Dist.CLIENT) {
			ItemModelsProperties.func_239418_a_(rlShootableItem, new ResourceLocation("model"), new IItemPropertyGetter() {

				@Override
				public float call(ItemStack stack, ClientWorld p_call_2_, LivingEntity p_call_3_) {
					float model = 1.0F;

					model = (float) rlShootableItem.getTexture(stack);

					return model;
				}
			});
			
			
				
	
			
		}
		
		
		
	}
}

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

public class TextureProxy {

	public static void setModelProperties(BaseTool i) {
		if(FMLEnvironment.dist == Dist.CLIENT) {
			ItemModelsProperties.func_239418_a_(i, new ResourceLocation("model"), new IItemPropertyGetter() {

				@Override
				public float call(ItemStack stack, ClientWorld p_call_2_, LivingEntity p_call_3_) {
					float model = 1.0F;

					model = (float) i.getTexture(stack);

					return model;
				}
			});
		}
		
	}
}

package com.mic.randomloot.util.handlers;

import com.mic.randomloot.entity.projectile.ThrowableWeaponEntity;
import com.mic.randomloot.init.ModItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderHandler 
{
	public static void registerEntityRenders()
	{
		RenderingRegistry.registerEntityRenderingHandler(ThrowableWeaponEntity.class, new IRenderFactory<ThrowableWeaponEntity>()
		{
			@Override
			public Render<? super ThrowableWeaponEntity> createRenderFor(RenderManager manager) 
			{
				return new RenderSnowball<ThrowableWeaponEntity>(manager, ModItems.RANDOM_BOOTS, Minecraft.getMinecraft().getRenderItem());
				
			}
		});
	}
	
	
}
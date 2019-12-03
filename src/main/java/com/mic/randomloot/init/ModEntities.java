package com.mic.randomloot.init;

import com.mic.randomloot.RandomLoot;
import com.mic.randomloot.entity.projectile.ThrowableWeaponEntity;
import com.mic.randomloot.util.handlers.ConfigHandler;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities 
{
	public static void registerEntities()
	{
		registerEntity("throwing", ThrowableWeaponEntity.class, ConfigHandler.throwingID, 50, 11437146, 000000);
	}
	
	private static void registerEntity(String name, Class<? extends Entity> entity, int id, int range, int color1, int color2)
	{
		EntityRegistry.registerModEntity(new ResourceLocation(RandomLoot.MODID + ":" + name), entity, name, id, RandomLoot.instance, range, 1, true, color1, color2);
	}
}
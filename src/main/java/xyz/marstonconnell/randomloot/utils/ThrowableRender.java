package xyz.marstonconnell.randomloot.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.util.ResourceLocation;
import xyz.marstonconnell.randomloot.entity.ThrowableWeaponEntity;

public class ThrowableRender extends SpriteRenderer<ThrowableWeaponEntity>{

	public ThrowableRender(EntityRendererManager renderManagerIn, ItemRenderer itemRenderer) {
		super(renderManagerIn, itemRenderer);
	}
	
	public ThrowableRender(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, Minecraft.getInstance().getItemRenderer());
	}
	
	
	

}

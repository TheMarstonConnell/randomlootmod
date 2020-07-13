package xyz.marstonconnell.randomloot.utils;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import xyz.marstonconnell.randomloot.entity.ThrowableWeaponEntity;

public class ThrowableRender extends ArrowRenderer<ThrowableWeaponEntity>{

	public ThrowableRender(EntityRendererManager renderManagerIn) {
		super(renderManagerIn);
	}

	@Override
	public ResourceLocation getEntityTexture(ThrowableWeaponEntity entity) {
		return null;
	}

}

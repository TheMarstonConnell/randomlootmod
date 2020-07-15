package xyz.marstonconnell.randomloot.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.marstonconnell.randomloot.init.RLEntities;

public class ThrowableWeaponEntity extends TridentEntity implements IRendersAsItem {

	ItemStack stack;
	
	
	public ThrowableWeaponEntity(EntityType<? extends ThrowableWeaponEntity> type, World worldIn) {
		super(type, worldIn);
	}

	public ThrowableWeaponEntity(World worldIn, PlayerEntity playerentity, ItemStack stack) {
	      super(RLEntities.THROWABLE_WEAPON, worldIn);
	      this.setShooter(playerentity);
	      this.setPosition(playerentity.getPosX(), playerentity.getPosYEye() - (double)0.1F, playerentity.getPosZ());
	
	}
	
	
	@OnlyIn(Dist.CLIENT)
	   public ThrowableWeaponEntity(World worldIn, double x, double y, double z) {
	      super(RLEntities.THROWABLE_WEAPON, worldIn);
	   }
	
	public void setItem(ItemStack stack) {
		this.stack = stack;
	}
	

	@Override
	public ItemStack getItem() {
		return stack;
	}

	



}

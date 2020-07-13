package xyz.marstonconnell.randomloot.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import xyz.marstonconnell.randomloot.init.RLEntities;
import xyz.marstonconnell.randomloot.init.RLItems;

public class ThrowableWeaponEntity extends ArrowEntity {

	public ThrowableWeaponEntity(EntityType<? extends ArrowEntity> p_i50159_1_, World p_i50159_2_) {
		super(p_i50159_1_, p_i50159_2_);
	}

	public ThrowableWeaponEntity(World worldIn) {
		super(RLEntities.THROWABLE_WEAPON, worldIn);
	}


	public ThrowableWeaponEntity(World worldIn, LivingEntity shooter) {
		this(worldIn);
		this.setShooter(shooter);
	}

	private float damage = 0;
	public ItemStack item;

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		super.tick();
		System.out.println();
	}

	@Override
	public void onAddedToWorld() {
		super.onAddedToWorld();
		System.out.println("Added to world successfully");
	}



	

	protected Item getDefaultItem() {
		return RLItems.THROWABLE_ITEM;
	}

	public void setDamageToDeal(float damageToDeal) {
		this.damage = damageToDeal;
	}



}

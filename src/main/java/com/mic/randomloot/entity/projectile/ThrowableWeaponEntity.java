package com.mic.randomloot.entity.projectile;

import com.mic.randomloot.init.ModItems;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ThrowableWeaponEntity extends EntityThrowable {

	float damageToDeal = 1.0f;
	private Item thisAsItem;

	public ThrowableWeaponEntity(World worldIn) {
		super(worldIn);
		this.setThisAsItem(ModItems.THROWABLE);
	}

	public void setDamageToDeal(float damageToDeal) {
		this.damageToDeal = damageToDeal;
	}
	
	public ThrowableWeaponEntity(World worldIn, Item asItem) {
		super(worldIn);
		this.setThisAsItem(asItem);
	}

	public ThrowableWeaponEntity(World worldIn, EntityLivingBase throwerIn) {
		super(worldIn, throwerIn);
	}

	public ThrowableWeaponEntity(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}

	public static void registerFixesEgg(DataFixer fixer) {
		EntityThrowable.registerFixesThrowable(fixer, "ThrownWeapon");
	}

	/**
	 * Handler for {@link World#setEntityState}
	 */
	@SideOnly(Side.CLIENT)
	public void handleStatusUpdate(byte id) {
		if (id == 3) {
			double d0 = 0.08D;

			for (int i = 0; i < 8; ++i) {
				this.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, this.posX, this.posY, this.posZ,
						((double) this.rand.nextFloat() - 0.5D) * 0.08D,
						((double) this.rand.nextFloat() - 0.5D) * 0.08D,
						((double) this.rand.nextFloat() - 0.5D) * 0.08D, Item.getIdFromItem(this.getThisAsItem()));
			}
		}
	}

	/**
	 * Called when this EntityThrowable hits a block or entity.
	 */
	protected void onImpact(RayTraceResult result) {
		if (result.entityHit != null) {
			result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()),
					this.damageToDeal);
		}

		if (!this.world.isRemote) {

			this.dropItem(this.thisAsItem, 1);

			this.world.setEntityState(this, (byte) 3);
			this.setDead();
		}
	}

	public Item getThisAsItem() {
		return thisAsItem;
	}

	public void setThisAsItem(Item thisAsItem) {
		this.thisAsItem = thisAsItem;
	}
}
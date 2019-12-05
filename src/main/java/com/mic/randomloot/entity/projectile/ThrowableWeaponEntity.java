package com.mic.randomloot.entity.projectile;

import java.util.List;

import com.mic.randomloot.init.ModItems;
import com.mic.randomloot.items.ThrowableWeapon;
import com.mic.randomloot.tags.BasicTag;
import com.mic.randomloot.tags.EffectTag;
import com.mic.randomloot.tags.TagHelper;
import com.mic.randomloot.tags.WorldInteractTag;

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
	private ItemStack thisAsItem;

	public ThrowableWeaponEntity(World worldIn) {
		super(worldIn);

		this.setThisAsItem(new ItemStack(ModItems.THROWABLE));
	}

	public void setDamageToDeal(float damageToDeal) {
		this.damageToDeal = damageToDeal;
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
						((double) this.rand.nextFloat() - 0.5D) * 0.08D,
						Item.getIdFromItem(this.getThisAsItem().getItem()));
			}
		}
	}

	/**
	 * Called when this EntityThrowable hits a block or entity.
	 */
	protected void onImpact(RayTraceResult result) {
		List<BasicTag> tags = TagHelper.getAllTags(this.getThisAsItem());

		if (result.entityHit != null) {

			for (int i = 0; i < tags.size(); i++) {
				if (tags.get(i) instanceof EffectTag) {
					EffectTag eTag = (EffectTag) tags.get(i);
					if (eTag.offensive) {
						eTag.runEffect(this.getThisAsItem(), this.getEntityWorld(),
								(EntityLivingBase) result.entityHit);
					} else {
						eTag.runEffect(this.getThisAsItem(), this.getEntityWorld(), this.getThrower());

					}

				}

				if (this.getThisAsItem().getItem().equals(ModItems.THROWABLE)) {
					ItemStack item = this.getThisAsItem();
					ThrowableWeapon throwable = (ThrowableWeapon) item.getItem();
					throwable.setLore(this.getThisAsItem(), (EntityPlayer) this.getThrower());
					throwable.setName(item);
				}

				result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()),
						this.damageToDeal);
			}

		}
		if (!this.world.isRemote) {
			for (int i = 0; i < tags.size(); i++) {
				if (tags.get(i) instanceof WorldInteractTag) {
					WorldInteractTag eTag = (WorldInteractTag) tags.get(i);

					eTag.runEffect(this.getThisAsItem(), this.getEntityWorld(), this.getThrower(),
							this.getEntityWorld().getBlockState(this.getPosition()), this.getPosition());

				}
			}
			this.world.setEntityState(this, (byte) 3);
			this.setDead();
		}
	}

	public ItemStack getThisAsItem() {
		return thisAsItem;
	}

	public void setThisAsItem(ItemStack thisAsItem) {
		this.thisAsItem = thisAsItem;
	}
}
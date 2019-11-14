package com.mic.randomloot.items;

import java.text.DecimalFormat;
import java.util.Random;

import javax.annotation.Nullable;

import com.mic.randomloot.RandomLoot;
import com.mic.randomloot.init.ItemFields;
import com.mic.randomloot.init.ModItems;
import com.mic.randomloot.util.IRandomTool;
import com.mic.randomloot.util.IReforgeable;
import com.mic.randomloot.util.handlers.ConfigHandler;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BowItem extends ItemBow implements IReforgeable, IRandomTool {
	static int bows;
	private static int useTime = 72000;
	static int tCount = 11;

	public BowItem(int numBows) {
		bows = numBows;
		setCreativeTab(RandomLoot.randomlootTab);
		setRegistryName(new ResourceLocation(RandomLoot.MODID, "rl_bow"));
		setUnlocalizedName("rl_bow");
		ModItems.ITEMS.add(this);

		this.addPropertyOverride(new ResourceLocation("model"), new IItemPropertyGetter() {
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) {
				float model = 1.0F;

				model = (float) ItemFields.getTexture(stack);

				return model;
			}
		});
		this.addPropertyOverride(new ResourceLocation("rl_pull"), new IItemPropertyGetter() {
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) {
				float pull = 0.0F;

				if (entity == null) {
					return pull;
				} else {
					ItemStack itemstack = entity.getActiveItemStack();
					pull = itemstack != null && itemstack.getItem() == ModItems.RL_BOW
							? (float) (stack.getMaxItemUseDuration() - entity.getItemInUseCount()) / 20.0F : 0.0F;

					return pull;
				}
			}
		});
		this.addPropertyOverride(new ResourceLocation("rl_pulling"), new IItemPropertyGetter() {
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) {
				return entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack ? 1.0F : 0.0F;
			}
		});
	}
	
	public static ItemStack chooseTexture(ItemStack stack) {
		Random rand = new Random();
		NBTTagCompound nbt;
		if (stack.hasTagCompound()) {
			nbt = stack.getTagCompound();
		} else {
			nbt = new NBTTagCompound();
		}
		nbt.setInteger("Texture", rand.nextInt(bows) + 1);
		stack.setTagCompound(nbt);
		
		return stack;

	}
	
	@Override
	public boolean isRepairable() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public Item setNoRepair() {
		// TODO Auto-generated method stub
		return super.setNoRepair();
	}
	public static float getVelo(ItemStack stack) {
		NBTTagCompound nbt;
		if (stack.hasTagCompound()) {
			nbt = stack.getTagCompound();
		} else {
			nbt = new NBTTagCompound();
		}

		
		return nbt.getFloat("velo");
		
		
	}

	public void setVelo(float digSpeed, ItemStack stack) {
		NBTTagCompound nbt;
		if (stack.hasTagCompound()) {
			nbt = stack.getTagCompound();
		} else {
			nbt = new NBTTagCompound();
		}

		
		nbt.setFloat("velo", digSpeed);
	}
	
	public void addVelo(float num, ItemStack stack) {
		setVelo(getVelo(stack) + num, stack);

	}

	public static ItemStack assignType(ItemStack stack) {
		Random rand = new Random();
		NBTTagCompound nbt;
		if (stack.hasTagCompound()) {
			nbt = stack.getTagCompound();
		} else {
			nbt = new NBTTagCompound();
		}

		int t1 = 0, t2 = 0, t3 = 0, traits = 0;
		int ts = rand.nextInt(8);
		switch (ts) {
		case 0:
		case 1:
			break;
		case 2:
		case 3:
		case 4:
			traits = 1;
			break;
		case 5:
		case 6:
			traits = 2;
			break;
		case 7:
			traits = 3;
			break;
		}

		if (traits == 1) {
			t1 = rand.nextInt(tCount) + 1;
		} else if (traits == 2) {
			t1 = rand.nextInt(tCount) + 1;

//			rand.setSeed(t1);
			t2 = rand.nextInt(tCount) + 1;

		} else if (traits == 3) {
			t1 = rand.nextInt(tCount) + 1;

//			rand.setSeed(t1);
			t2 = rand.nextInt(tCount) + 1;

//			rand.setSeed(t2);
			t3 = rand.nextInt(tCount) + 1;

		}

		if ((t1 == 9 || t2 == 9 || t3 == 9) && ConfigHandler.unbreakable) {
			nbt.setBoolean("Unbreakable", true);
		}

		nbt.setInteger("T1", t1);
		nbt.setInteger("T2", t2);
		nbt.setInteger("T3", t3);

		nbt.setInteger("Lvl", 1);
		nbt.setInteger("lvlXp", 256);
		nbt.setInteger("Xp", 0);
		rand.setSeed(rand.nextInt(256));
		nbt.setInteger("Texture", rand.nextInt(bows) + 1);
		nbt.setInteger("HideFlags", 2);

		stack.setTagCompound(nbt);

		return stack;
	}

	private ItemStack findAmmo(EntityPlayer player) {
		if (this.isArrow(player.getHeldItem(EnumHand.OFF_HAND))) {
			return player.getHeldItem(EnumHand.OFF_HAND);
		} else if (this.isArrow(player.getHeldItem(EnumHand.MAIN_HAND))) {
			return player.getHeldItem(EnumHand.MAIN_HAND);
		} else {
			for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
				ItemStack itemstack = player.inventory.getStackInSlot(i);

				if (this.isArrow(itemstack)) {
					return itemstack;
				}
			}

			return ItemStack.EMPTY;
		}
	}
	
	
	public static float getArrowVelocity(int charge)
    {
    	
    	
    	
        float f = (float)(charge) / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;

        if (f > 1.0F)
        {
            f = 1.0F;
        }

        return f;
    }
	

	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
		NBTTagCompound nbt;
		if (stack.hasTagCompound()) {
			nbt = stack.getTagCompound();
		} else {
			nbt = new NBTTagCompound();
		}

		int xp = nbt.getInteger("Xp");
		int lvlXp = nbt.getInteger("lvlXp");

		

		int t1 = nbt.getInteger("T1");
		int t2 = nbt.getInteger("T2");
		int t3 = nbt.getInteger("T3");

		stack.setTagCompound(nbt);
		setLore(stack, entityLiving);

		if (entityLiving instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) entityLiving;
			boolean flag = entityplayer.capabilities.isCreativeMode
					|| EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
			ItemStack itemstack = this.findAmmo(entityplayer);

			int i = this.getMaxItemUseDuration(stack) - timeLeft;
			i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, entityplayer, i,
					!itemstack.isEmpty() || flag);
			if (i < 0)
				return;

			if (!itemstack.isEmpty() || flag) {
				if (itemstack.isEmpty()) {
					itemstack = new ItemStack(Items.ARROW);
				}

				float f = getArrowVelocity(i);

				if ((double) f >= 0.1D) {
					boolean flag1 = entityplayer.capabilities.isCreativeMode
							|| (itemstack.getItem() instanceof ItemArrow
									&& ((ItemArrow) itemstack.getItem()).isInfinite(itemstack, stack, entityplayer));

					if (!worldIn.isRemote) {
						ItemArrow itemarrow = (ItemArrow) (itemstack.getItem() instanceof ItemArrow
								? itemstack.getItem() : Items.ARROW);
						EntityArrow entityarrow = itemarrow.createArrow(worldIn, itemstack, entityplayer);

						EntityTippedArrow tippedArrow;

						if (entityarrow instanceof EntityTippedArrow) {
							tippedArrow = (EntityTippedArrow) entityarrow;
							
						} else {
							tippedArrow = new EntityTippedArrow(worldIn, entityplayer);
						}
						tippedArrow.pickupStatus = EntityArrow.PickupStatus.DISALLOWED;
						if (t1 == 1 || t2 == 1 || t3 == 1) {
							tippedArrow.addEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 5 * 20, 1));
						}
						if (t1 == 2 || t2 == 2 || t3 == 2) {
							tippedArrow.addEffect(new PotionEffect(MobEffects.INSTANT_DAMAGE, 5 * 20, 0));
						}
						if (t1 == 3 || t2 == 3 || t3 == 3) {
							tippedArrow.addEffect(new PotionEffect(MobEffects.GLOWING, 5 * 20, 1));
						}
						if (t1 == 4 || t2 == 4 || t3 == 4) {
							tippedArrow.addEffect(new PotionEffect(MobEffects.WITHER, 5 * 20, 1));
						}
						if (t1 == 5 || t2 == 5 || t3 == 5) {
							tippedArrow.addEffect(new PotionEffect(MobEffects.WEAKNESS, 5 * 20, 1));
						}
						if (t1 == 6 || t2 == 6 || t3 == 6) {
							tippedArrow.addEffect(new PotionEffect(MobEffects.LEVITATION, 5 * 20, 1));
						}
						if (t1 == 7 || t2 == 7 || t3 == 7) {
							tippedArrow.addEffect(new PotionEffect(MobEffects.HUNGER, 5 * 20, 1));
						}
						if (t1 == 8 || t2 == 8 || t3 == 8) {
							tippedArrow.addEffect(new PotionEffect(MobEffects.SLOWNESS, 5 * 20, 1));
						}
						if (t1 == 10 || t2 == 10 || t3 == 10) {
							tippedArrow.addEffect(new PotionEffect(MobEffects.BLINDNESS, 5 * 20, 1));
						}
						if (t1 == 11 || t2 == 11 || t3 == 11) {
							tippedArrow.addEffect(new PotionEffect(MobEffects.POISON, 5 * 20, 1));
						}

						entityarrow = tippedArrow;

						entityarrow.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F,
								f * 3.0F, 1.0F);

						if (f == 1.0F) {
							entityarrow.setIsCritical(true);
						}

						int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);

						if (j > 0) {
							entityarrow.setDamage(entityarrow.getDamage() + (double) j * 0.5D + 0.5D);
						}

						int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);

						if (k > 0) {
							entityarrow.setKnockbackStrength(k);
						}

						if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0) {
							entityarrow.setFire(100);
						}

						stack.damageItem(1, entityplayer);

						if (flag1 || entityplayer.capabilities.isCreativeMode
								&& (itemstack.getItem() == Items.ARROW
										|| itemstack.getItem() == Items.ARROW)) {
							entityarrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
						}

						worldIn.spawnEntity(entityarrow);
					}

					worldIn.playSound((EntityPlayer) null, entityplayer.posX, entityplayer.posY, entityplayer.posZ,
							SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F,
							1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

					if (!flag1 && !entityplayer.capabilities.isCreativeMode) {
						itemstack.shrink(1);

						if (itemstack.isEmpty()) {
							entityplayer.inventory.deleteStack(itemstack);
						}
					}
					if (nbt.hasKey("Xp")) {
						nbt.setInteger("Xp", nbt.getInteger("Xp") + 1);
					} else {
						nbt.setInteger("Xp", 1);
					}

					if (xp >= lvlXp) {
						ModItems.ITEM_FIELDS.upgrade(stack, entityLiving);

					}
					entityplayer.addStat(StatList.getObjectUseStats(this));
				}
			}
		}
	}

	/**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack stack)
    {
    	NBTTagCompound compound;
		if (stack.hasTagCompound()) {
			compound = stack.getTagCompound();
		} else {
			compound = new NBTTagCompound();
		}
//		System.out.println(72000 - compound.getInteger("velo"));
		
		return 72000 - compound.getInteger("velo");
    }
	public void setName(ItemStack stack){
		NBTTagCompound compound;
		if (stack.hasTagCompound()) {
			compound = stack.getTagCompound();
		} else {
			compound = new NBTTagCompound();
		}

		TextFormatting color = null;
		switch (compound.getInteger("rarity")) {
			case 1:
				color = TextFormatting.WHITE;
				break;
			case 2:
				color = TextFormatting.GOLD;
				break;
			case 3:
				color = TextFormatting.LIGHT_PURPLE;
				break;

		}
		stack.setStackDisplayName(color + compound.getString("name"));

	}

	public void setLore(ItemStack stack, EntityLivingBase player) {


		NBTTagCompound compound;
		if (stack.hasTagCompound()) {
			compound = stack.getTagCompound();
		} else {
			compound = new NBTTagCompound();
		}
		
		TextFormatting color = null;
		switch (compound.getInteger("rarity")) {
		case 1:
			color = TextFormatting.WHITE;
			break;
		case 2:
			color = TextFormatting.GOLD;
			break;
		case 3:
			color = TextFormatting.LIGHT_PURPLE;
			break;
		}

		NBTTagList lore = new NBTTagList();
		int increase = (int)((getVelo(stack) / 72000.0) * 100);
//		System.out.println(increase);
//		System.out.println(increase + "%");
		lore.appendTag(new NBTTagString(TextFormatting.GRAY + "Bonus Speed: " + increase + "%"));
		DecimalFormat f = new DecimalFormat("##.00");
		// lore.appendTag(new NBTTagString(TextFormatting.GRAY + "Mining Speed:
		// " + f.format(getDigSpeed())));
		lore.appendTag(new NBTTagString(""));
		int t1 = compound.getInteger("T1");
		int t2 = compound.getInteger("T2");
		int t3 = compound.getInteger("T3");

		if (t1 == 1 || t2 == 1 || t3 == 1) {
			lore.appendTag(new NBTTagString(TextFormatting.DARK_RED + "Smite"));
		}
		if (t1 == 2 || t2 == 2 || t3 == 2) {
			lore.appendTag(new NBTTagString(TextFormatting.DARK_RED + "Instant Damage +"));
		}
		if (t1 == 3 || t2 == 3 || t3 == 3) {
			lore.appendTag(new NBTTagString(TextFormatting.YELLOW + "Glowing"));
		}
		if (t1 == 4 || t2 == 4 || t3 == 4) {
			lore.appendTag(new NBTTagString(TextFormatting.DARK_GRAY + "Wither"));
		}
		if (t1 == 5 || t2 == 5 || t3 == 5) {
			lore.appendTag(new NBTTagString(TextFormatting.DARK_GRAY + "Weakening"));
		}
		if (t1 == 6 || t2 == 6 || t3 == 6) {
			lore.appendTag(new NBTTagString(TextFormatting.GOLD + "Levitating"));
		}
		if (t1 == 7 || t2 == 7 || t3 == 7) {
			lore.appendTag(new NBTTagString(TextFormatting.DARK_RED + "Starving"));
		}
		if (t1 == 8 || t2 == 8 || t3 == 8) {
			lore.appendTag(new NBTTagString(TextFormatting.DARK_BLUE + "Slowing"));
		}
		if ((t1 == 9 || t2 == 9 || t3 == 9) && ConfigHandler.unbreakable) {
			lore.appendTag(new NBTTagString(TextFormatting.GRAY + "Fortified"));
		}
		if (t1 == 10 || t2 == 10 || t3 == 10) {
			lore.appendTag(new NBTTagString(TextFormatting.DARK_PURPLE + "Blinding"));
		}
		if (t1 == 11 || t2 == 11 || t3 == 11) {
			lore.appendTag(new NBTTagString(TextFormatting.DARK_GREEN + "Posionous"));
		}

		lore.appendTag(new NBTTagString(""));
		lore.appendTag(new NBTTagString(TextFormatting.GRAY + "Level " + compound.getInteger("Lvl")));
		lore.appendTag(new NBTTagString(
				TextFormatting.GRAY + "" + compound.getInteger("Xp") + "/" + compound.getInteger("lvlXp") + " Xp"));

		NBTTagCompound display = new NBTTagCompound();
		display.setTag("Lore", lore);
		compound.setTag("display", display);

//		stack.setStackDisplayName(color + compound.getString("name"));

	}

	@Override
	public ItemStack reforge(ItemStack stack) {
		Random rand = new Random();
		NBTTagCompound nbt;
		if (stack.hasTagCompound()) {
			nbt = stack.getTagCompound();
		} else {
			nbt = new NBTTagCompound();
		}

		int t1 = 0, t2 = 0, t3 = 0, traits = 0;

		nbt.setBoolean("Unbreakable", false);

		nbt.setInteger("T1", t1);
		nbt.setInteger("T2", t2);
		nbt.setInteger("T3", t3);

		nbt.setInteger("Lvl", 1);
		nbt.setInteger("lvlXp", 256);
		nbt.setInteger("Xp", 0);
		nbt.setInteger("HideFlags", 2);

		int rarity = nbt.getInteger("rarity");
//		System.out.println("Item rarity: "  + rarity);
		
		assignType(stack);
		
		
		BowItem bow = (BowItem) stack.getItem();
		int max = Items.BOW.getMaxItemUseDuration(new ItemStack(Items.BOW));

		switch (rarity) {
		case 1:
			bow.setVelo(max / 100 * 2 + rand.nextInt(max / 100 * 20), stack);
			break;
		case 2:
			bow.setVelo(max / 100 * 30 + rand.nextInt(max / 100 * 20), stack);
			break;
		case 3:
			bow.setVelo(max / 100 * 50 + rand.nextInt(max / 100 * 40), stack);
			break;

		}
//		System.out.println(bow.getVelo(stack));
		nbt.setFloat("velo", bow.getVelo(stack));
		nbt.setString("name", ModItems.ITEM_FIELDS.nameItem("bow"));


		return stack;
	}

	
}

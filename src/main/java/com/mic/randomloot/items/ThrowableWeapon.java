package com.mic.randomloot.items;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.mic.randomloot.RandomLoot;
import com.mic.randomloot.client.renderer.ThrownWeaponRender;
import com.mic.randomloot.entity.projectile.ThrowableWeaponEntity;
import com.mic.randomloot.init.ItemFields;
import com.mic.randomloot.init.ModItems;
import com.mic.randomloot.tags.BasicTag;
import com.mic.randomloot.tags.EffectTag;
import com.mic.randomloot.tags.TagHelper;
import com.mic.randomloot.tags.WorldInteractTag;
import com.mic.randomloot.util.IRandomTool;
import com.mic.randomloot.util.IReforgeable;
import com.mic.randomloot.util.WeightedChooser;
import com.mic.randomloot.util.handlers.ConfigHandler;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ThrowableWeapon extends ItemBase implements IReforgeable, IRandomTool {

	static int numThrowables = 1;
	int maxDamage = 64;

	public ThrowableWeapon(String name, int throwables) {
		super(name);
		numThrowables = throwables;
		addPropertyOverride(new ResourceLocation("model"), new IItemPropertyGetter() {
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) {
				float model = 1.0F;

				model = (float) ItemFields.getTexture(stack);

				return model;
			}
		});
	}
	
	public void setMaxDamage(ItemStack stack, int damage) {
		NBTTagCompound nbt;
		if (stack.hasTagCompound()) {
			nbt = stack.getTagCompound();
		} else {
			nbt = new NBTTagCompound();
		}
		nbt.setInteger("maxDamage", damage);
		stack.setTagCompound(nbt);
	}

	public int getNewMaxDamage(ItemStack stack) {
		NBTTagCompound nbt;
		if (stack.hasTagCompound()) {
			nbt = stack.getTagCompound();
		} else {
			nbt = new NBTTagCompound();
		}
		return nbt.getInteger("maxDamage");
		
	}
	
	@Override
	public int getMaxDamage() {
		
		return this.maxDamage;
	}

	@Override
	public boolean isDamageable() {
		// TODO Auto-generated method stub
		return true;
	}

	public ItemStack chooseTexture(ItemStack stack, int num) {
		Random rand = new Random();

		if(num == 0) {
			num = rand.nextInt(numThrowables) + 1;
		}
		
		NBTTagCompound nbt;
		if (stack.hasTagCompound()) {
			nbt = stack.getTagCompound();
		} else {
			nbt = new NBTTagCompound();
		}
		nbt.setInteger("Texture", num);
		stack.setTagCompound(nbt);

		return stack;

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

		lore.appendTag(new NBTTagString(TextFormatting.GRAY + "Attack Damage: " + compound.getInteger("damageToDeal")));
		lore.appendTag(new NBTTagString(""));

		List<BasicTag> tags = TagHelper.getAllTags(stack);
		for (int i = 0; i < tags.size(); i++) {

			String name = tags.get(i).name.replaceAll("_", " ");
			name = TagHelper.convertToTitleCaseIteratingChars(name);
			lore.appendTag(new NBTTagString(tags.get(i).color + name));
		}

//		lore.appendTag(new NBTTagString(TextFormatting.GRAY + "Damge: " + compound.getInteger("damageToDeal")));
		lore.appendTag(new NBTTagString(""));
		lore.appendTag(new NBTTagString(TextFormatting.GRAY + "Level " + compound.getInteger("Lvl")));
		lore.appendTag(new NBTTagString(
				TextFormatting.GRAY + "" + compound.getInteger("Xp") + "/" + compound.getInteger("lvlXp") + " Xp"));

		NBTTagCompound display = new NBTTagCompound();
		display.setTag("Lore", lore);
		compound.setTag("display", display);

		if (TagHelper.checkForTag(stack, TagHelper.UNBREAKABLE) && ConfigHandler.unbreakable) {
			compound.setBoolean("Unbreakable", true);
		} else {
			compound.setBoolean("Unbreakable", false);
		}

		// stack.setStackDisplayName(color + compound.getString("name"));

	}

	public void setName(ItemStack stack) {
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

	public static ItemStack assignType(ItemStack stack) {
		Random rand = new Random();
		NBTTagCompound nbt;
		if (stack.hasTagCompound()) {
			nbt = stack.getTagCompound();
		} else {
			nbt = new NBTTagCompound();
		}

		// nbt.setInteger("damageToDeal", rand.nextInt(8) + 4);

		nbt.setInteger("Lvl", 1);
		nbt.setInteger("lvlXp", 256);
		nbt.setInteger("Xp", 0);
		rand.setSeed(rand.nextInt(256));
		nbt.setInteger("HideFlags", 2);

		stack.setTagCompound(nbt);

		List<BasicTag> allowedTags = new ArrayList<BasicTag>();
		for (BasicTag tag : TagHelper.allTags) {
			if (tag instanceof EffectTag) {
				EffectTag eTag = (EffectTag) tag;
				if (eTag.forWeapons) {
					allowedTags.add(eTag);
				}
			} else if (tag instanceof WorldInteractTag) {
				WorldInteractTag eTag = (WorldInteractTag) tag;
				if (eTag.forWeapons) {
					allowedTags.add(eTag);
				}
			}

		}

		allowedTags.add(TagHelper.UNBREAKABLE);
		allowedTags.add(TagHelper.REPLENISH);
		allowedTags.add(TagHelper.EXPLOSION);

		WeightedChooser<Integer> wc = new WeightedChooser<Integer>();
		wc.addChoice(1, 6);
		wc.addChoice(2, 3);
		wc.addChoice(3, 1);

		// for(int i = 0; i < allowedTags.size(); i ++) {
		// System.out.println(allowedTags.get(i).name);
		// }

		int totalTags = wc.getRandomObject();
		// System.out.println("Total tags to be applied: " + totalTags);
		for (int i = 0; i < totalTags; i++) {
			BasicTag toAdd = allowedTags.get(RandomLoot.rand.nextInt(allowedTags.size()));
			while (TagHelper.checkForTag(stack, toAdd)) {
				toAdd = allowedTags.get(RandomLoot.rand.nextInt(allowedTags.size()));
				rand.setSeed(rand.nextLong() / 2 * totalTags * allowedTags.size() * i);
			}
			TagHelper.addTag(stack, toAdd.name);
			// System.out.println("Adding tag: " + toAdd.name);
		}

		if (TagHelper.checkForTag(stack, TagHelper.UNBREAKABLE) && ConfigHandler.unbreakable) {
			nbt.setBoolean("Unbreakable", true);
		}

		return stack;
	}

	/**
	 * Called when the equipped item is right clicked.
	 */
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		
		this.maxDamage = this.getNewMaxDamage(itemstack);
		
		
        boolean flag = true;

        ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, worldIn, playerIn, handIn, flag);
        if (ret != null) return ret;

        if (!playerIn.capabilities.isCreativeMode && !flag)
        {
            return flag ? new ActionResult(EnumActionResult.PASS, itemstack) : new ActionResult(EnumActionResult.FAIL, itemstack);
        }
        else
        {
            playerIn.setActiveHand(handIn);
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
        }

	}

	public static float getVelocity(int charge) {
		float f = (float) charge / 20.0F;
		f = (f * f + f * 2.0F) / 3.0F;

		if (f > 1.0F) {
			f = 1.0F;
		}

		return f;
	}
	
	/**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BOW;
    }
    
    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 72000;
    }

	/**
	 * Called when the player stops using an Item (stops holding the right mouse
	 * button).
	 */
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
		if (entityLiving instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) entityLiving;
			int i = this.getMaxItemUseDuration(stack) - timeLeft;

			float f = getVelocity(i);

			if ((double) f >= 0.1D) {
				ItemStack itemstack = entityplayer.getHeldItemMainhand();

				NBTTagCompound nbt;
				if (itemstack.hasTagCompound()) {
					nbt = itemstack.getTagCompound();
				} else {
					nbt = new NBTTagCompound();
				}

				int xp = nbt.getInteger("Xp");
				int lvlXp = nbt.getInteger("lvlXp");

				if (nbt.hasKey("Xp")) {
					nbt.setInteger("Xp", nbt.getInteger("Xp") + 1);
				} else {
					nbt.setInteger("Xp", 1);
				}

				if (xp >= lvlXp) {
					ModItems.ITEM_FIELDS.upgrade(stack, entityLiving);

				}

				stack.setTagCompound(nbt);
				
				float damageToDeal = nbt.getFloat("damageToDeal");

				if (!entityplayer.capabilities.isCreativeMode) {
					itemstack.damageItem(1, entityplayer);
				}

				worldIn.playSound((EntityPlayer) null, entityplayer.posX, entityplayer.posY, entityplayer.posZ,
						SoundEvents.ENTITY_ENDERPEARL_THROW, SoundCategory.PLAYERS, 0.5F,
						0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

				if (!worldIn.isRemote) {
					ThrowableWeaponEntity ent = new ThrowableWeaponEntity(worldIn, entityplayer);
					ent.setDamageToDeal(damageToDeal);
					ent.setThisAsItem(itemstack);
					ent.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, f * 1.5F, 1.0F);
					worldIn.spawnEntity(ent);
				}

				entityplayer.addStat(StatList.getObjectUseStats(this));

			}
		}
		setLore(stack, entityLiving);
		setName(stack);

	}

	@Override
	public ItemStack reforge(ItemStack stack) {
		Random rand = new Random();
		NBTTagCompound compound = (stack.hasTagCompound()) ? stack.getTagCompound() : new NBTTagCompound();

		double dam = 4;

		int rarity = compound.getInteger("rarity");

		switch (rarity) {
		case 1:
			dam = rand.nextDouble() * (ConfigHandler.tierOneDamageMax - ConfigHandler.tierOneDamageMin)
					+ ConfigHandler.tierOneDamageMax;
			break;
		case 2:
			dam = rand.nextDouble() * (ConfigHandler.tierTwoDamageMax - ConfigHandler.tierTwoDamageMin)
					+ ConfigHandler.tierTwoDamageMin;

			break;
		case 3:
			dam = rand.nextDouble() * (ConfigHandler.tierThreeDamageMax - ConfigHandler.tierThreeDamageMin)
					+ ConfigHandler.tierThreeDamageMin;

			break;

		}
		
		compound.setInteger("maxDamage", (int)((rarity * 128) / 2));

		compound.setFloat("damageToDeal", (float) (dam * 0.75));
		compound.setString("name", ModItems.ITEM_FIELDS.nameItem("throwable"));

		stack.setTagCompound(compound);
		return stack;
	}

}

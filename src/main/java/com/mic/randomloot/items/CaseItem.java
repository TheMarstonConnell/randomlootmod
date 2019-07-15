package com.mic.randomloot.items;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.collect.Multimap;
import com.mic.randomloot.RandomLoot;
import com.mic.randomloot.init.ItemFields;
import com.mic.randomloot.init.ModItems;
import com.mic.randomloot.util.handlers.ConfigHandler;
import com.mic.randomloot.util.handlers.NetworkHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CaseItem extends ItemBase {
	private static final UUID MODIFIER_UUID = UUID.fromString("294093da-54f0-4c1b-9dbb-13b77534a84c");

	static int level;
	int[] spawnChances = new int[4];

	/**
	 * Levels: <br>
	 * 1: basic <br>
	 * 2: golden <br>
	 * 3: titan <br>
	 * 
	 * @param name
	 * @param level
	 */
	public CaseItem(String name, int lvl) {
		super(name);
		level = lvl;
		this.setMaxStackSize(1);
		spawnChances[0] = ConfigHandler.playerChance;
		spawnChances[1] = ConfigHandler.animalChance;
		spawnChances[2] = ConfigHandler.mobChance;
		spawnChances[3] = ConfigHandler.bossChance;

	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		Random rand = new Random();

		if (worldIn.isRemote) {

			NetworkHandler.getNewItem();

		}

		for (int countparticles = 0; countparticles <= 25; ++countparticles) {
			worldIn.spawnParticle(EnumParticleTypes.CLOUD, playerIn.posX, playerIn.posY + 2, playerIn.posZ,
					0.1D * getNegOrPos(), 0.1D * getNegOrPos(), 0.1D * getNegOrPos());
		}
		worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.BLOCK_CLOTH_BREAK, SoundCategory.PLAYERS, 1.0F,
				1.0F);
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	private int getLVL() {
		return level;
	}

	public int getNegOrPos() {
		Random rand = new Random();

		switch (rand.nextInt(3)) {
		case 0:
			return -1;
		case 1:
			return 1;
		case 2:
			return 0;
		}
		return 1;

	}

	public static int rollRarity(Item i) {
		return ModItems.ITEM_FIELDS.rollRarity(i);
	}

	public static ItemStack getItem(World worldIn, EntityLivingBase player, Item i) {
		Random rand = new Random();
		boolean needsItem = true;
		int iType = 0;
		Item iChoice = null;
		while (needsItem) {
			rand.setSeed(rand.nextLong());
			iType = rand.nextInt(14);
			switch (iType) {
			case 0:
			case 1:
			case 2:
			case 3:
				if (ConfigHandler.swords) {
					iChoice = ModItems.RL_SWORD;
					needsItem = false;
				}
				break;
			case 4:
			case 5:
			case 6:
			case 7:
				if (ConfigHandler.pickaxes) {
					iChoice = ModItems.RL_PICKAXE;
					needsItem = false;
				}
				break;
			case 8:
				if (ConfigHandler.shovels) {
					iChoice = ModItems.RL_SHOVEL;
					needsItem = false;
				}
				break;
			case 9:
			case 10:
				if (ConfigHandler.axes) {
					iChoice = ModItems.RL_AXE;
					needsItem = false;
				}
				break;
			case 11:
			case 12:
			case 13:
				if (ConfigHandler.bows) {
					iChoice = ModItems.RL_BOW;
					needsItem = false;
				}
				break;
			}
		}

		ItemStack item = new ItemStack(iChoice);
		if (iChoice instanceof SwordItem) {
			item = SwordItem.assignType(item);
			item = SwordItem.chooseTexture(item);

		} else if (iChoice instanceof AxeItem) {
			item = AxeItem.assignType(item);

		} else if (iChoice instanceof PickaxeItem) {
			item = PickaxeItem.assignType(item);

		} else if (iChoice instanceof ShovelItem) {
			item = ShovelItem.assignType(item);

		} else if (iChoice instanceof BowItem) {
			item = BowItem.assignType(item);

		}

		item.getItem().addPropertyOverride(new ResourceLocation("model"), new IItemPropertyGetter() {
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) {
				float model = 1.0F;

				model = (float) ItemFields.getTexture(stack);

				return model;
			}
		});

		// rarirty of 1-3
		int rarity = rollRarity(i);

		
		
		
		// nbt
		NBTTagCompound compound = (item.hasTagCompound()) ? item.getTagCompound() : new NBTTagCompound();
		NBTTagList modifiers = new NBTTagList();

		compound.setInteger("rarity", rarity);
		
		
		if (iChoice.equals(ModItems.RL_SWORD)) {

			// damage
			NBTTagCompound damage = new NBTTagCompound();
			damage.setTag("AttributeName", new NBTTagString("generic.attackDamage"));
			damage.setTag("Name", new NBTTagString("generic.attackDamage"));
			int dmg = (int) ModItems.ITEM_FIELDS.calcDamage(rarity);
			damage.setTag("Amount", new NBTTagInt(dmg));
			damage.setTag("Operation", new NBTTagInt(0));
			damage.setTag("UUIDLeast", new NBTTagInt(3));
			damage.setTag("UUIDMost", new NBTTagInt(4));
			damage.setTag("Slot", new NBTTagString("mainhand"));

			// speed
			NBTTagCompound speed = new NBTTagCompound();
			speed.setTag("AttributeName", new NBTTagString("generic.attackSpeed"));
			speed.setTag("Name", new NBTTagString("generic.attackSpeed"));

			double spd = ModItems.ITEM_FIELDS.calcSpeed(rarity) * -1;
			speed.setTag("Amount", new NBTTagDouble(spd));
			speed.setTag("Operation", new NBTTagInt(0));
			speed.setTag("UUIDLeast", new NBTTagInt(1));
			speed.setTag("UUIDMost", new NBTTagInt(2));
			speed.setTag("Slot", new NBTTagString("mainhand"));
			compound.setInteger("damage", dmg);
			compound.setDouble("speed", spd);
			modifiers.appendTag(damage);
			modifiers.appendTag(speed);
			compound.setString("name", ModItems.ITEM_FIELDS.nameItem("sword"));

		} else if (iChoice.equals(ModItems.RL_PICKAXE)) {

			PickaxeItem pick = (PickaxeItem) item.getItem();
			switch (rarity) {
			case 1:
				pick.setDigSpeed(7 + rand.nextInt(6), item);
				break;
			case 2:
				pick.setDigSpeed(13 + rand.nextInt(5), item);
				break;
			case 3:
				pick.setDigSpeed(18 + rand.nextInt(5), item);
				break;

			}

			compound.setString("name", ModItems.ITEM_FIELDS.nameItem("pickaxe"));
		} else if (iChoice.equals(ModItems.RL_SHOVEL)) {

			ShovelItem shov = (ShovelItem) item.getItem();
			switch (rarity) {
			case 1:
				shov.setDigSpeed(7 + rand.nextInt(6), item);
				break;
			case 2:
				shov.setDigSpeed(13 + rand.nextInt(5), item);
				break;
			case 3:
				shov.setDigSpeed(18 + rand.nextInt(5), item);
				break;

			}

			compound.setString("name", ModItems.ITEM_FIELDS.nameItem("shovel"));
		} else if (iChoice.equals(ModItems.RL_AXE)) {

			AxeItem axe = (AxeItem) item.getItem();
			switch (rarity) {
			case 1:
				axe.setDigSpeed(7 + rand.nextInt(6), item);
				break;
			case 2:
				axe.setDigSpeed(13 + rand.nextInt(5), item);
				break;
			case 3:
				axe.setDigSpeed(18 + rand.nextInt(5), item);
				break;

			}

			compound.setString("name", ModItems.ITEM_FIELDS.nameItem("axe"));
		} else if (iChoice.equals(ModItems.RL_BOW)) {

			BowItem bow = (BowItem) item.getItem();
			int max = Items.BOW.getMaxItemUseDuration(new ItemStack(Items.BOW));

			switch (rarity) {
			case 1:
				bow.setVelo(max / 100 * 2 + rand.nextInt(max / 100 * 20), item);
				break;
			case 2:
				bow.setVelo(max / 100 * 30 + rand.nextInt(max / 100 * 20), item);
				break;
			case 3:
				bow.setVelo(max / 100 * 50 + rand.nextInt(max / 100 * 40), item);
				break;

			}
			System.out.println(bow.getVelo(item));
			compound.setFloat("velo", bow.getVelo(item));
			compound.setString("name", ModItems.ITEM_FIELDS.nameItem("bow"));
		}

		compound.setInteger("rarity", rarity);
		compound.setTag("AttributeModifiers", modifiers);
		item.setTagCompound(compound);

		if (iChoice.equals(ModItems.RL_SWORD))
			((SwordItem) iChoice).setLore(item, player);
		else if (iChoice.equals(ModItems.RL_PICKAXE))
			((PickaxeItem) iChoice).setLore(item, player);
		else if (iChoice.equals(ModItems.RL_SHOVEL))
			((ShovelItem) iChoice).setLore(item, player);
		else if (iChoice.equals(ModItems.RL_AXE))
			((AxeItem) iChoice).setLore(item, player);
		else if (iChoice.equals(ModItems.RL_BOW))
			((BowItem) iChoice).setLore(item, player);
		return item;

	}

	public int getBossDropWeight() {
		return spawnChances[3];
	}

	public int getPlayerDropWeight() {
		return spawnChances[0];
	}

	public int getPassiveDropWeight() {
		return spawnChances[1];
	}

	public int getMonsterDropWeight() {
		return spawnChances[2];
	}

	public ItemStack getItem() {

		Random rand = new Random();

		switch (rand.nextInt(12)) {
		case 0:
		case 1:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
			return new ItemStack(ModItems.BASIC_CASE);
		case 8:
		case 9:
		case 10:
			return new ItemStack(ModItems.GOLDEN_CASE);
		case 11:
			return new ItemStack(ModItems.TITAN_CASE);

		}

		return new ItemStack(ModItems.BASIC_CASE);
	}

}

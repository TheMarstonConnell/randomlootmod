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
import com.mic.randomloot.util.WeightedChooser;
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
import net.minecraft.nbt.NBTTagFloat;
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

		if (rand.nextInt(100) < ConfigHandler.chanceToDrop) {

			int iType = 0;

			WeightedChooser<Item> wc = new WeightedChooser<Item>();
			if (ConfigHandler.swords) {
				wc.addChoice(ModItems.RL_SWORD, ConfigHandler.swordWeight);
			}
			if (ConfigHandler.pickaxes) {
				wc.addChoice(ModItems.RL_PICKAXE, ConfigHandler.pickWeight);
			}
			if (ConfigHandler.axes) {
				wc.addChoice(ModItems.RL_AXE, ConfigHandler.axeWeight);
			}
			if (ConfigHandler.shovels) {
				wc.addChoice(ModItems.RL_SHOVEL, ConfigHandler.shovelWeight);
			}
			if (ConfigHandler.bows) {
				wc.addChoice(ModItems.RL_BOW, ConfigHandler.bowWeight);
			}
			if (ConfigHandler.paxels) {
				wc.addChoice(ModItems.RL_PAXEL, ConfigHandler.paxelWeight);
			}
			if (ConfigHandler.armor) {
				wc.addChoice(ModItems.RANDOM_BOOTS, ConfigHandler.armorWeight);
				wc.addChoice(ModItems.RANDOM_CHEST, ConfigHandler.armorWeight);
				wc.addChoice(ModItems.RANDOM_LEGS, ConfigHandler.armorWeight);
				wc.addChoice(ModItems.RANDOM_HELMET, ConfigHandler.armorWeight);
				wc.addChoice(ModItems.TITANIUM_BOOTS, ConfigHandler.armorWeight);
				wc.addChoice(ModItems.TITANIUM_CHEST, ConfigHandler.armorWeight);
				wc.addChoice(ModItems.TITANIUM_LEGS, ConfigHandler.armorWeight);
				wc.addChoice(ModItems.TITANIUM_HELMET, ConfigHandler.armorWeight);
			}
			if (ConfigHandler.throwables) {
				wc.addChoice(ModItems.THROWABLE, ConfigHandler.throwWeight);
			}
			Item iChoice = wc.getRandomObject();

			ItemStack item = new ItemStack(iChoice);
			if (iChoice instanceof SwordItem) {
				item = SwordItem.assignType(item);
				item = SwordItem.chooseTexture(item);

			} else if (iChoice instanceof AxeItem) {
				item = AxeItem.assignType(item);
				item = AxeItem.chooseTexture(item);

			} else if (iChoice instanceof PickaxeItem) {
				item = PickaxeItem.assignType(item);
				item = PickaxeItem.chooseTexture(item);

			} else if (iChoice instanceof PaxelItem) {
				item = PaxelItem.assignType(item);
				item = PaxelItem.chooseTexture(item);

			} else if (iChoice instanceof ShovelItem) {
				item = ShovelItem.assignType(item);
				item = ShovelItem.chooseTexture(item);

			} else if (iChoice instanceof BowItem) {
				item = BowItem.assignType(item);
				item = BowItem.chooseTexture(item);

			} else if (iChoice instanceof RandomArmor) {
				item = RandomArmor.assignType(item);
			} else if (iChoice instanceof ThrowableWeapon) {
				item = ThrowableWeapon.assignType(item);
				item = ThrowableWeapon.chooseTexture(item);
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

			NBTTagList modifiers;
			if (compound.hasKey("AttributeModifiers")) {
				modifiers = compound.getTagList("AttributeModifiers", 10);
			} else {
				modifiers = new NBTTagList();
			}

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
			} else if (iChoice.equals(ModItems.RL_PAXEL)) {

				PaxelItem pick = (PaxelItem) item.getItem();
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

				compound.setString("name", ModItems.ITEM_FIELDS.nameItem("paxel"));
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
				// System.out.println(bow.getVelo(item));
				compound.setFloat("velo", bow.getVelo(item));
				compound.setString("name", ModItems.ITEM_FIELDS.nameItem("bow"));
			} else if (iChoice.equals(ModItems.RANDOM_BOOTS) || iChoice.equals(ModItems.TITANIUM_BOOTS)) {
				double arm = 4;

				switch (rarity) {
					case 1:
						arm = rand.nextDouble() * (ConfigHandler.tierOneMaxArmor - ConfigHandler.tierOneMinArmor)
								+ ConfigHandler.tierOneMinArmor;
						break;
					case 2:
						arm = rand.nextDouble() * (ConfigHandler.tierTwoMaxArmor - ConfigHandler.tierTwoMinArmor)
								+ ConfigHandler.tierTwoMinArmor;

						break;
					case 3:
						arm = rand.nextDouble() * (ConfigHandler.tierThreeMaxArmor - ConfigHandler.tierThreeMinArmor)
								+ ConfigHandler.tierThreeMinArmor;

						break;

				}
				arm = arm * 0.8;
				NBTTagCompound armor = new NBTTagCompound();
				armor.setTag("AttributeName", new NBTTagString("generic.armor"));
				armor.setTag("Name", new NBTTagString("generic.armor"));
				armor.setTag("Amount", new NBTTagDouble(arm));
				armor.setTag("Operation", new NBTTagInt(0));
				armor.setTag("UUIDLeast", new NBTTagInt(3));
				armor.setTag("UUIDMost", new NBTTagInt(4));
				armor.setTag("Slot", new NBTTagString("feet"));
				modifiers.appendTag(armor);
				compound.setString("name", ModItems.ITEM_FIELDS.nameItem("boots"));

			} else if (iChoice.equals(ModItems.RANDOM_CHEST) || iChoice.equals(ModItems.TITANIUM_CHEST)) {
				double arm = 4;

				switch (rarity) {
					case 1:
						arm = rand.nextDouble() * (ConfigHandler.tierOneMaxArmor - ConfigHandler.tierOneMinArmor)
								+ ConfigHandler.tierOneMinArmor;
						break;
					case 2:
						arm = rand.nextDouble() * (ConfigHandler.tierTwoMaxArmor - ConfigHandler.tierTwoMinArmor)
								+ ConfigHandler.tierTwoMinArmor;

						break;
					case 3:
						arm = rand.nextDouble() * (ConfigHandler.tierThreeMaxArmor - ConfigHandler.tierThreeMinArmor)
								+ ConfigHandler.tierThreeMinArmor;

						break;

				}
				arm = arm * 1;
				NBTTagCompound armor = new NBTTagCompound();
				armor.setTag("AttributeName", new NBTTagString("generic.armor"));
				armor.setTag("Name", new NBTTagString("generic.armor"));
				armor.setTag("Amount", new NBTTagDouble(arm));
				armor.setTag("Operation", new NBTTagInt(0));
				armor.setTag("UUIDLeast", new NBTTagInt(5));
				armor.setTag("UUIDMost", new NBTTagInt(6));
				armor.setTag("Slot", new NBTTagString("chest"));
				modifiers.appendTag(armor);
				compound.setString("name", ModItems.ITEM_FIELDS.nameItem("chest"));

			} else if (iChoice.equals(ModItems.RANDOM_HELMET) || iChoice.equals(ModItems.TITANIUM_HELMET)) {
				double arm = 4;

				switch (rarity) {
					case 1:
						arm = rand.nextDouble() * (ConfigHandler.tierOneMaxArmor - ConfigHandler.tierOneMinArmor)
								+ ConfigHandler.tierOneMinArmor;
						break;
					case 2:
						arm = rand.nextDouble() * (ConfigHandler.tierTwoMaxArmor - ConfigHandler.tierTwoMinArmor)
								+ ConfigHandler.tierTwoMinArmor;

						break;
					case 3:
						arm = rand.nextDouble() * (ConfigHandler.tierThreeMaxArmor - ConfigHandler.tierThreeMinArmor)
								+ ConfigHandler.tierThreeMinArmor;

						break;

				}
				arm = arm * 0.8;
				NBTTagCompound armor = new NBTTagCompound();
				armor.setTag("AttributeName", new NBTTagString("generic.armor"));
				armor.setTag("Name", new NBTTagString("generic.armor"));
				armor.setTag("Amount", new NBTTagDouble(arm));
				armor.setTag("Operation", new NBTTagInt(0));
				armor.setTag("UUIDLeast", new NBTTagInt(7));
				armor.setTag("UUIDMost", new NBTTagInt(8));
				armor.setTag("Slot", new NBTTagString("head"));
				modifiers.appendTag(armor);
				compound.setString("name", ModItems.ITEM_FIELDS.nameItem("helmet"));

			} else if (iChoice.equals(ModItems.RANDOM_LEGS) || iChoice.equals(ModItems.TITANIUM_LEGS)) {
				double arm = 4;

				switch (rarity) {
					case 1:
						arm = rand.nextDouble() * (ConfigHandler.tierOneMaxArmor - ConfigHandler.tierOneMinArmor)
								+ ConfigHandler.tierOneMinArmor;
						break;
					case 2:
						arm = rand.nextDouble() * (ConfigHandler.tierTwoMaxArmor - ConfigHandler.tierTwoMinArmor)
								+ ConfigHandler.tierTwoMinArmor;

						break;
					case 3:
						arm = rand.nextDouble() * (ConfigHandler.tierThreeMaxArmor - ConfigHandler.tierThreeMinArmor)
								+ ConfigHandler.tierThreeMinArmor;

						break;

				}
				arm = arm * 0.9;
				NBTTagCompound armor = new NBTTagCompound();
				armor.setTag("AttributeName", new NBTTagString("generic.armor"));
				armor.setTag("Name", new NBTTagString("generic.armor"));
				armor.setTag("Amount", new NBTTagDouble(arm));
				armor.setTag("Operation", new NBTTagInt(0));
				armor.setTag("UUIDLeast", new NBTTagInt(9));
				armor.setTag("UUIDMost", new NBTTagInt(10));
				armor.setTag("Slot", new NBTTagString("legs"));
				modifiers.appendTag(armor);
				compound.setString("name", ModItems.ITEM_FIELDS.nameItem("legs"));

			}

			if (iChoice instanceof RandomArmor) {

			}

			compound.setInteger("rarity", rarity);
			compound.setTag("AttributeModifiers", modifiers);

			item.setTagCompound(compound);

			if (iChoice.equals(ModItems.RL_SWORD)) {
				((SwordItem) iChoice).setLore(item, player);
				((SwordItem) iChoice).setName(item);

			} else if (iChoice.equals(ModItems.RL_PICKAXE)) {
				((PickaxeItem) iChoice).setLore(item, player);
				((PickaxeItem) iChoice).setName(item);

			} else if (iChoice.equals(ModItems.RL_PAXEL)) {
				((PaxelItem) iChoice).setLore(item, player);
				((PaxelItem) iChoice).setName(item);

			} else if (iChoice.equals(ModItems.RL_SHOVEL)) {
				((ShovelItem) iChoice).setLore(item, player);
				((ShovelItem) iChoice).setName(item);

			}else if (iChoice.equals(ModItems.RL_AXE)) {
				((AxeItem) iChoice).setLore(item, player);
				((AxeItem) iChoice).setName(item);

			}else if (iChoice.equals(ModItems.RL_BOW)) {
				((BowItem) iChoice).setLore(item, player);
				((BowItem) iChoice).setName(item);

			}else if (iChoice instanceof RandomArmor) {
				((RandomArmor) iChoice).setLore(item, player);
				((RandomArmor) iChoice).setName(item);

			}

			return item;
		}
		return null;
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

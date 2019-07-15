package com.mic.randomloot.init;

import java.util.Random;

import com.mic.randomloot.items.AxeItem;
import com.mic.randomloot.items.BowItem;
import com.mic.randomloot.items.PickaxeItem;
import com.mic.randomloot.items.ShovelItem;
import com.mic.randomloot.items.SwordItem;
import com.mic.randomloot.util.handlers.ConfigHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;

public class ItemFields {
	public static String[] adjectivesU;
	public static String[] adjectivesS;
	public static String[] nounsS;
	public static String[] adjectivesSh;
	public static String[] nounsSh;
	public static String[] adjectivesP;
	public static String[] nounsP;
	public static String[] adjectivesA;
	public static String[] nounsA;
	public static String[] adjectivesB;
	public static String[] nounsB;
	private static Random rand = new Random();

	public ItemFields() {

		String[] adjectivesU = { "Blazing", "Dark", "Dainty", "Jewel Encrusted", "Light", "Heavy", "Serious",
				"Enlightened", "Swift", "Titanic", "Crude", "Icy", "Mythic", "Epic", "Legendary", "Awesome",
				"Incredible", "Shocking", "Iron", "Titanium", "Soft", "Pretty", "Sweet", "Steel", "Elder", "Ancient",
				"Advanced", "Dreadful" };

		String[] adjectivesS = { "Fearsome", "Brutal", "Terrifying", "Harsh", "Barbaric", "Bloodthirsty", "Heartless",
				"Merciless", "Ruthless", "Savage", "Cold-Blooded", "Fearsome" };

		String[] adjectivesSh = { "Rusty", "Filthy", "Shining", "Powerful", "Ground-Breaking", "Sharpened",
				"Crackling" };

		String[] adjectivesP = { "Rusty", "Reliable", "Gritty", "Powerful", "Terra", "Hammering", "Sturdy" };

		String[] nounsP = { "Digger", "Terrablade", "Harbringer", "Earth Shatterer", "Crust-Breaker", "Hole-Puncher",
				"Point", "Swinger", "Mountain Mover", "Pickaxe", "Pick", "Gold Digger" };

		String[] adjectivesB = { "Quivering", "Heavy-Bolted", "Venomous", "Whistling", "Atuned", "Starstruck" };

		String[] nounsB = { "Bow", "Fletcher", "Slinger", "Bolt-Tosser", "Warp-Bow", "Piercer", "Hunting Bow",
				"Crossbow", "Basilisk", "Launcher" };

		String[] adjectivesA = { "Towering", "Wooden", "Fireman's", "Gracious", "Lumber", "Felling", "Swinging" };

		String[] nounsA = { "Chopper", "Axe", "Hatchet", "Splitter", "Tomahawk", "Tremor", "Greataxe", "War Axe",
				"Broadaxe", "Ravager", "Reaver", "Halberd" };

		String[] nounsSh = { "Spade", "Shovel", "Shatter", "Trowel", "Scoop", "Gravedigger", "Spoon" };

		String[] nounsS = { "Blade", "Sword", "Slasher", "Titan", "Killer", "Cleaver", "Knife", "Cutlass", "Nightmare",
				"Glaive", "Machete", "Saber", "Claymore", "Doomblade", "Defender", "Striker", "Crusader", "Skewer",
				"Chaos", "Infinity", "Broadsword", "Shortsword", "Architect", "Lance" };

		this.adjectivesU = adjectivesU;
		this.adjectivesP = adjectivesP;
		this.nounsP = nounsP;
		this.adjectivesS = adjectivesS;
		this.nounsS = nounsS;
		this.adjectivesSh = adjectivesSh;
		this.nounsSh = nounsSh;
		this.adjectivesA = adjectivesA;
		this.nounsA = nounsA;
		this.adjectivesB = adjectivesB;
		this.nounsB = nounsB;
	}

	/**
	 * case type 1-3
	 * 
	 * @param caseType
	 * @return tier (normal, rare, epic)
	 */
	public int rollRarity(Item i) {
		System.out.println("Drop type = " + ConfigHandler.dropType);
		int tier = 0;

		// Defaults
		int[] basicChance = { 90, 7, 3 };
		int[] goldenChance = { 60, 30, 10 };
		int[] titanChance = { 20, 55, 25 };

		int caseType = 0;
		if (i.equals(ModItems.BASIC_CASE)) {
			caseType = 1;
		} else if (i.equals(ModItems.GOLDEN_CASE)) {
			caseType = 2;
		} else if (i.equals(ModItems.TITAN_CASE)) {
			caseType = 3;
		}
		
		System.out.println(caseType);
		int[] chance = null;
		switch (caseType) {
		case 1:
			if (ConfigHandler.dropType == 1)
				return 1;
			chance = basicChance;
			break;

		case 2:
			if (ConfigHandler.dropType == 1)
				return 2;
			chance = goldenChance;
			break;

		case 3:
			if (ConfigHandler.dropType == 1)
				return 3;
			chance = titanChance;
			break;

		}

		int[] rarity = new int[100];
		for (int x = 0; x < chance[0]; x++) {
			rarity[x] = 1;
		}
		for (int x = 0; x < chance[1]; x++) {
			rarity[chance[0] + x] = 2;
		}
		for (int x = 0; x < chance[2]; x++) {
			rarity[chance[0] + chance[1] + x] = 3;
		}

		if (ConfigHandler.dropType == 1)
			System.out.println("You goofed");
		return rarity[rand.nextInt(rarity.length)];
	}

	public String nameItem(String type) {
		String adj = null;
		String nn = null;

		if (type.equals("pickaxe")) {
			String[] adjs = mergeArrs(adjectivesP, adjectivesU);
			adj = adjs[rand.nextInt(adjs.length)];
			nn = nounsP[rand.nextInt(nounsP.length)];
		} else if (type.equals("sword")) {
			String[] adjs = mergeArrs(adjectivesS, adjectivesU);
			adj = adjs[rand.nextInt(adjs.length)];
			nn = nounsS[rand.nextInt(nounsS.length)];
		} else if (type.equals("shovel")) {
			String[] adjs = mergeArrs(adjectivesSh, adjectivesU);
			adj = adjs[rand.nextInt(adjs.length)];
			nn = nounsSh[rand.nextInt(nounsSh.length)];
		} else if (type.equals("axe")) {
			String[] adjs = mergeArrs(adjectivesA, adjectivesU);
			adj = adjs[rand.nextInt(adjs.length)];
			nn = nounsA[rand.nextInt(nounsA.length)];
		} else if (type.equals("bow")) {
			String[] adjs = mergeArrs(adjectivesB, adjectivesU);
			adj = adjs[rand.nextInt(adjs.length)];
			nn = nounsB[rand.nextInt(nounsB.length)];
		}
		return adj + " " + nn;
	}

	public String[] mergeArrs(String[] a, String[] b) {
		int length = a.length + b.length;
		String[] result = new String[length];
		System.arraycopy(a, 0, result, 0, a.length);
		System.arraycopy(b, 0, result, a.length, b.length);
		return result;
	}

	public static int getTexture(ItemStack stack) {
		int value = 0;

		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Texture")) {

			value = stack.getTagCompound().getInteger("Texture");
			// TextComponentString("Grabbed Texture"));

		}

		return value;
	}

	public int calcDamage(int tier) {

		double min = 0, max = 0;
		switch (tier) {
		case 1:
			max = ConfigHandler.tierOneDamageMax;
			min = ConfigHandler.tierOneDamageMin;
			break;

		case 2:
			max = ConfigHandler.tierTwoDamageMax;
			min = ConfigHandler.tierTwoDamageMin;
			break;

		case 3:
			max = ConfigHandler.tierThreeDamageMax;
			min = ConfigHandler.tierThreeDamageMin;
			break;
		}
		return (int) (min + (max - min) * rand.nextDouble());

	}

	public double calcSpeed(int tier) {
		double min = 0, max = 0;
		switch (tier) {
		case 1:
			max = 2.4;
			min = 2.0;
			break;

		case 2:
			max = 2.1;
			min = 1.8;
			break;

		case 3:
			max = 2;
			min = 1.2;
			break;
		}
		return min + (max - min) * rand.nextDouble();

	}

	public float displaySpeed(double spd, EntityLivingBase player) {

		return (float) (player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getBaseValue() + spd);
	}

	public void upgrade(ItemStack stack, EntityLivingBase player) {
		player.getEntityWorld().playSound((EntityPlayer) player, player.getPosition(),
				SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.PLAYERS, 1.0F, 1.0F);

		NBTTagCompound compound = (stack.hasTagCompound()) ? stack.getTagCompound() : new NBTTagCompound();
		NBTTagList modifiers = new NBTTagList();
		int lvl = compound.getInteger("Lvl");
		lvl++;
		compound.setInteger("Lvl", lvl);

		if (stack.getItem().equals(ModItems.RL_SWORD)) {
			int dmg = compound.getInteger("damage");
			double spd = compound.getDouble("speed");
			switch (rand.nextInt(2) + 1) {
			case 1:
				dmg++;
				break;
			case 2:
				spd = spd + 0.05;
				break;

			}

			// damage
			NBTTagCompound damage = new NBTTagCompound();

			damage.setTag("AttributeName", new NBTTagString("generic.attackDamage"));
			damage.setTag("Name", new NBTTagString("generic.attackDamage"));

			damage.setTag("Amount", new NBTTagInt(dmg));
			damage.setTag("Operation", new NBTTagInt(0));
			damage.setTag("UUIDLeast", new NBTTagInt(3));
			damage.setTag("UUIDMost", new NBTTagInt(4));
			damage.setTag("Slot", new NBTTagString("mainhand"));

			// speed
			NBTTagCompound speed = new NBTTagCompound();
			speed.setTag("AttributeName", new NBTTagString("generic.attackSpeed"));
			speed.setTag("Name", new NBTTagString("generic.attackSpeed"));

			speed.setTag("Amount", new NBTTagDouble(spd));
			speed.setTag("Operation", new NBTTagInt(0));
			speed.setTag("UUIDLeast", new NBTTagInt(1));
			speed.setTag("UUIDMost", new NBTTagInt(2));
			speed.setTag("Slot", new NBTTagString("mainhand"));

			compound.setInteger("damage", dmg);
			compound.setDouble("speed", spd);
			modifiers.appendTag(damage);
			modifiers.appendTag(speed);
		} else if (stack.getItem().equals(ModItems.RL_PICKAXE)) {
			PickaxeItem i = (PickaxeItem) stack.getItem();
			float num = (float) (rand.nextFloat() + 0.3);
			i.addSpeed(num, stack);
			i.setLore(stack, player);
		} else if (stack.getItem().equals(ModItems.RL_SHOVEL)) {
			ShovelItem i = (ShovelItem) stack.getItem();
			float num = (float) (rand.nextFloat() + 0.3);
			i.addSpeed(num, stack);
			i.setLore(stack, player);
		} else if (stack.getItem().equals(ModItems.RL_AXE)) {
			AxeItem i = (AxeItem) stack.getItem();
			float num = (float) (rand.nextFloat() + 0.3);
			i.addSpeed(num, stack);
			i.setLore(stack, player);
		} else if (stack.getItem().equals(ModItems.RL_BOW)) {
			BowItem i = (BowItem) stack.getItem();
			Random rand = new Random();
			switch (rand.nextInt(2) + 1) {
			case 1:
				if (!(compound.getInteger("T1") > 0)) {
					if (rand.nextInt(10) == 9) {
						compound.setInteger("T1", rand.nextInt(11) + 1);
					}
				}
				if (!(compound.getInteger("T2") > 0)) {
					if (rand.nextInt(10) == 9) {
						compound.setInteger("T2", rand.nextInt(11) + 1);

					}
				}
				if (!(compound.getInteger("T3") > 0)) {
					if (rand.nextInt(10) == 9) {
						compound.setInteger("T3", rand.nextInt(11) + 1);

					}
				}
				break;
			case 2:
				i.setVelo(i.getVelo(stack) + rand.nextInt(9500), stack);
				compound.setFloat("velo", i.getVelo(stack));

				break;

			}
			i.setLore(stack, player);

		}

		int lvlXp = compound.getInteger("lvlXp");
		compound.setInteger("Xp", 0);
		compound.setInteger("lvlXp", lvlXp = (int) (lvlXp + (lvlXp / 2)));

		compound.setTag("AttributeModifiers", modifiers);
		stack.setTagCompound(compound);

		// SwordItem.setLore(stack, player);

	}

}

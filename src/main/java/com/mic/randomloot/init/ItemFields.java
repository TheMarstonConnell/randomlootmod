package com.mic.randomloot.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.mic.randomloot.RandomLoot;
import com.mic.randomloot.items.AxeItem;
import com.mic.randomloot.items.BowItem;
import com.mic.randomloot.items.PickaxeItem;
import com.mic.randomloot.items.RandomArmor;
import com.mic.randomloot.items.ShovelItem;
import com.mic.randomloot.items.SwordItem;
import com.mic.randomloot.tags.BasicTag;
import com.mic.randomloot.tags.EffectTag;
import com.mic.randomloot.tags.TagHelper;
import com.mic.randomloot.tags.WorldInteractTag;
import com.mic.randomloot.util.IRandomTool;
import com.mic.randomloot.util.WeightedChooser;
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
	public static String[] adjectivesUniversal;
	public static String[] adjectivesShovels;
	public static String[] nounsSwords;
	public static String[] adjectivesSwords;
	public static String[] nounsShovels;
	public static String[] adjectivesPickaxes;
	public static String[] nounsPickaxes;
	public static String[] adjectivesAxes;
	public static String[] nounsAxes;
	public static String[] adjectivesBows;
	public static String[] nounsBows;
	private static Random rand = new Random();
	private static String[] adjectivesBoots;
	private static String[] nounsBoots;
	private static String[] adjectivesLegs;
	private static String[] nounsLegs;
	private static String[] adjectivesChest;
	private static String[] nounsChest;
	private static String[] adjectivesHelmet;
	private static String[] nounsHelmet;
	private static String[] nounsPaxels;
	private static String[] nounsThrowables;
	private static String[] adjectivesThrowables;

	public ItemFields() {

		String[] adjectivesUniversal = { "Blazing", "Dark", "Dainty", "Jewel Encrusted", "Light", "Heavy", "Serious",
				"Enlightened", "Swift", "Titanic", "Crude", "Icy", "Mythic", "Epic", "Legendary", "Awesome",
				"Incredible", "Shocking", "Iron", "Titanium", "Soft", "Pretty", "Sweet", "Steel", "Elder", "Ancient",
				"Advanced", "Dreadful", "Shiny", "Alien", "Twisted", "Necro", "Molten", "Crimson", "Shadow",
				"Fossilized", "Platinum", "Galactic", "Ripe", "Stary", "Vortex", "Nightmarish", "Spectre", "Nebula" };

		String[] adjectivesSwords = { "Fearsome", "Brutal", "Terrifying", "Harsh", "Barbaric", "Bloodthirsty",
				"Heartless", "Merciless", "Ruthless", "Savage", "Cold-Blooded", "Fearsome" };

		String[] adjectivesShovels = { "Rusty", "Filthy", "Shining", "Powerful", "Ground-Breaking", "Sharpened",
				"Crackling" };

		String[] adjectivesPickaxes = { "Rusty", "Reliable", "Gritty", "Powerful", "Terra", "Hammering", "Sturdy",
				"Crystaline", "Pointy" };

		String[] nounsPickaxes = { "Digger", "Terrablade", "Harbringer", "Earth Shatterer", "Crust-Breaker",
				"Hole-Puncher", "Point", "Swinger", "Mountain Mover", "Pickaxe", "Pick", "Gold Digger", "Jackhammer" };

		String[] adjectivesBows = { "Quivering", "Heavy-Bolted", "Venomous", "Whistling", "Atuned", "Starstruck" };

		String[] nounsBows = { "Bow", "Fletcher", "Slinger", "Bolt-Tosser", "Warp-Bow", "Piercer", "Hunting Bow",
				"Crossbow", "Basilisk", "Launcher" };

		String[] adjectivesAxes = { "Towering", "Wooden", "Fireman's", "Gracious", "Lumbering", "Felling", "Swinging" };

		String[] nounsAxes = { "Chopper", "Axe", "Hatchet", "Splitter", "Tomahawk", "Tremor", "Greataxe", "War Axe",
				"Broadaxe", "Ravager", "Reaver", "Halberd", "Hacker", "Battle Axe", "Lumber Axe" };

		String[] nounsPaxels = { "Spiker", "Chop-Digger", "Pick-Logger", "Brute" };

		String[] nounsShovels = { "Spade", "Shovel", "Shatter", "Trowel", "Scoop", "Gravedigger", "Spoon" };

		String[] nounsSwords = { "Blade", "Sword", "Slasher", "Titan", "Killer", "Cleaver", "Knife", "Cutlass",
				"Nightmare", "Glaive", "Machete", "Saber", "Claymore", "Doomblade", "Defender", "Striker", "Crusader",
				"Skewer", "Chaos", "Infinity", "Broadsword", "Shortsword", "Architect", "Lance", "Bat", "Th0rn" };

		String[] nounsBoots = { "Boots", "Booties", "Feet", "Runners", "Tires", "Shoes" };
		String[] adjectivesBoots = { "Swift", "Fast" };
		String[] nounsLegs = { "Leggings", "Pants", "Shorts", "Knickers", "Trousers", "Drawers", "Briefs", "Chaps",
				"Jeans" };
		String[] adjectivesLegs = { "Thick", "Sweaty" };
		String[] nounsChest = { "Chestplate", "Breastplate", "Shirt", "Chest", "Armor Piece", "Shell" };
		String[] adjectivesChest = { "Heavy", "Blast-Resistant" };
		String[] nounsHelmet = { "Helmet", "Helm", "Hat", "Hard Hat", "Head Protector", "Skull" };
		String[] adjectivesHelmet = { "Upright", "Weighted" };

		String[] adjectivesThrowables = { "Throwable", "Tossable", "Throwing", "Gliding", "Floating", "Whizzing",
				"Zipping", "Flying", "Zooming", "Chuckable" };
		String[] nounsThrowables = { "Hammer", "Star", "Hatchet", "Object", "Boomerang", "Knife", "Weapon", "Stick" };

		ItemFields.adjectivesUniversal = adjectivesUniversal;
		ItemFields.adjectivesPickaxes = adjectivesPickaxes;
		ItemFields.nounsPickaxes = nounsPickaxes;
		ItemFields.adjectivesShovels = adjectivesShovels;
		ItemFields.nounsShovels = nounsShovels;
		ItemFields.adjectivesSwords = adjectivesSwords;
		ItemFields.nounsSwords = nounsSwords;
		ItemFields.adjectivesAxes = adjectivesAxes;
		ItemFields.nounsAxes = nounsAxes;
		ItemFields.adjectivesBows = adjectivesBows;
		ItemFields.nounsBows = nounsBows;
		ItemFields.adjectivesBoots = adjectivesBoots;
		ItemFields.nounsBoots = nounsBoots;
		ItemFields.adjectivesChest = adjectivesChest;
		ItemFields.nounsChest = nounsChest;
		ItemFields.adjectivesLegs = adjectivesLegs;
		ItemFields.nounsLegs = nounsLegs;
		ItemFields.adjectivesHelmet = adjectivesHelmet;
		ItemFields.nounsHelmet = nounsHelmet;
		ItemFields.nounsPaxels = nounsPaxels;
		ItemFields.nounsThrowables = nounsThrowables;
		ItemFields.adjectivesThrowables = adjectivesThrowables;

	}

	/**
	 * case type 1-3
	 * 
	 * @param caseType
	 * @return tier (normal, rare, epic)
	 */
	public int rollRarity(Item i) {
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
			String[] adjs = mergeArrs(adjectivesPickaxes, adjectivesUniversal);
			adj = adjs[rand.nextInt(adjs.length)];
			nn = nounsPickaxes[rand.nextInt(nounsPickaxes.length)];
		} else if (type.equals("sword")) {
			String[] adjs = mergeArrs(adjectivesSwords, adjectivesUniversal);
			adj = adjs[rand.nextInt(adjs.length)];
			nn = nounsSwords[rand.nextInt(nounsSwords.length)];
		} else if (type.equals("shovel")) {
			String[] adjs = mergeArrs(adjectivesShovels, adjectivesUniversal);
			adj = adjs[rand.nextInt(adjs.length)];
			nn = nounsShovels[rand.nextInt(nounsShovels.length)];
		} else if (type.equals("axe")) {
			String[] adjs = mergeArrs(adjectivesAxes, adjectivesUniversal);
			adj = adjs[rand.nextInt(adjs.length)];
			nn = nounsAxes[rand.nextInt(nounsAxes.length)];
		} else if (type.equals("paxel")) {
			String[] adjs = mergeArrs(adjectivesAxes, adjectivesUniversal);
			adjs = mergeArrs(adjs, adjectivesPickaxes);
			adj = adjs[rand.nextInt(adjs.length)];
			nn = nounsPaxels[rand.nextInt(nounsPaxels.length)];
		} else if (type.equals("bow")) {
			String[] adjs = mergeArrs(adjectivesBows, adjectivesUniversal);
			adj = adjs[rand.nextInt(adjs.length)];
			nn = nounsBows[rand.nextInt(nounsBows.length)];
		} else if (type.equals("boots")) {
			String[] adjs = mergeArrs(adjectivesUniversal, adjectivesBoots);
			adj = adjs[rand.nextInt(adjs.length)];
			nn = nounsBoots[rand.nextInt(nounsBoots.length)];

		} else if (type.equals("legs")) {
			String[] adjs = mergeArrs(adjectivesUniversal, adjectivesLegs);
			adj = adjs[rand.nextInt(adjs.length)];
			nn = nounsLegs[rand.nextInt(nounsLegs.length)];

		} else if (type.equals("chest")) {
			String[] adjs = mergeArrs(adjectivesUniversal, adjectivesChest);
			adj = adjs[rand.nextInt(adjs.length)];
			nn = nounsChest[rand.nextInt(nounsChest.length)];

		} else if (type.equals("helmet")) {
			String[] adjs = mergeArrs(adjectivesUniversal, adjectivesHelmet);
			adj = adjs[rand.nextInt(adjs.length)];
			nn = nounsHelmet[rand.nextInt(nounsHelmet.length)];

		} else if (type.equals("throwable")) {
			String[] adjs = mergeArrs(adjectivesUniversal, adjectivesThrowables);
			adj = adjs[rand.nextInt(adjs.length)];
			nn = nounsThrowables[rand.nextInt(nounsThrowables.length)];

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
		if (player instanceof EntityPlayer) {
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
				switch (rand.nextInt(3) + 1) {
				case 1:
					dmg++;
					break;
				case 2:
					spd = spd + 0.05;
					break;
				case 3:
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
					BasicTag toAdd = allowedTags.get(RandomLoot.rand.nextInt(allowedTags.size()));
					while (TagHelper.checkForTag(stack, toAdd)) {
						toAdd = allowedTags.get(RandomLoot.rand.nextInt(allowedTags.size()));
					}
					TagHelper.addTag(stack, toAdd.name);

					if (TagHelper.checkForTag(stack, TagHelper.UNBREAKABLE) && ConfigHandler.unbreakable) {
						compound.setBoolean("Unbreakable", true);
					}

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
				Random rand = new Random();
				switch (rand.nextInt(2) + 1) {
				case 1:
					List<BasicTag> allowedTags = new ArrayList<BasicTag>();
					for (BasicTag tag : TagHelper.allTags) {
						if (tag instanceof EffectTag) {
							EffectTag eTag = (EffectTag) tag;
							if (eTag.forTools) {
								allowedTags.add(eTag);
							}
						} else if (tag instanceof WorldInteractTag) {
							WorldInteractTag eTag = (WorldInteractTag) tag;
							if (eTag.forTools) {
								allowedTags.add(eTag);
							}

						}

					}

					allowedTags.add(TagHelper.AUTOSMELT);
					allowedTags.add(TagHelper.UNBREAKABLE);

					BasicTag toAdd = allowedTags.get(RandomLoot.rand.nextInt(allowedTags.size()));
					while (TagHelper.checkForTag(stack, toAdd)) {
						toAdd = allowedTags.get(RandomLoot.rand.nextInt(allowedTags.size()));
					}
					TagHelper.addTag(stack, toAdd.name);

					if (TagHelper.checkForTag(stack, TagHelper.UNBREAKABLE) && ConfigHandler.unbreakable) {
						compound.setBoolean("Unbreakable", true);
					}
					break;
				case 2:
					float num = (float) (rand.nextFloat() + 0.3);
					i.addSpeed(num, stack);
					break;
				}
				i.setLore(stack, player);

			} else if (stack.getItem().equals(ModItems.RL_SHOVEL)) {
				ShovelItem i = (ShovelItem) stack.getItem();
				Random rand = new Random();
				switch (rand.nextInt(2) + 1) {
				case 1:
					List<BasicTag> allowedTags = new ArrayList<BasicTag>();
					for (BasicTag tag : TagHelper.allTags) {
						if (tag instanceof EffectTag) {
							EffectTag eTag = (EffectTag) tag;
							if (eTag.forTools) {
								allowedTags.add(eTag);
							}
						} else if (tag instanceof WorldInteractTag) {
							WorldInteractTag eTag = (WorldInteractTag) tag;
							if (eTag.forTools) {
								allowedTags.add(eTag);
							}

						}

					}

					allowedTags.add(TagHelper.AUTOSMELT);
					allowedTags.add(TagHelper.UNBREAKABLE);

					WeightedChooser<Integer> wc = new WeightedChooser<Integer>();
					wc.addChoice(1, 6);
					wc.addChoice(2, 3);
					wc.addChoice(3, 1);

					BasicTag toAdd = allowedTags.get(RandomLoot.rand.nextInt(allowedTags.size()));
					while (TagHelper.checkForTag(stack, toAdd)) {
						toAdd = allowedTags.get(RandomLoot.rand.nextInt(allowedTags.size()));
					}
					TagHelper.addTag(stack, toAdd.name);

					if (TagHelper.checkForTag(stack, TagHelper.UNBREAKABLE) && ConfigHandler.unbreakable) {
						compound.setBoolean("Unbreakable", true);
					}
					break;
				case 2:
					float num = (float) (rand.nextFloat() + 0.3);
					i.addSpeed(num, stack);
					break;
				}
				i.setLore(stack, player);

			} else if (stack.getItem().equals(ModItems.RL_AXE)) {
				AxeItem i = (AxeItem) stack.getItem();
				Random rand = new Random();
				switch (rand.nextInt(2) + 1) {
				case 1:
					List<BasicTag> allowedTags = new ArrayList<BasicTag>();
					for (BasicTag tag : TagHelper.allTags) {
						if (tag instanceof EffectTag) {
							EffectTag eTag = (EffectTag) tag;
							if (eTag.forTools) {
								allowedTags.add(eTag);
							}
						} else if (tag instanceof WorldInteractTag) {

							WorldInteractTag eTag = (WorldInteractTag) tag;
							if (eTag.forTools) {

								allowedTags.add(eTag);
							}
						}

					}

					allowedTags.add(TagHelper.AUTOSMELT);
					allowedTags.add(TagHelper.UNBREAKABLE);

					BasicTag toAdd = allowedTags.get(RandomLoot.rand.nextInt(allowedTags.size()));
					while (TagHelper.checkForTag(stack, toAdd)) {
						toAdd = allowedTags.get(RandomLoot.rand.nextInt(allowedTags.size()));
					}
					TagHelper.addTag(stack, toAdd.name);

					if (TagHelper.checkForTag(stack, TagHelper.UNBREAKABLE) && ConfigHandler.unbreakable) {
						compound.setBoolean("Unbreakable", true);
					}
					break;
				case 2:
					float num = (float) (rand.nextFloat() + 0.3);
					i.addSpeed(num, stack);
					break;
				}
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
					i.setVelo(BowItem.getVelo(stack) + rand.nextInt(9500), stack);
					compound.setFloat("velo", BowItem.getVelo(stack));

					break;

				}
				i.setLore(stack, player);

			} else if (stack.getItem() instanceof RandomArmor) {
				Random rand = new Random();
				List<BasicTag> allowedTags = new ArrayList<BasicTag>();
				for (BasicTag tag : TagHelper.allTags) {
					if (tag instanceof EffectTag) {
						EffectTag eTag = (EffectTag) tag;
						if (!eTag.offensive) {
							allowedTags.add(eTag);
						}
					}
				}

				int totalTags = rand.nextInt(3);

				TagHelper.addTag(stack, allowedTags.get(rand.nextInt(allowedTags.size())).name);
				if (TagHelper.checkForTag(stack, TagHelper.UNBREAKABLE) && ConfigHandler.unbreakable) {
					compound.setBoolean("Unbreakable", true);
				}

			} else if (stack.getItem().equals(ModItems.THROWABLE)) {
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

				BasicTag toAdd = allowedTags.get(RandomLoot.rand.nextInt(allowedTags.size()));
				while (TagHelper.checkForTag(stack, toAdd)) {
					toAdd = allowedTags.get(RandomLoot.rand.nextInt(allowedTags.size()));
				}
				TagHelper.addTag(stack, toAdd.name);

				if (TagHelper.checkForTag(stack, TagHelper.UNBREAKABLE) && ConfigHandler.unbreakable) {
					compound.setBoolean("Unbreakable", true);
				}

			}

			int lvlXp = compound.getInteger("lvlXp");
			compound.setInteger("Xp", 0);
			compound.setInteger("lvlXp", lvlXp = (int) (lvlXp + (lvlXp / 2)));

			compound.setTag("AttributeModifiers", modifiers);
			stack.setTagCompound(compound);
			IRandomTool randItem = (IRandomTool) stack.getItem();
			randItem.setName(stack);
		}
	}

}

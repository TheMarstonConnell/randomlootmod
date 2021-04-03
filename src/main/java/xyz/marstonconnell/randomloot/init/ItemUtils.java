package xyz.marstonconnell.randomloot.init;

import java.util.Random;

public class ItemUtils {
	
	
	
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
	
	private static Random rand;
	
	public ItemUtils() {
		rand = new Random();
		
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

		ItemUtils.adjectivesUniversal = adjectivesUniversal;
		ItemUtils.adjectivesPickaxes = adjectivesPickaxes;
		ItemUtils.nounsPickaxes = nounsPickaxes;
		ItemUtils.adjectivesShovels = adjectivesShovels;
		ItemUtils.nounsShovels = nounsShovels;
		ItemUtils.adjectivesSwords = adjectivesSwords;
		ItemUtils.nounsSwords = nounsSwords;
		ItemUtils.adjectivesAxes = adjectivesAxes;
		ItemUtils.nounsAxes = nounsAxes;
		ItemUtils.adjectivesBows = adjectivesBows;
		ItemUtils.nounsBows = nounsBows;
		ItemUtils.adjectivesBoots = adjectivesBoots;
		ItemUtils.nounsBoots = nounsBoots;
		ItemUtils.adjectivesChest = adjectivesChest;
		ItemUtils.nounsChest = nounsChest;
		ItemUtils.adjectivesLegs = adjectivesLegs;
		ItemUtils.nounsLegs = nounsLegs;
		ItemUtils.adjectivesHelmet = adjectivesHelmet;
		ItemUtils.nounsHelmet = nounsHelmet;
		ItemUtils.nounsPaxels = nounsPaxels;
		ItemUtils.nounsThrowables = nounsThrowables;
		ItemUtils.adjectivesThrowables = adjectivesThrowables;

	}
	
	public static String[] mergeArrs(String[] a, String[] b) {
		int length = a.length + b.length;
		String[] result = new String[length];
		System.arraycopy(a, 0, result, 0, a.length);
		System.arraycopy(b, 0, result, a.length, b.length);
		return result;
	}
	
	
	public static String nameItem(String type) {
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
		} else if (type.equals("feet")) {
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

		} else if (type.equals("head")) {
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
}

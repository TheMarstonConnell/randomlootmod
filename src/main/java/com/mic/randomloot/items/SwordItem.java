package com.mic.randomloot.items;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.mic.randomloot.RandomLoot;
import com.mic.randomloot.init.ItemFields;
import com.mic.randomloot.init.ModItems;
import com.mic.randomloot.tags.BasicTag;
import com.mic.randomloot.tags.EffectTag;
import com.mic.randomloot.tags.TagHelper;
import com.mic.randomloot.tags.WorldInteractTag;
import com.mic.randomloot.util.IHasModel;
import com.mic.randomloot.util.IRandomTool;
import com.mic.randomloot.util.IReforgeable;
import com.mic.randomloot.util.TagUpdater;
import com.mic.randomloot.util.WeightedChooser;
import com.mic.randomloot.util.handlers.ConfigHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SwordItem extends ItemSword implements IReforgeable, IRandomTool {

	private static int swords;
	public static int tCount = 11;

	public SwordItem(ToolMaterial material, int swords) {
		super(material);
		this.swords = swords;
		setCreativeTab(RandomLoot.randomlootTab);
		setRegistryName(new ResourceLocation(RandomLoot.MODID, "sword"));
		setUnlocalizedName("sword");
		ModItems.ITEMS.add(this);

		addPropertyOverride(new ResourceLocation("model"), new IItemPropertyGetter() {
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) {
				float model = 1.0F;

				model = (float) ItemFields.getTexture(stack);

				return model;
			}
		});

	}

	@Override
	public Item setNoRepair() {
		// TODO Auto-generated method stub
		return super.setNoRepair();
	}

	@Override
	public boolean isRepairable() {
		// TODO Auto-generated method stub
		return false;
	}

	public static int getTexture(ItemStack stack) {
		int value = 0;

		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Texture")) {

			value = stack.getTagCompound().getInteger("Texture");
			// TextComponentString("Grabbed Texture"));

		}

		return value;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {

		stack = TagUpdater.update(stack, (EntityPlayer)attacker);

		
		NBTTagCompound nbt;
		if (stack.hasTagCompound()) {
			nbt = stack.getTagCompound();
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
			ModItems.ITEM_FIELDS.upgrade(stack, attacker);

		}

		stack.setTagCompound(nbt);

		List<BasicTag> tags = TagHelper.getAllTags(stack);

		for (int i = 0; i < tags.size(); i++) {
			if (tags.get(i) instanceof EffectTag) {
				EffectTag eTag = (EffectTag) tags.get(i);
				if (eTag.offensive) {
//					System.out.println(eTag.name);
//					System.out.println(target.getName());
					eTag.runEffect(stack, attacker.world, target);
				} else {
//					System.out.println(eTag.name);
//					System.out.println(attacker.getName());
					eTag.runEffect(stack, attacker.world, attacker);

				}
			}
		}

		setLore(stack, attacker);
		return super.hitEntity(stack, target, attacker);
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

		lore.appendTag(new NBTTagString(TextFormatting.GRAY + "Attack Damage: " + compound.getInteger("damage")));
		DecimalFormat f = new DecimalFormat("##.00");
		lore.appendTag(new NBTTagString(TextFormatting.GRAY + "Attack Speed: "
				+ f.format(ModItems.ITEM_FIELDS.displaySpeed(compound.getDouble("speed"), player))));
		lore.appendTag(new NBTTagString(""));

//		if (t1 == 1 || t2 == 1 || t3 == 1) {
//			lore.appendTag(new NBTTagString(TextFormatting.DARK_GREEN + "Poisonous"));
//		}
//		if (t1 == 2 || t2 == 2 || t3 == 2) {
//			lore.appendTag(new NBTTagString(TextFormatting.DARK_GRAY + "Weakening"));
//		}
//		if (t1 == 3 || t2 == 3 || t3 == 3) {
//			lore.appendTag(new NBTTagString(TextFormatting.DARK_GRAY + "Withering"));
//		}
//		if (t1 == 4 || t2 == 4 || t3 == 4) {
//			lore.appendTag(new NBTTagString(TextFormatting.DARK_PURPLE + "Blinding"));
//		}
//		if (t1 == 5 || t2 == 5 || t3 == 5) {
//			lore.appendTag(new NBTTagString(TextFormatting.DARK_RED + "Starving"));
//		}
//		if (t1 == 6 || t2 == 6 || t3 == 6) {
//			lore.appendTag(new NBTTagString(TextFormatting.GOLD + "Levitating"));
//		}
//		if (t1 == 7 || t2 == 7 || t3 == 7) {
//			lore.appendTag(new NBTTagString(TextFormatting.DARK_BLUE + "Slowing"));
//		}
//		if (t1 == 8 || t2 == 8 || t3 == 8) {
//			lore.appendTag(new NBTTagString(TextFormatting.YELLOW + "Glowing"));
//		}
//		if ((t1 == 9 || t2 == 9 || t3 == 9) && ConfigHandler.unbreakable) {
//			lore.appendTag(new NBTTagString(TextFormatting.GRAY + "Fortified"));
//		}
//		if (t1 == 10 || t2 == 10 || t3 == 10) {
//			lore.appendTag(new NBTTagString(TextFormatting.DARK_RED + "Leaching"));
//		}
//		if (t1 == 11 || t2 == 11 || t3 == 11) {
//			lore.appendTag(new NBTTagString(TextFormatting.LIGHT_PURPLE + "Bezerker"));
//		}

		
		List<BasicTag> tags = TagHelper.getAllTags(stack);
		for (int i = 0; i < tags.size(); i++) {

			String name = tags.get(i).name.replaceAll("_", " ");
			name = TagHelper.convertToTitleCaseIteratingChars(name);
			lore.appendTag(new NBTTagString(tags.get(i).color + name));
		}
		
		lore.appendTag(new NBTTagString(""));
		lore.appendTag(new NBTTagString(TextFormatting.GRAY + "Level " + compound.getInteger("Lvl")));
		lore.appendTag(new NBTTagString(
				TextFormatting.GRAY + "" + compound.getInteger("Xp") + "/" + compound.getInteger("lvlXp") + " Xp"));

		NBTTagCompound display = new NBTTagCompound();
		display.setTag("Lore", lore);
		compound.setTag("display", display);

		if (TagHelper.checkForTag(stack, TagHelper.UNBREAKABLE) && ConfigHandler.unbreakable) {
			compound.setBoolean("Unbreakable", true);
		}else {
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
			}

			

		}
		
		allowedTags.add(TagHelper.UNBREAKABLE);
		allowedTags.add(TagHelper.REPLENISH);

		WeightedChooser<Integer> wc = new WeightedChooser<Integer>();
		wc.addChoice(1, 6);
		wc.addChoice(2, 3);
		wc.addChoice(3, 1);
		
//		for(int i = 0; i < allowedTags.size(); i ++) {
//			System.out.println(allowedTags.get(i).name);
//		}
		
		int totalTags = wc.getRandomObject();
//		System.out.println("Total tags to be applied: " + totalTags);
		for (int i = 0; i < totalTags; i++) {
			BasicTag toAdd = allowedTags.get(RandomLoot.rand.nextInt(allowedTags.size()));
			while (TagHelper.checkForTag(stack, toAdd)) {
				toAdd = allowedTags.get(RandomLoot.rand.nextInt(allowedTags.size()));
				rand.setSeed(rand.nextLong() / 2 * totalTags * allowedTags.size() * i);
			}
			TagHelper.addTag(stack, toAdd.name);
			System.out.println("Adding tag: " + toAdd.name);
		}

		if (TagHelper.checkForTag(stack, TagHelper.UNBREAKABLE) && ConfigHandler.unbreakable) {
			nbt.setBoolean("Unbreakable", true);
		}

		return stack;
	}

	public static ItemStack chooseTexture(ItemStack stack) {
		Random rand = new Random();
		NBTTagCompound nbt;
		if (stack.hasTagCompound()) {
			nbt = stack.getTagCompound();
		} else {
			nbt = new NBTTagCompound();
		}
		nbt.setInteger("Texture", rand.nextInt(swords) + 1);
		stack.setTagCompound(nbt);

		return stack;

	}
	//
	// @Override
	// public void registerModels() {
	// RandomLoot.proxy.registerItemRenderer(this, 0, "inventory");
	//
	// }

	@Override
	public ItemStack reforge(ItemStack stack) {

		System.out.println("Reforging Sword");
		NBTTagCompound nbt;
		if (stack.hasTagCompound()) {
			nbt = stack.getTagCompound();
		} else {
			nbt = new NBTTagCompound();
		}

		int t1 = 0, t2 = 0, t3 = 0, traits = 0;

		nbt.setBoolean("Unbreakable", false);

		nbt.setInteger("Lvl", 1);
		nbt.setInteger("lvlXp", 256);
		nbt.setInteger("Xp", 0);
		nbt.setInteger("HideFlags", 2);

		int rarity = nbt.getInteger("rarity");
		System.out.println("Item rarity: " + rarity);

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
		nbt.setInteger("damage", dmg);
		nbt.setDouble("speed", spd);

		NBTTagList modifiers = new NBTTagList();

		modifiers.appendTag(damage);
		modifiers.appendTag(speed);
		nbt.setString("name", ModItems.ITEM_FIELDS.nameItem("sword"));

		nbt.setTag("AttributeModifiers", modifiers);

		stack.setTagCompound(nbt);
		// TextComponentString("Assigned NBT"));
		assignType(stack);
		System.out.println("reforged sword");

		return stack;

	}

}

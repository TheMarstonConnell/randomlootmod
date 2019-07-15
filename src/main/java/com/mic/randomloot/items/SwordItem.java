package com.mic.randomloot.items;

import java.text.DecimalFormat;
import java.util.Random;

import javax.annotation.Nullable;

import com.mic.randomloot.RandomLoot;
import com.mic.randomloot.init.ItemFields;
import com.mic.randomloot.init.ModItems;
import com.mic.randomloot.util.IHasModel;
import com.mic.randomloot.util.IReforgeable;
import com.mic.randomloot.util.handlers.ConfigHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
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

public class SwordItem extends ItemSword implements IReforgeable {

	private static int swords;
	private static int tCount = 11;

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

		int t1 = nbt.getInteger("T1");
		int t2 = nbt.getInteger("T2");
		int t3 = nbt.getInteger("T3");

		if (t1 == 1 || t2 == 1 || t3 == 1) {
			target.addPotionEffect(new PotionEffect(MobEffects.POISON, 5 * 20, 0));
		}
		if (t1 == 2 || t2 == 2 || t3 == 2) {
			target.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 5 * 20, 1));
		}
		if (t1 == 3 || t2 == 3 || t3 == 3) {
			target.addPotionEffect(new PotionEffect(MobEffects.WITHER, 5 * 20, 0));
		}
		if (t1 == 4 || t2 == 4 || t3 == 4) {
			target.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 8 * 20, 0));
		}
		if (t1 == 5 || t2 == 5 || t3 == 5) {
			target.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 10 * 20, 0));
		}
		if (t1 == 6 || t2 == 6 || t3 == 6) {
			target.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 10 * 20, 0));
		}
		if (t1 == 7 || t2 == 7 || t3 == 7) {
			target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 10 * 20, 0));
		}
		if (t1 == 8 || t2 == 8 || t3 == 8) {
			target.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 10 * 20, 0));
		}
		if (t1 == 10 || t2 == 10 || t3 == 10) {
			attacker.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 1, (int) (target.getHealth() * 0.1)));
		}
		if (t1 == 11 || t2 == 11 || t3 == 11) {
			attacker.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 5 * 20, 1));
		}

		stack.setTagCompound(nbt);
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
		int t1 = compound.getInteger("T1");
		int t2 = compound.getInteger("T2");
		int t3 = compound.getInteger("T3");

		if (t1 == 1 || t2 == 1 || t3 == 1) {
			lore.appendTag(new NBTTagString(TextFormatting.DARK_GREEN + "Poisonous"));
		}
		if (t1 == 2 || t2 == 2 || t3 == 2) {
			lore.appendTag(new NBTTagString(TextFormatting.DARK_GRAY + "Weakening"));
		}
		if (t1 == 3 || t2 == 3 || t3 == 3) {
			lore.appendTag(new NBTTagString(TextFormatting.DARK_GRAY + "Withering"));
		}
		if (t1 == 4 || t2 == 4 || t3 == 4) {
			lore.appendTag(new NBTTagString(TextFormatting.DARK_PURPLE + "Blinding"));
		}
		if (t1 == 5 || t2 == 5 || t3 == 5) {
			lore.appendTag(new NBTTagString(TextFormatting.DARK_RED + "Starving"));
		}
		if (t1 == 6 || t2 == 6 || t3 == 6) {
			lore.appendTag(new NBTTagString(TextFormatting.GOLD + "Levitating"));
		}
		if (t1 == 7 || t2 == 7 || t3 == 7) {
			lore.appendTag(new NBTTagString(TextFormatting.DARK_BLUE + "Slowing"));
		}
		if (t1 == 8 || t2 == 8 || t3 == 8) {
			lore.appendTag(new NBTTagString(TextFormatting.YELLOW + "Glowing"));
		}
		if ((t1 == 9 || t2 == 9 || t3 == 9) && ConfigHandler.unbreakable) {
			lore.appendTag(new NBTTagString(TextFormatting.GRAY + "Fortified"));
		}
		if (t1 == 10 || t2 == 10 || t3 == 10) {
			lore.appendTag(new NBTTagString(TextFormatting.DARK_RED + "Leaching"));
		}
		if (t1 == 11 || t2 == 11 || t3 == 11) {
			lore.appendTag(new NBTTagString(TextFormatting.LIGHT_PURPLE + "Bezerker"));
		}

		lore.appendTag(new NBTTagString(""));
		lore.appendTag(new NBTTagString(TextFormatting.GRAY + "Level " + compound.getInteger("Lvl")));
		lore.appendTag(new NBTTagString(
				TextFormatting.GRAY + "" + compound.getInteger("Xp") + "/" + compound.getInteger("lvlXp") + " Xp"));

		NBTTagCompound display = new NBTTagCompound();
		display.setTag("Lore", lore);
		compound.setTag("display", display);

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

			rand.setSeed(t1);
			t2 = rand.nextInt(tCount) + 1;

		} else if (traits == 3) {
			t1 = rand.nextInt(tCount) + 1;

			rand.setSeed(t1);
			t2 = rand.nextInt(tCount) + 1;

			rand.setSeed(t2);
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
		nbt.setInteger("HideFlags", 2);

		stack.setTagCompound(nbt);
		// TextComponentString("Assigned NBT"));

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

		nbt.setInteger("T1", t1);
		nbt.setInteger("T2", t2);
		nbt.setInteger("T3", t3);

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

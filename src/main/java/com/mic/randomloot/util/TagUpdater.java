package com.mic.randomloot.util;

import com.mic.randomloot.init.ModItems;
import com.mic.randomloot.items.BowItem;
import com.mic.randomloot.items.SwordItem;
import com.mic.randomloot.tags.TagHelper;
import com.mic.randomloot.util.handlers.ConfigHandler;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TagUpdater {

	public static ItemStack update(ItemStack stack, EntityPlayer player) {
		Item i = stack.getItem();
		NBTTagCompound compound;
		if (stack.hasTagCompound()) {
			compound = stack.getTagCompound();
		} else {
			compound = new NBTTagCompound();
		}

		if (compound.hasKey("T1")) {
			int rarity = compound.getInteger("rarity");
			
			stack.shrink(1);
			
			switch (rarity) {
			case 1:
				stack = new ItemStack(ModItems.BASIC_CASE);
				break;
			case 2:
				stack = new ItemStack(ModItems.GOLDEN_CASE);
				break;
			case 3:
				stack = new ItemStack(ModItems.TITAN_CASE);
				break;
			}
//			stack = new ItemStack(ModItems.BASIC_CASE);
			
			player.addItemStackToInventory(stack);
		}
		return stack;

	}

	public static ItemStack update2(ItemStack stack, EntityPlayer player) {

		Item i = stack.getItem();
		NBTTagCompound compound;
		if (stack.hasTagCompound()) {
			compound = stack.getTagCompound();
		} else {
			compound = new NBTTagCompound();
		}

		if (compound.hasKey("T1")) {
			if (compound.getInteger("T1") < 100) {

				if (i instanceof SwordItem) {
					SwordItem si = (SwordItem) i;

					int t1 = compound.getInteger("T1");
					int t2 = compound.getInteger("T2");
					int t3 = compound.getInteger("T3");

					if (t1 == 1 || t2 == 1 || t3 == 1) {
						TagHelper.addTag(stack, TagHelper.POISON_ONE.name);
						// target.addPotionEffect(new PotionEffect(MobEffects.POISON, 5 * 20, 0));
					}
					if (t1 == 2 || t2 == 2 || t3 == 2) {
						TagHelper.addTag(stack, TagHelper.WEAKNESS_ONE.name);

						// target.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 5 * 20, 1));
					}
					if (t1 == 3 || t2 == 3 || t3 == 3) {
						TagHelper.addTag(stack, TagHelper.WITHER_ONE.name);

						// target.addPotionEffect(new PotionEffect(MobEffects.WITHER, 5 * 20, 0));
					}
					if (t1 == 4 || t2 == 4 || t3 == 4) {
						TagHelper.addTag(stack, TagHelper.BLINDING.name);

						// target.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 8 * 20, 0));
					}
					if (t1 == 5 || t2 == 5 || t3 == 5) {
						TagHelper.addTag(stack, TagHelper.WEAKNESS_TWO.name);

						// target.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 10 * 20, 0));
					}
					if (t1 == 6 || t2 == 6 || t3 == 6) {
						TagHelper.addTag(stack, TagHelper.FLOATING.name);

						// target.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 10 * 20, 0));
					}
					if (t1 == 7 || t2 == 7 || t3 == 7) {
						TagHelper.addTag(stack, TagHelper.SLOWING_ONE.name);

						// target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 10 * 20, 0));
					}
					if (t1 == 8 || t2 == 8 || t3 == 8) {
						TagHelper.addTag(stack, TagHelper.GLOWING.name);

						// target.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 10 * 20, 0));
					}
					if ((t1 == 9 || t2 == 9 || t3 == 9) && ConfigHandler.unbreakable) {
						TagHelper.addTag(stack, TagHelper.UNBREAKABLE.name);
						// lore.appendTag(new NBTTagString(TextFormatting.GRAY + "Fortified"));
					}
					if (t1 == 10 || t2 == 10 || t3 == 10) {
						TagHelper.addTag(stack, TagHelper.DAMAGE_ONE.name);

						// attacker.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 1, (int)
						// (target.getHealth() * 0.1)));
					}
					if (t1 == 11 || t2 == 11 || t3 == 11) {
						TagHelper.addTag(stack, TagHelper.RESISTANCE_ONE.name);

						// attacker.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 5 * 20, 1));
					}

					compound.setInteger("T1", 100);
					compound.setInteger("T2", 100);
					compound.setInteger("T3", 100);
					compound.removeTag("T1");
					compound.removeTag("T2");
					compound.removeTag("T3");

					stack.writeToNBT(compound);

					si.setLore(stack, player);

				} else if (i instanceof BowItem) {
					BowItem bi = (BowItem) i;
				} else {
					IRandomTool ri = (IRandomTool) i;

					int t1 = compound.getInteger("T1");
					int t2 = compound.getInteger("T2");
					int t3 = compound.getInteger("T3");

					if (t1 == 1 || t2 == 1 || t3 == 1) {
						// lore.appendTag(new NBTTagString(TextFormatting.DARK_GREEN +
						// "Poisonous"));
					}
					if (t1 == 2 || t2 == 2 || t3 == 2) {
						// lore.appendTag(new NBTTagString(TextFormatting.DARK_GRAY +
						// "Weakening"));
					}
					if (t1 == 3 || t2 == 3 || t3 == 3) {
						// lore.appendTag(new NBTTagString(TextFormatting.DARK_GRAY +
						// "Withering"));
					}
					if (t1 == 4 || t2 == 4 || t3 == 4) {
						// lore.appendTag(new NBTTagString(TextFormatting.DARK_GRAY +
						// "Blinding"));
					}
					if (t1 == 5 || t2 == 5 || t3 == 5) {
						// lore.appendTag(new NBTTagString(TextFormatting.DARK_RED +
						// "Starving"));
					}
					if (t1 == 6 || t2 == 6 || t3 == 6) {
						// lore.appendTag(new NBTTagString(TextFormatting.GOLD +
						// "Levitating"));
					}
					if (t1 == 7 || t2 == 7 || t3 == 7) {
						TagHelper.addTag(stack, TagHelper.REPLENISH.name);

						// lore.appendTag(new NBTTagString(TextFormatting.DARK_GREEN + "Filling"));
					}
					if (t1 == 8 || t2 == 8 || t3 == 8) {
						TagHelper.addTag(stack, TagHelper.AUTOSMELT.name);

						// lore.appendTag(new NBTTagString(TextFormatting.DARK_RED + "Auto-Smelt"));
					}
					if (t1 == 9 || t2 == 9 || t3 == 9) {
						TagHelper.addTag(stack, TagHelper.UNBREAKABLE.name);

						// lore.appendTag(new NBTTagString(TextFormatting.GRAY + "Fortified"));
					}
					if (t1 == 10 || t2 == 10 || t3 == 10) {
						TagHelper.addTag(stack, TagHelper.EXPLOSION.name);

						// lore.appendTag(new NBTTagString(TextFormatting.RED + "Explosive"));
					}
					if (t1 == 11 || t2 == 11 || t3 == 11) {
						TagHelper.addTag(stack, TagHelper.HASTE_ONE.name);

						// lore.appendTag(new NBTTagString(TextFormatting.YELLOW + "Hasty"));
					}

					compound.setInteger("T1", 100);
					compound.setInteger("T2", 100);
					compound.setInteger("T3", 100);
					// compound.removeTag("T1");
					// compound.removeTag("T2");
					// compound.removeTag("T3");

					stack.writeToNBT(compound);

					ri.setLore(stack, player);

				}

				((IReforgeable) i).setName(stack);
				// player.inventory.addItemStackToInventory(stack);
			}
		}

		return stack;
	}
}

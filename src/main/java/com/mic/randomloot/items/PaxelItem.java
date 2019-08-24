package com.mic.randomloot.items;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.collect.Sets;
import com.mic.randomloot.RandomLoot;
import com.mic.randomloot.init.ItemFields;
import com.mic.randomloot.init.ModItems;
import com.mic.randomloot.util.IReforgeable;
import com.mic.randomloot.util.handlers.ConfigHandler;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PaxelItem extends ItemTool implements IReforgeable {

	static int paxels;
	public static int tCount = 11;

	private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.CLAY, Blocks.DIRT, Blocks.FARMLAND,
			Blocks.GRASS, Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.SNOW, Blocks.SNOW_LAYER, Blocks.SOUL_SAND,
			Blocks.GRASS_PATH, Blocks.CONCRETE_POWDER, Blocks.PLANKS, Blocks.BOOKSHELF, Blocks.LOG, Blocks.LOG2,
			Blocks.CHEST, Blocks.PUMPKIN, Blocks.LIT_PUMPKIN, Blocks.MELON_BLOCK, Blocks.LADDER, Blocks.WOODEN_BUTTON,
			Blocks.WOODEN_PRESSURE_PLATE, Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE, Blocks.COBBLESTONE,
			Blocks.DETECTOR_RAIL, Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE, Blocks.DOUBLE_STONE_SLAB,
			Blocks.GOLDEN_RAIL, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.ICE, Blocks.IRON_BLOCK, Blocks.IRON_ORE,
			Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.LIT_REDSTONE_ORE, Blocks.MOSSY_COBBLESTONE, Blocks.NETHERRACK,
			Blocks.PACKED_ICE, Blocks.RAIL, Blocks.REDSTONE_ORE, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.STONE,
			Blocks.STONE_SLAB, Blocks.STONE_BUTTON, Blocks.STONE_PRESSURE_PLATE);
	private static final float[] ATTACK_DAMAGES = new float[] { 6.0F, 8.0F, 8.0F, 8.0F, 6.0F };
	private static final float[] ATTACK_SPEEDS = new float[] { -3.2F, -3.2F, -3.1F, -3.0F, -3.0F };

	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		Material material = state.getMaterial();
		return material != Material.IRON && material != Material.ANVIL && material != Material.ROCK
				&& material != Material.WOOD && material != Material.PLANTS && material != Material.VINE
						? super.getDestroySpeed(stack, state)
						: this.efficiency + getDigSpeed(stack);
	}

	public PaxelItem(ToolMaterial material, int paxels) {
		super(material, EFFECTIVE_ON);
		this.attackDamage = ATTACK_DAMAGES[material.ordinal()];
		this.attackSpeed = ATTACK_SPEEDS[material.ordinal()];
		PaxelItem.paxels = paxels;
		setCreativeTab(RandomLoot.randomlootTab);
		setRegistryName(new ResourceLocation(RandomLoot.MODID, "paxel"));
		setUnlocalizedName("paxel");
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

	public static ItemStack chooseTexture(ItemStack stack) {
		Random rand = new Random();
		NBTTagCompound nbt;
		if (stack.hasTagCompound()) {
			nbt = stack.getTagCompound();
		} else {
			nbt = new NBTTagCompound();
		}
		nbt.setInteger("Texture", rand.nextInt(paxels) + 1);
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

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos,
			EntityLivingBase entityLiving) {

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
			ModItems.ITEM_FIELDS.upgrade(stack, entityLiving);

		}

		int t1 = nbt.getInteger("T1");
		int t2 = nbt.getInteger("T2");
		int t3 = nbt.getInteger("T3");

		if (t1 == 1 || t2 == 1 || t3 == 1) {

		}
		if (t1 == 2 || t2 == 2 || t3 == 2) {

		}
		if (t1 == 3 || t2 == 3 || t3 == 3) {

		}
		if (t1 == 4 || t2 == 4 || t3 == 4) {

		}
		if (t1 == 5 || t2 == 5 || t3 == 5) {

		}
		if (t1 == 6 || t2 == 6 || t3 == 6) {
			List<EntityItem> entities = worldIn.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(
					pos.getX() - 1, pos.getY() - 1, pos.getZ() - 1, pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1));
			for (EntityItem item : entities) {
				item.setPositionAndUpdate(entityLiving.posX, entityLiving.posY, entityLiving.posZ);
			}

		}
		if (t1 == 7 || t2 == 7 || t3 == 7) {
			// PotionEffect(MobEffects.SATURATION, 30 * 20, 0));
			Random rand = new Random();
			switch (rand.nextInt(6)) {
			case 4:
			case 5:
				((EntityPlayer) entityLiving).getFoodStats().addStats((ItemFood) Items.BEETROOT,
						new ItemStack(Items.BEETROOT));
			}

		}
		if (t1 == 8 || t2 == 8 || t3 == 8) {

		}
		if (t1 == 10 || t2 == 10 || t3 == 10) {

			if (!worldIn.isRemote) {
				float f = 4.0F;
				worldIn.createExplosion(entityLiving, pos.getX(), pos.getY(), pos.getZ(), 4.0F, true);

			}

		}
		if (t1 == 11 || t2 == 11 || t3 == 11) {
			entityLiving.addPotionEffect(new PotionEffect(MobEffects.HASTE, 5 * 20, 1));
		}

		stack.setTagCompound(nbt);
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

		DecimalFormat f = new DecimalFormat("##.00");
		lore.appendTag(new NBTTagString(TextFormatting.GRAY + "Mining Speed: " + f.format(getDigSpeed(stack))));
		lore.appendTag(new NBTTagString(""));

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
			lore.appendTag(new NBTTagString(TextFormatting.DARK_GREEN + "Filling"));
		}
		if (t1 == 8 || t2 == 8 || t3 == 8) {
			lore.appendTag(new NBTTagString(TextFormatting.DARK_RED + "Auto-Smelt"));
		}
		if (t1 == 9 || t2 == 9 || t3 == 9) {
			lore.appendTag(new NBTTagString(TextFormatting.GRAY + "Fortified"));
		}
		if (t1 == 10 || t2 == 10 || t3 == 10) {
			lore.appendTag(new NBTTagString(TextFormatting.RED + "Explosive"));
		}
		if (t1 == 11 || t2 == 11 || t3 == 11) {
			lore.appendTag(new NBTTagString(TextFormatting.YELLOW + "Hasty"));
		}

		lore.appendTag(new NBTTagString(""));
		lore.appendTag(new NBTTagString(TextFormatting.GRAY + "Level " + compound.getInteger("Lvl")));
		lore.appendTag(new NBTTagString(
				TextFormatting.GRAY + "" + compound.getInteger("Xp") + "/" + compound.getInteger("lvlXp") + " Xp"));

		NBTTagCompound display = new NBTTagCompound();
		display.setTag("Lore", lore);
		compound.setTag("display", display);

		stack.setStackDisplayName(color + compound.getString("name"));

		return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
	}

	public float getRLDigSpeed(ItemStack stack) {
		return 0f;
	}

	public void setLore(ItemStack stack, EntityLivingBase player) {

		// System.out.println(digSpeed);

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

		DecimalFormat f = new DecimalFormat("##.00");
		lore.appendTag(new NBTTagString(TextFormatting.GRAY + "Mining Speed: " + f.format(getDigSpeed(stack))));
		lore.appendTag(new NBTTagString(""));
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
			lore.appendTag(new NBTTagString(TextFormatting.AQUA + "Phasing"));
		}
		if (t1 == 7 || t2 == 7 || t3 == 7) {
			lore.appendTag(new NBTTagString(TextFormatting.DARK_GREEN + "Filling"));
		}
		if (t1 == 8 || t2 == 8 || t3 == 8) {
			lore.appendTag(new NBTTagString(TextFormatting.DARK_RED + "Auto-Smelt"));
		}
		if ((t1 == 9 || t2 == 9 || t3 == 9) && ConfigHandler.unbreakable) {
			lore.appendTag(new NBTTagString(TextFormatting.GRAY + "Fortified"));
		}
		if (t1 == 10 || t2 == 10 || t3 == 10) {
			lore.appendTag(new NBTTagString(TextFormatting.RED + "Explosive"));
		}
		if (t1 == 11 || t2 == 11 || t3 == 11) {
			lore.appendTag(new NBTTagString(TextFormatting.YELLOW + "Hasty"));
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

		return stack;
	}

	// @Override
	// public void registerModels() {
	// RandomLoot.proxy.registerItemRenderer(this, 0, "inventory");
	//
	// }

	public float getDigSpeed(ItemStack stack) {
		NBTTagCompound nbt;
		if (stack.hasTagCompound()) {
			nbt = stack.getTagCompound();
		} else {
			nbt = new NBTTagCompound();
		}

		return nbt.getFloat("digSpeed");

	}

	public void setDigSpeed(float digSpeed, ItemStack stack) {
		NBTTagCompound nbt;
		if (stack.hasTagCompound()) {
			nbt = stack.getTagCompound();
		} else {
			nbt = new NBTTagCompound();
		}

		nbt.setFloat("digSpeed", digSpeed);
	}

	public void addSpeed(float num, ItemStack stack) {
		setDigSpeed(getDigSpeed(stack) + num, stack);

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
		System.out.println("Item rarity: " + rarity);

		assignType(stack);

		PaxelItem pick = (PaxelItem) stack.getItem();
		switch (rarity) {
		case 1:
			pick.setDigSpeed(7 + rand.nextInt(6), stack);
			break;
		case 2:
			pick.setDigSpeed(13 + rand.nextInt(5), stack);
			break;
		case 3:
			pick.setDigSpeed(18 + rand.nextInt(5), stack);
			break;

		}
		nbt.setString("name", ModItems.ITEM_FIELDS.nameItem("paxel"));

		return stack;
	}

	public boolean canHarvestBlock(IBlockState blockIn) {
		Block block = blockIn.getBlock();

		if (block == Blocks.OBSIDIAN) {
			return this.toolMaterial.getHarvestLevel() == 3;
		} else if (block != Blocks.DIAMOND_BLOCK && block != Blocks.DIAMOND_ORE) {
			if (block != Blocks.EMERALD_ORE && block != Blocks.EMERALD_BLOCK) {
				if (block != Blocks.GOLD_BLOCK && block != Blocks.GOLD_ORE) {
					if (block != Blocks.IRON_BLOCK && block != Blocks.IRON_ORE) {
						if (block != Blocks.LAPIS_BLOCK && block != Blocks.LAPIS_ORE) {
							if (block != Blocks.REDSTONE_ORE && block != Blocks.LIT_REDSTONE_ORE) {
								Material material = blockIn.getMaterial();

								if (material == Material.ROCK) {
									return true;
								} else if (material == Material.IRON) {
									return true;
								} else {
									return material == Material.ANVIL;
								}
							} else {
								return this.toolMaterial.getHarvestLevel() >= 2;
							}
						} else {
							return this.toolMaterial.getHarvestLevel() >= 1;
						}
					} else {
						return this.toolMaterial.getHarvestLevel() >= 1;
					}
				} else {
					return this.toolMaterial.getHarvestLevel() >= 2;
				}
			} else {
				return this.toolMaterial.getHarvestLevel() >= 2;
			}
		} else if (block == Blocks.SNOW_LAYER) {
			return true;
		} else if (block == Blocks.SNOW) {
			return true;
		} else {
			return this.toolMaterial.getHarvestLevel() >= 2;
		}
	}
}

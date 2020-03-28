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

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
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

public class AxeItem extends ItemAxe implements IReforgeable, IRandomTool{

	static int axes;

	public AxeItem(ToolMaterial material, int axes) {
		super(material);
		AxeItem.axes = axes;
		setCreativeTab(RandomLoot.randomlootTab);
		setRegistryName(new ResourceLocation(RandomLoot.MODID, "axe"));
		setUnlocalizedName("axe");
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
	
	public ItemStack chooseTexture(ItemStack stack, int num) {
		Random rand = new Random();

		if(num == 0) {
			num = rand.nextInt(axes) + 1;
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

		stack = TagUpdater.update(stack, (EntityPlayer)entityLiving);
		
		
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

		List<BasicTag> tags = TagHelper.getAllTags(stack);

		for (int i = 0; i < tags.size(); i++) {
			if (tags.get(i) instanceof EffectTag) {
				EffectTag eTag = (EffectTag) tags.get(i);
				eTag.runEffect(stack, worldIn, entityLiving);
			} else if (tags.get(i) instanceof WorldInteractTag) {
				WorldInteractTag wTag = (WorldInteractTag) tags.get(i);
				wTag.runEffect(stack, worldIn, entityLiving, state, pos);
			}
		}

		setLore(stack, entityLiving);
		setName(stack);

		return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
	}

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
	public float getDestroySpeed(ItemStack stack, IBlockState state) {

		Material material = state.getMaterial();
		return material != Material.WOOD && material != Material.CACTUS && material != Material.GOURD
				? super.getDestroySpeed(stack, state)
				: this.efficiency + getDigSpeed(stack);

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
		
		List<BasicTag> tags = TagHelper.getAllTags(stack);
//		System.out.println("Amount of tags on item: " + tags.size());
		for (int i = 0; i < tags.size(); i++) {

			String name = tags.get(i).name.replaceAll("_", " ");
			name = TagHelper.convertToTitleCaseIteratingChars(name);
//			System.out.println("Writing new tag to lore...");
			lore.appendTag(new NBTTagString(tags.get(i).color + name));
		}
		
		lore.appendTag(new NBTTagString(""));
		lore.appendTag(new NBTTagString(TextFormatting.GRAY + "Level " + compound.getInteger("Lvl")));
		lore.appendTag(new NBTTagString(
				TextFormatting.GRAY + "" + compound.getInteger("Xp") + "/" + compound.getInteger("lvlXp") + " Xp"));

		NBTTagCompound display = new NBTTagCompound();
		display.setTag("Lore", lore);
		compound.setTag("display", display);

//		stack.setStackDisplayName(color + compound.getString("name"));

	}

	public ItemStack setName(ItemStack stack){
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
		return stack;
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
		nbt.setInteger("Texture", rand.nextInt(axes) + 1);
		nbt.setInteger("HideFlags", 2);

		stack.setTagCompound(nbt);
		
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

		int totalTags = wc.getRandomObject();
		for (int i = 0; i < totalTags; i++) {
			BasicTag toAdd = allowedTags.get(RandomLoot.rand.nextInt(allowedTags.size()));
			while (TagHelper.checkForTag(stack, toAdd)) {
				toAdd = allowedTags.get(RandomLoot.rand.nextInt(allowedTags.size()));
				rand.setSeed(rand.nextLong() / 2 * totalTags * allowedTags.size() * i);
			}
			TagHelper.addTag(stack, toAdd.name);
		}

		if (TagHelper.checkForTag(stack, TagHelper.UNBREAKABLE) && ConfigHandler.unbreakable) {
			nbt.setBoolean("Unbreakable", true);
		}

		

		return stack;
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

		nbt.setBoolean("Unbreakable", false);

		nbt.setInteger("Lvl", 1);
		nbt.setInteger("lvlXp", 256);
		nbt.setInteger("Xp", 0);
		nbt.setInteger("HideFlags", 2);

		int rarity = nbt.getInteger("rarity");
		System.out.println("Item rarity: "  + rarity);
		
		assignType(stack);
		
		
		AxeItem axe = (AxeItem) stack.getItem();
		switch (rarity) {
		case 1:
			axe.setDigSpeed(7 + rand.nextInt(6), stack);
			break;
		case 2:
			axe.setDigSpeed(13 + rand.nextInt(5), stack);
			break;
		case 3:
			axe.setDigSpeed(18 + rand.nextInt(5), stack);
			break;

		}

		nbt.setString("name", ModItems.ITEM_FIELDS.nameItem("axe"));

		return stack;
	}



}

package xyz.marstonconnell.randomloot.tools;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.command.arguments.NBTCompoundTagArgument;
import net.minecraft.command.arguments.NBTPathArgument.NBTPath;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.Constants.NBT;
import xyz.marstonconnell.randomloot.tags.BasicTag;
import xyz.marstonconnell.randomloot.tags.TagHelper;

public class BaseTool extends Item {

	final static String TAG_XP = "xp";
	final static String TAG_MAX_XP = "max_xp";
	final static String TAG_TEXTURE = "texture";
	

	public BaseTool(Properties properties) {
		super(properties.defaultMaxDamage(1562));

		ItemModelsProperties.func_239418_a_(this, new ResourceLocation("model"), new IItemPropertyGetter() {
			

			@Override
			public float call(ItemStack stack, ClientWorld p_call_2_, LivingEntity p_call_3_) {
				float model = 1.0F;

				model = (float) getTexture(stack);

				return model;
			}
		});

	}

	public int getVariants() {
		return 1;
	}
	
	public List<BasicTag> getAllowedTags() {

		return new ArrayList<BasicTag>();
		
	}
	
	public static void changeXP(ItemStack stack, int amt) {
		setXP(stack, getXP(stack) + amt);
	}
	
	public static void setLore(ItemStack stack) {
		
		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}
		
		System.out.println("Setting Lore...");
		ListNBT lore = new ListNBT();

		lore.add(StringNBT.valueOf("{\"text\":\"\"}"));

		
		
		
		List<BasicTag> tags = TagHelper.getAllTags(stack);
		for (int i = 0; i < tags.size(); i++) {


			String name = tags.get(i).name.replaceAll("_", " ");
			name = TagHelper.convertToTitleCaseIteratingChars(name);
			
			
			lore.add(StringNBT.valueOf("{\"text\":\"" + tags.get(i).color + name + "\"}"));

			
			
			System.out.println(" - " + name);
			
		}
		lore.add(StringNBT.valueOf("{\"text\":\"\"}"));
		lore.add(StringNBT.valueOf("{\"text\":\"" + TextFormatting.GRAY + "XP: " + getXP(stack) + " / " + getMaxXP(stack) + "\"}"));


		
		
		
		
		CompoundNBT display = nbt.getCompound("display");
		
		display.put("Lore", lore);
		
		nbt.put("display", display);
		stack.setTag(nbt);
	}
	

	@Override
	public int getItemStackLimit(ItemStack stack) {
		return 1;
	}

	public String getItemType() {
		return "random";

	}

	public static void setName(ItemStack stack, String name) {
		setStringNBT(stack, "name", name);
	}

	public static String getName(ItemStack stack) {
		return getStringNBT(stack, "name");
	}

	/**
	 * Sets given nbt tag.
	 * 
	 * @param stack
	 * @param tag   String
	 * @param value String
	 */
	private static void setStringNBT(ItemStack stack, String tag, String value) {
		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}

		nbt.putString(tag, value);
		stack.setTag(nbt);
	}

	/**
	 * Gets given nbt tag.
	 * 
	 * @param stack
	 * @param tag   String
	 * @return
	 */
	private static String getStringNBT(ItemStack stack, String tag) {
		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}

		return nbt.getString(tag);

	}

	/**
	 * Sets given nbt tag.
	 * 
	 * @param stack
	 * @param tag   String
	 * @param value int
	 */
	private static void setIntNBT(ItemStack stack, String tag, int value) {
		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}

		nbt.putInt(tag, value);
		stack.setTag(nbt);
	}

	/**
	 * Returns int from nbt tag.
	 * 
	 * @param stack
	 * @param tag
	 * @return value int
	 */
	private static int getIntNBT(ItemStack stack, String tag) {
		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}

		return nbt.getInt(tag);
	}

	/**
	 * Returns current xp value.
	 * 
	 * @param stack
	 * @return int xp value
	 */
	public static int getXP(ItemStack stack) {
		return getIntNBT(stack, TAG_XP);
	}

	/**
	 * Sets current xp value.
	 * 
	 * @param stack
	 * @param xp    int
	 */
	public static void setXP(ItemStack stack, int xp) {
		setIntNBT(stack, TAG_XP, xp);
	}

	/**
	 * Returns max xp value.
	 * 
	 * @param stack
	 * @return int max xp value
	 */
	public static int getMaxXP(ItemStack stack) {
		return getIntNBT(stack, TAG_MAX_XP);
	}

	/**
	 * Sets max xp value.
	 * 
	 * @param stack
	 * @param xp    int
	 */
	public static void setMaxXP(ItemStack stack, int xp) {
		setIntNBT(stack, TAG_MAX_XP, xp);
	}

	/**
	 * Returns current texture value.
	 * 
	 * @param stack
	 * @return int texture value
	 */
	public int getTexture(ItemStack stack) {
		return getIntNBT(stack, TAG_TEXTURE);
	}

	/**
	 * Sets current texture id.
	 * 
	 * @param stack
	 * @param texture int
	 */
	public static void setTexture(ItemStack stack, int textureId) {
		setIntNBT(stack, TAG_TEXTURE, textureId);
	}

}

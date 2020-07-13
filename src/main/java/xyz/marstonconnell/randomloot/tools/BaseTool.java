package xyz.marstonconnell.randomloot.tools;

import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import xyz.marstonconnell.randomloot.init.ItemFactory;
import xyz.marstonconnell.randomloot.init.RLItems;
import xyz.marstonconnell.randomloot.tags.BasicTag;
import xyz.marstonconnell.randomloot.tags.TagHelper;

public class BaseTool extends Item {

	final static String TAG_XP = "xp";
	final static String TAG_MAX_XP = "max_xp";
	final static String TAG_TEXTURE = "texture";
	

	public boolean isRepairItem(ItemStack stack) {
		return stack.getItem() == RLItems.best_shard;
	}
	
	
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		// TODO Auto-generated method stub
		return isRepairItem(repair) || super.getIsRepairable(toRepair, repair);
	}
	
	
	
	
	
@Override
public boolean isRepairable(ItemStack stack) {
	return true;
}
	
	public BaseTool(Properties properties) {
		super(properties.defaultMaxDamage(1562));
		if(FMLEnvironment.dist == Dist.CLIENT) {
            TextureProxy.setModelProperties(this);
        }
		RLItems.ITEMS.add(this);

	}

	public static void changeXP(ItemStack stack, int amt) {
		setXP(stack, getXP(stack) + amt);

		if (getXP(stack) >= getMaxXP(stack)) {
			upgradeTool(stack);
		}

	}

	private static void upgradeTool(ItemStack stack) {
		setXP(stack, 0);
		setMaxXP(stack, (int) (getMaxXP(stack) * 1.5));
		ItemFactory.applyToken(stack);
	}

	public static void setLore(ItemStack stack) {

		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}

		ListNBT lore = new ListNBT();

		lore.add(StringNBT.valueOf("{\"text\":\"\"}"));

		List<BasicTag> tags = TagHelper.getAllTags(stack);
		for (int i = 0; i < tags.size(); i++) {

			String name = tags.get(i).name.replaceAll("_", " ");
			name = TagHelper.convertToTitleCaseIteratingChars(name);

			lore.add(StringNBT.valueOf("{\"text\":\"" + tags.get(i).color + name + "\"}"));

		}

		lore.add(StringNBT.valueOf("{\"text\":\"\"}"));

		for (String s : ((IRLTool) stack.getItem()).getStatsLore(stack)) {
			lore.add(StringNBT.valueOf("{\"text\":\"" + s + "\"}"));
		}

		lore.add(StringNBT.valueOf("{\"text\":\"\"}"));
		lore.add(StringNBT.valueOf(
				"{\"text\":\"" + TextFormatting.GRAY + "XP: " + getXP(stack) + " / " + getMaxXP(stack) + "\"}"));

		CompoundNBT display = nbt.getCompound("display");

		display.put("Lore", lore);

		nbt.put("display", display);
		
		if (TagHelper.checkForTag(stack, TagHelper.UNBREAKABLE)) {
			nbt.putBoolean("Unbreakable", true);
		}else {
			nbt.putBoolean("Unbreakable", false);
		}
		
		
		nbt.putInt("HideFlags", 6);

		stack.setTag(nbt);
	}

	@Override
	public int getItemStackLimit(ItemStack stack) {
		return 1;
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
	protected static void setStringNBT(ItemStack stack, String tag, String value) {
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
	protected static String getStringNBT(ItemStack stack, String tag) {
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
	protected static void setIntNBT(ItemStack stack, String tag, int value) {
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
	protected static int getIntNBT(ItemStack stack, String tag) {
		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}

		return nbt.getInt(tag);
	}
	
	
	/**
	 * Sets given nbt tag.
	 * 
	 * @param stack
	 * @param tag   String
	 * @param value float
	 */
	protected static void setFloatNBT(ItemStack stack, String tag, float value) {
		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}

		nbt.putFloat(tag, value);
		stack.setTag(nbt);
	}

	/**
	 * Returns float from nbt tag.
	 * 
	 * @param stack
	 * @param tag
	 * @return value float
	 */
	protected static float getFloatNBT(ItemStack stack, String tag) {
		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}

		return nbt.getFloat(tag);
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

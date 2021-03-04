package xyz.marstonconnell.randomloot.tools;

import java.util.List;

import net.minecraft.client.audio.Sound;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.loading.FMLEnvironment;
import xyz.marstonconnell.randomloot.init.ItemFactory;
import xyz.marstonconnell.randomloot.init.RLItems;
import xyz.marstonconnell.randomloot.tags.BasicTag;
import xyz.marstonconnell.randomloot.tags.TagHelper;

public class BaseTool extends Item {

	public final static String TAG_XP = "xp";
	public final static String TAG_MAX_XP = "max_xp";
	public final static String TAG_TEXTURE = "texture";
	public final static String TAG_BONUS_SPEED = "rl_bonus_speed";

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

	public static void changeXP(ItemStack stack, int amt, World worldIn, BlockPos pos) {
		setXP(stack, getXP(stack) + amt);

		if (getXP(stack) >= getMaxXP(stack)) {
			setIntNBT(stack, "rl_level", getIntNBT(stack, "rl_level") + 1);
			upgradeTool(stack, worldIn, pos);
			
		}

	}

	private static void upgradeTool(ItemStack stack, World worldIn, BlockPos pos) {
		setXP(stack, 0);
		setMaxXP(stack, (int) (getMaxXP(stack) * 1.5));
		ItemFactory.applyToken(stack);
		
        worldIn.playSound((PlayerEntity)null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.PLAYERS, 1.0F, 1.0F);

		
	}
	
	public static void setLore(ItemStack stack) {
		setLore(stack, "");
	}
	
	/**
	 * Updates old tool to new version.
	 * 
	 * @param stack
	 * @return
	 */
	public static ItemStack updateToNewVersion(ItemStack stack) {
		
		if(getIntNBT(stack, "rl_tool_version") != ItemFactory.CURRENT_TOOL_VERSION) {
			
			CompoundNBT nbt;
			if (stack.hasTag()) {
				nbt = stack.getTag();
			} else {
				nbt = new CompoundNBT();
			}

			ListNBT tags = nbt.getList("rl_tags", NBT.TAG_STRING);
			
			for(int i = 0; i < tags.size(); i ++) {
				TagHelper.addTag(stack, TagHelper.tagMap.get(tags.getString(i)));
			}
			
			BaseTool.setIntNBT(stack, "rl_tool_version", ItemFactory.CURRENT_TOOL_VERSION);

			tags.clear();
			nbt.put("rl_tags", tags);
		}
		
		return stack;
		
	}

	public static void setLore(ItemStack stack, String addLore) {

		stack = updateToNewVersion(stack);
		
		
		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}

		ListNBT lore = new ListNBT();

		
		lore.add(StringNBT.valueOf("{\"text\":\"" + addLore + "\"}"));

		
		List<BasicTag> tags = TagHelper.getTagList(stack);
		for (int i = 0; i < tags.size(); i++) {

			
			lore.add(StringNBT.valueOf("{\"text\":\"" + tags.get(i).color + tags.get(i).toString() + "\"}"));

		}

		lore.add(StringNBT.valueOf("{\"text\":\"\"}"));

		for (String s : ((IRLTool) stack.getItem()).getStatsLore(stack)) {
			lore.add(StringNBT.valueOf("{\"text\":\"" + s + "\"}"));
		}

		lore.add(StringNBT.valueOf("{\"text\":\"\"}"));
		lore.add(StringNBT.valueOf(
				"{\"text\":\"" + TextFormatting.GRAY + "XP: " + getXP(stack) + " / " + getMaxXP(stack) + "\"}"));
		lore.add(StringNBT.valueOf(
				"{\"text\":\"" + TextFormatting.GRAY + "Level: " + getIntNBT(stack, "rl_level") + "\"}"));

		
		int cells = 14;
		int perc = (int) Math.ceil(((double)getXP(stack)) / ((double)getMaxXP(stack)) * cells);
		
		
		String bar = "";
		for(int i = 0; i < perc; i ++) {
			bar = bar + "\u23F9";
		}
		for(int i = perc; i < cells; i ++) {
			bar = bar + "\u25A1";
		}
		bar = bar + "";
		
		lore.add(StringNBT.valueOf(
				"{\"text\":\"" + TextFormatting.GRAY + bar + "\"}"));
		
		
		CompoundNBT display = nbt.getCompound("display");

		display.put("Lore", lore);

		nbt.put("display", display);
		
		
		
		
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
	public static void setStringNBT(ItemStack stack, String tag, String value) {
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
	public static String getStringNBT(ItemStack stack, String tag) {
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
	public static void setIntNBT(ItemStack stack, String tag, int value) {
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
	public static int getIntNBT(ItemStack stack, String tag) {
		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}

		return nbt.getInt(tag);
	}
	
	
	/**
	 * Returns long from nbt tag.
	 * 
	 * @param stack
	 * @param tag
	 * @return value int
	 */
	public static long getLongNBT(ItemStack stack, String tag) {
		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}

		return nbt.getLong(tag);
	}
	
	/**
	 * Sets given nbt tag.
	 * 
	 * @param stack
	 * @param tag   String
	 * @param value float
	 */
	protected static void setLongNBT(ItemStack stack, String tag, long value) {
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
	 * Sets given nbt tag.
	 * 
	 * @param stack
	 * @param tag   String
	 * @param value float
	 */
	public static void setFloatNBT(ItemStack stack, String tag, float value) {
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
	public static float getFloatNBT(ItemStack stack, String tag) {
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
	public static int getTexture(ItemStack stack) {
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

	public static void deleteStringNBT(ItemStack stack, String string) {
		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}

		nbt.remove(string);
		stack.setTag(nbt);
		
	}

}

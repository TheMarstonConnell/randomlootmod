package xyz.marstonconnell.randomloot.tools;

import java.util.List;
import java.util.function.Predicate;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShootableItem;
import net.minecraft.item.Item.Properties;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import xyz.marstonconnell.randomloot.init.ItemFactory;
import xyz.marstonconnell.randomloot.init.RLItems;
import xyz.marstonconnell.randomloot.tags.BasicTag;
import xyz.marstonconnell.randomloot.tags.TagHelper;

public abstract class RLShootableItem extends ShootableItem {


	public static final Predicate<ItemStack> ARROWS = (stack) -> {
		return stack.getItem().isIn(ItemTags.ARROWS);
	};
	public static final Predicate<ItemStack> ARROWS_OR_FIREWORKS = ARROWS.or((stack) -> {
		return stack.getItem() == Items.FIREWORK_ROCKET;
	});

	public Predicate<ItemStack> getAmmoPredicate() {
		return this.getInventoryAmmoPredicate();
	}

	/**
	 * Get the predicate to match ammunition when searching the player's inventory,
	 * not their main/offhand
	 */
	public abstract Predicate<ItemStack> getInventoryAmmoPredicate();

	public static ItemStack getHeldAmmo(LivingEntity living, Predicate<ItemStack> isAmmo) {
		if (isAmmo.test(living.getHeldItem(Hand.OFF_HAND))) {
			return living.getHeldItem(Hand.OFF_HAND);
		} else {
			return isAmmo.test(living.getHeldItem(Hand.MAIN_HAND)) ? living.getHeldItem(Hand.MAIN_HAND)
					: ItemStack.EMPTY;
		}
	}

	/**
	 * Return the enchantability factor of the item, most of the time is based on
	 * material.
	 */
	public int getItemEnchantability() {
		return 1;
	}

	public abstract int func_230305_d_();
	
	final static String TAG_XP = "xp";
	final static String TAG_MAX_XP = "max_xp";
	final static String TAG_TEXTURE = "texture";

	

	
	public RLShootableItem(Properties properties) {
		super(properties.defaultMaxDamage(1562));
		if(FMLEnvironment.dist == Dist.CLIENT) {
            TextureProxy.setModelProperties(this);
        }
		RLItems.ITEMS.add(this);

	}



	@Override
	public int getItemStackLimit(ItemStack stack) {
		return 1;
	}


}

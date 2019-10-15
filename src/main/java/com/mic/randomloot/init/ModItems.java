package com.mic.randomloot.init;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mic.randomloot.RandomLoot;
import com.mic.randomloot.items.AxeItem;
import com.mic.randomloot.items.BowItem;
import com.mic.randomloot.items.CaseItem;
import com.mic.randomloot.items.ClickItem;
import com.mic.randomloot.items.ItemBase;
import com.mic.randomloot.items.PaxelItem;
import com.mic.randomloot.items.PickaxeItem;
import com.mic.randomloot.items.RandomArmor;
import com.mic.randomloot.items.ShovelItem;
import com.mic.randomloot.items.SwordItem;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems {
	public static final List<Item> ITEMS = new ArrayList<Item>();
	public static final ItemFields ITEM_FIELDS = new ItemFields();

	public static final CaseItem BASIC_CASE = new CaseItem("basic_case", 1);
	public static final CaseItem GOLDEN_CASE = new CaseItem("golden_case", 2);
	public static final CaseItem TITAN_CASE = new CaseItem("titan_case", 3);
	
	public static final Item RANDOM_SHARD = new ItemBase("random_shard");

	public static final Item RL_SWORD = new SwordItem(ToolMaterial.DIAMOND, 28);
	public static final Item RL_PICKAXE = new PickaxeItem(ToolMaterial.DIAMOND, 17);
	public static final Item RL_SHOVEL = new ShovelItem(ToolMaterial.DIAMOND, 8);
	public static final Item RL_AXE = new AxeItem(ToolMaterial.DIAMOND, 11);
	public static final Item RL_PAXEL = new PaxelItem(ToolMaterial.DIAMOND, 3);
	public static final Item RL_BOW = new BowItem(6);

	public static final ArmorMaterial HEAVY = EnumHelper.addArmorMaterial("heavy", RandomLoot.MODID + ":heavy", 33,
			new int[] { 3, 6, 8, 3 }, 6, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0F);
	public static final Item RANDOM_HELMET = new RandomArmor("heavy_helmet", HEAVY, 1, EntityEquipmentSlot.HEAD);
	public static final Item RANDOM_CHEST = new RandomArmor("heavy_chest", HEAVY, 1, EntityEquipmentSlot.CHEST);
	public static final Item RANDOM_LEGS = new RandomArmor("heavy_legs", HEAVY, 2, EntityEquipmentSlot.LEGS);
	public static final Item RANDOM_BOOTS = new RandomArmor("heavy_boots", HEAVY, 1, EntityEquipmentSlot.FEET);

	public static final ArmorMaterial TITANIUM = EnumHelper.addArmorMaterial("titanium", RandomLoot.MODID + ":titanium", 36,
			new int[] { 4, 5, 7, 4 }, 7, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.3F);
	public static final Item TITANIUM_HELMET = new RandomArmor("titanium_helmet", TITANIUM, 1, EntityEquipmentSlot.HEAD);
	public static final Item TITANIUM_CHEST = new RandomArmor("titanium_chest", TITANIUM, 1, EntityEquipmentSlot.CHEST);
	public static final Item TITANIUM_LEGS = new RandomArmor("titanium_legs", TITANIUM, 2, EntityEquipmentSlot.LEGS);
	public static final Item TITANIUM_BOOTS = new RandomArmor("titanium_boots", TITANIUM, 1, EntityEquipmentSlot.FEET);


	@Mod.EventBusSubscriber(modid = RandomLoot.MODID)
	public static class ItemRegistry {
		public static final Set<Item> ITEM_SET = new HashSet<Item>();

		@SubscribeEvent
		public static void newRegistry(final RegistryEvent.NewRegistry event) {
			
		}

		@SubscribeEvent
		public static void register(final RegistryEvent.Register<Item> event) {
			final IForgeRegistry<Item> registry = event.getRegistry();

			for (final Item item : ITEMS) {
				registry.register(item);
				
				ITEM_SET.add(item);
			}

		}
	}
}

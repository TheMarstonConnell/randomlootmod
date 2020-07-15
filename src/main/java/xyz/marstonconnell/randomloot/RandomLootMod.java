package xyz.marstonconnell.randomloot;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.GameRules;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import xyz.marstonconnell.randomloot.entity.RLPointOfInterestTypes;
import xyz.marstonconnell.randomloot.entity.RLVillagerProfession;
import xyz.marstonconnell.randomloot.init.ItemUtils;
import xyz.marstonconnell.randomloot.init.RLCommands;
import xyz.marstonconnell.randomloot.init.RLEntities;
import xyz.marstonconnell.randomloot.init.RLItems;
import xyz.marstonconnell.randomloot.tags.TagHelper;
import xyz.marstonconnell.randomloot.tools.TextureProxy;
import xyz.marstonconnell.randomloot.utils.Config;
import xyz.marstonconnell.randomloot.utils.RLUtils;
import xyz.marstonconnell.randomloot.utils.RandomTradeBuilder;
import xyz.marstonconnell.randomloot.utils.Registration;
import xyz.marstonconnell.randomloot.utils.WeightedChooser;
import xyz.marstonconnell.randomloot.utils.handlers.NetworkHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Preconditions;

import java.util.Random;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(RandomLootMod.MODID)
public class RandomLootMod {
	// Directly reference a log4j logger.
	private static final Logger LOGGER = LogManager.getLogger();
	public static final String MODID = "randomloot";
	public static Random rand;
	public static WeightedChooser<Item> wc;

	public RandomLootMod() {

		new ItemUtils();
		rand = new Random();
		wc = new WeightedChooser<Item>();

		Registration.init();

		// Register the setup method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		// Register the enqueueIMC method for modloading

		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
		// Register the processIMC method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
		// Register the doClientStuff method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

		// Register ourselves for server and other game events we are interested in

		ModLoadingContext.get().registerConfig(Type.COMMON, Config.COMMON_CONFIG);

		MinecraftForge.EVENT_BUS.register(this);

	}

	private void setup(final FMLCommonSetupEvent event) {
		// some preinit code
		LOGGER.info("HELLO FROM PREINIT");
		LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());

		RLUtils.fixPOITypeBlockStates(RLPointOfInterestTypes.LOOTER);

		NetworkHandler.registerMessage();
	}

	private void doClientStuff(final FMLClientSetupEvent event) {
		// do something that can only be done on the client
		if (FMLEnvironment.dist == Dist.CLIENT) {
			TextureProxy.init();
		}

		LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);

	}

	private void enqueueIMC(final InterModEnqueueEvent event) {
		// some example code to dispatch IMC to another mod
		// InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello
		// world from the MDK"); return "Hello world";});
	}

	private void processIMC(final InterModProcessEvent event) {
		// some example code to receive and process InterModComms from other mods
		LOGGER.info("Got IMC {}",
				event.getIMCStream().map(m -> m.getMessageSupplier().get()).collect(Collectors.toList()));
	}

	@SubscribeEvent
	public void entityDrop(LivingDropsEvent event) {
		if (event.getEntity().getEntityWorld().getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
			if (!(event.getSource() instanceof EntityDamageSource)
					|| !(((EntityDamageSource) event.getSource()).getTrueSource() instanceof PlayerEntity)
					|| !(((EntityDamageSource) event.getSource()).getTrueSource() instanceof PlayerEntity))
				return;

			int choice = RandomLootMod.rand.nextInt(100);
			int range = Config.DROP_CHANCE.get();

			if (choice > range) {
				choice = RandomLootMod.rand.nextInt(100);

				if (event.getEntity() instanceof MonsterEntity) {
					range = Config.MONSTERS_DROP.get();
				} else if (event.getEntity() instanceof AnimalEntity) {
					range = Config.ANIMAL_DROP.get();
				} else if (event.getEntity() instanceof EnderDragonEntity
						|| event.getEntity() instanceof WitherEntity) {
					range = Config.BOSS_DROP.get();
				}

				if (choice < range) {
					WeightedChooser<Item> cases = new WeightedChooser<Item>();
					cases.addChoice(RLItems.BASIC_ITEM_CASE, Config.BASIC_CHANCE.get());
					cases.addChoice(RLItems.BETTER_ITEM_CASE, Config.GOLD_CHANCE.get());
					cases.addChoice(RLItems.BEST_ITEM_CASE, Config.TITAN_CHANCE.get());
					event.getEntity().entityDropItem(new ItemStack(cases.getRandomObject()));
				}

			}

		}

	}

	// You can use SubscribeEvent and let the Event Bus discover methods to call
	@SubscribeEvent
	public void onServerStarting(FMLServerStartingEvent event) {
		// do something when the server starts
		LOGGER.info("HELLO from server starting");

		RLCommands.register(event.getCommandDispatcher());
	}

	// You can use EventBusSubscriber to automatically subscribe events on the
	// contained class (this is subscribing to the MOD
	// Event bus for receiving Registry Events)
	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents {
		@SubscribeEvent
		public static void onItemRegistry(RegistryEvent.Register<Item> event) {

			for (Item item : RLItems.ITEMS) {
				event.getRegistry().register(item);
			}

			wc.addChoice(RLItems.random_sword, Config.SWORD_CHANCE.get());
			wc.addChoice(RLItems.random_pick, Config.PICK_CHANCE.get());
			wc.addChoice(RLItems.random_axe, Config.AXE_CHANCE.get());
			wc.addChoice(RLItems.random_spade, Config.SPADE_CHANCE.get());
			wc.addChoice(RLItems.random_bow, Config.BOW_CHANCE.get());
			 wc.addChoice(RLItems.THROWABLE_ITEM, Config.THROWABLE_CHANCE.get());

			wc.addChoice(RLItems.HEAVY_BOOTS, Config.ARMOR_CHANCE.get());
			wc.addChoice(RLItems.HEAVY_CHEST, Config.ARMOR_CHANCE.get());
			wc.addChoice(RLItems.HEAVY_HELMET, Config.ARMOR_CHANCE.get());
			wc.addChoice(RLItems.HEAVY_LEGS, Config.ARMOR_CHANCE.get());
			wc.addChoice(RLItems.TITANIUM_BOOTS, Config.ARMOR_CHANCE.get());
			wc.addChoice(RLItems.TITANIUM_CHEST, Config.ARMOR_CHANCE.get());
			wc.addChoice(RLItems.TITANIUM_HELMET, Config.ARMOR_CHANCE.get());
			wc.addChoice(RLItems.TITANIUM_LEGS, Config.ARMOR_CHANCE.get());

		}

		@SubscribeEvent
		public static void onEntityRegistry(RegistryEvent.Register<EntityType<?>> event) {
			for (EntityType<?> entity : RLEntities.ENTITIES) {
				Preconditions.checkNotNull(entity.getRegistryName(), "registryName");
				event.getRegistry().register(entity);
			}

		}

	}

	@Mod.EventBusSubscriber(modid = RandomLootMod.MODID)
	public static class VillagerEvents {
		@SubscribeEvent
		public static void villagerTrades(VillagerTradesEvent event) {

			if (event.getType() == RLVillagerProfession.LOOTER) {

				for (int i = 0; i < TagHelper.allTags.size(); i++) {
					ItemStack s = new ItemStack(RLItems.TRAIT_HOLDER);
					TagHelper.addTag(s, TagHelper.allTags.get(i).name);
					s.setDisplayName(new StringTextComponent(TextFormatting.WHITE
							+ TagHelper.convertToTitleCaseIteratingChars(TagHelper.allTags.get(i).name) + " Essence"));
					event.getTrades().get(4)
							.add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 24), s, 3, 10, 0F));

				}

				event.getTrades().get(1).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 16),
						new ItemStack(RLItems.basic_shard), 8, 2, 0F));
				event.getTrades().get(1)
						.add((entity, random) -> new MerchantOffer(new ItemStack(RLItems.BASIC_ITEM_CASE),
								new ItemStack(Items.EMERALD), 8, 2, 0F));
				event.getTrades().get(1).add((entity, random) -> new MerchantOffer(new ItemStack(RLItems.better_shard),
						new ItemStack(Items.EMERALD, 2), 5, 2, 0.5F));
				event.getTrades().get(2)
						.add((entity, random) -> new MerchantOffer(new ItemStack(RLItems.BETTER_ITEM_CASE),
								new ItemStack(Items.EMERALD, 3), 8, 2, 0F));
				event.getTrades().get(2).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 32),
						new ItemStack(RLItems.better_shard), 3, 2, 0F));
				event.getTrades().get(3)
						.add((entity, random) -> new MerchantOffer(new ItemStack(RLItems.BEST_ITEM_CASE),
								new ItemStack(Items.EMERALD, 12), 8, 2, 0F));
				event.getTrades().get(3).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 32),
						new ItemStack(RLItems.BASIC_ITEM_CASE), 3, 2, 0F));
				event.getTrades().get(3).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 32),
						new ItemStack(RLItems.best_shard), 5, 2, 0F));
				event.getTrades().get(4).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 48),
						new ItemStack(RLItems.BETTER_ITEM_CASE), 2, 1, 0F));
				event.getTrades().get(5).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 64),
						new ItemStack(RLItems.BEST_ITEM_CASE), 2, 1, 0F));

				RandomTradeBuilder.forEachLevel(
						(level, tradeBuild) -> event.getTrades().get(level.intValue()).add(tradeBuild.build()));
			}

		}

	}

}

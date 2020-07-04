package xyz.marstonconnell.randomloot;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.GameRules;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import xyz.marstonconnell.randomloot.init.ItemUtils;
import xyz.marstonconnell.randomloot.init.RLCommands;
import xyz.marstonconnell.randomloot.init.RLITems;
import xyz.marstonconnell.randomloot.items.CaseItem;
import xyz.marstonconnell.randomloot.utils.WeightedChooser;
import xyz.marstonconnell.randomloot.utils.handlers.NetworkHandler;

import org.apache.commons.codec.language.bm.Rule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("randomloot")
public class RandomLootMod {
	// Directly reference a log4j logger.
	private static final Logger LOGGER = LogManager.getLogger();
	public static final String MODID = "randomloot";
	public static Random rand;

	public RandomLootMod() {
		ItemUtils iut = new ItemUtils();
		rand = new Random();

		// Register the setup method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		// Register the enqueueIMC method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
		// Register the processIMC method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
		// Register the doClientStuff method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);

	}

	private void setup(final FMLCommonSetupEvent event) {
		// some preinit code
		LOGGER.info("HELLO FROM PREINIT");
		LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());

		NetworkHandler.registerMessage();
	}

	private void doClientStuff(final FMLClientSetupEvent event) {
		// do something that can only be done on the client
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
		if(event.getEntity().getEntityWorld().getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
			if (!(event.getSource() instanceof EntityDamageSource)
					|| !(((EntityDamageSource) event.getSource()).getTrueSource() instanceof PlayerEntity)
					|| !(((EntityDamageSource) event.getSource()).getTrueSource() instanceof PlayerEntity))
				return;
			
			
			int choice = RandomLootMod.rand.nextInt(100);
			
			if(choice < 10) {
				WeightedChooser<Item> cases = new WeightedChooser<Item>();
				cases.addChoice(RLITems.BASIC_ITEM_CASE, 70);
				cases.addChoice(RLITems.BETTER_ITEM_CASE, 20);
				cases.addChoice(RLITems.BEST_ITEM_CASE, 10);
				event.getEntity().entityDropItem(new ItemStack(cases.getRandomObject()));
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
			event.getRegistry().registerAll(RLITems.BEST_ITEM_CASE, RLITems.BETTER_ITEM_CASE, RLITems.BASIC_ITEM_CASE,
					RLITems.random_sword);
		}
		
		

	}
}

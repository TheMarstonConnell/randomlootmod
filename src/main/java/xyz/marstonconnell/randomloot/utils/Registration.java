package xyz.marstonconnell.randomloot.utils;

import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.marstonconnell.randomloot.RandomLootMod;
import xyz.marstonconnell.randomloot.blocks.LightBall;
import xyz.marstonconnell.randomloot.blocks.MovingLightBlock;
import xyz.marstonconnell.randomloot.blocks.RLAnvil;
import xyz.marstonconnell.randomloot.container.RLRepairContainer;
import xyz.marstonconnell.randomloot.entity.MovingLightTileEntity;
import net.minecraftforge.common.extensions.IForgeContainerType;

import com.google.common.collect.Sets;

public class Registration {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, RandomLootMod.MODID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RandomLootMod.MODID);
    private static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, RandomLootMod.MODID);

    private static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, RandomLootMod.MODID);


    
    
    
    public static void init() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILE_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    static <T extends IRecipe<?>> IRecipeType<T> register(final String key) {
        return Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(RandomLootMod.MODID, key), new IRecipeType<T>() {
           public String toString() {
              return key;
           }
        });
     }
    
    
    
    
    public static final RegistryObject<RLAnvil> EDITOR = BLOCKS.register("editor", RLAnvil::new);
    public static final RegistryObject<LightBall> LIGHT_BALL = BLOCKS.register("light_ball", LightBall::new);
    public static final RegistryObject<MovingLightBlock> MOVING_LIGHT = BLOCKS.register("moving_light", MovingLightBlock::new);

    
    public static final RegistryObject<Item> EDITOR_ITEM = ITEMS.register("editor", () -> new BlockItem(EDITOR.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));

    public static final RegistryObject<ContainerType<RLRepairContainer>> EDITOR_CONTAINER = CONTAINERS.register("editor", () -> IForgeContainerType.create((windowId, inv, data) -> {
        return new RLRepairContainer(windowId, inv);
        
        
    
        
        
    }));
    
    public static final RegistryObject<TileEntityType<MovingLightTileEntity>> MOVING_LIGHT_TE = TILE_ENTITIES.register(
            "moving_light_te", () -> new TileEntityType<MovingLightTileEntity>(MovingLightTileEntity::new, Sets.newHashSet(MOVING_LIGHT.get()), null));
    
    
    
}

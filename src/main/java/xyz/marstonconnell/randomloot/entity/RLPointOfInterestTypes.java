package xyz.marstonconnell.randomloot.entity;

import net.minecraft.block.Blocks;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;
import xyz.marstonconnell.randomloot.RandomLootMod;
import xyz.marstonconnell.randomloot.utils.RLUtils;
import xyz.marstonconnell.randomloot.utils.Registration;

@EventBusSubscriber(modid = RandomLootMod.MODID, bus = Bus.MOD)
@ObjectHolder(RandomLootMod.MODID)
public class RLPointOfInterestTypes
{
    public static final PointOfInterestType LOOTER = null;
    
    @SubscribeEvent
    public static void registerPointOfInterestTypes(Register<PointOfInterestType> event)
    {
        IForgeRegistry<PointOfInterestType> registry = event.getRegistry();
        
        registry.register(RLUtils.pointOfInterestType("looter", RLUtils.getAllStates(Registration.EDITOR.get()), 1).setRegistryName(RandomLootMod.MODID, "looter"));
    }
}
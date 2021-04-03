package xyz.marstonconnell.randomloot.entity;

import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;
import xyz.marstonconnell.randomloot.RandomLootMod;
import xyz.marstonconnell.randomloot.utils.RLUtils;

@EventBusSubscriber(modid = RandomLootMod.MODID, bus = Bus.MOD)
@ObjectHolder(RandomLootMod.MODID)
public class RLVillagerProfession
{
    public static final VillagerProfession LOOTER = null;
    
    @SubscribeEvent
    public static void registerVillagerProfessions(Register<VillagerProfession> event)
    {
        IForgeRegistry<VillagerProfession> registry = event.getRegistry();
        
        registry.register(RLUtils.villagerProfession("looter", RLPointOfInterestTypes.LOOTER, SoundEvents.ENTITY_VILLAGER_WORK_WEAPONSMITH).setRegistryName(RandomLootMod.MODID, "looter"));
    }
}
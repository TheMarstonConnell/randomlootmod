package xyz.marstonconnell.randomloot.utils.loot;

import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.NBTPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import xyz.marstonconnell.randomloot.RandomLootMod;
import xyz.marstonconnell.randomloot.tags.TagHelper;

public class DataProvider extends GlobalLootModifierProvider
{
    public DataProvider(DataGenerator gen, String modid)
    {
        super(gen, modid);
    }

    @Override
    protected void start()
    {
    	
    	CompoundNBT cmp = TagHelper.convertToNBT(TagHelper.AUTOSMELT);
    	
    	
        add("smelting", RandomLootMod.SMELTING.get(), new AutoSmeltModifier(
                new ILootCondition[]{
                        MatchTool.builder(
                                ItemPredicate.Builder.create().nbt(
                                        cmp))
                                .build()
                })
        );
    }
}

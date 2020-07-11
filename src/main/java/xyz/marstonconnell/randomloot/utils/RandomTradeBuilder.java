package xyz.marstonconnell.randomloot.utils;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import net.minecraft.entity.merchant.villager.VillagerTrades.ITrade;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;


/**
 * Thanks to casualty on github
 * https://github.com/CAS-ual-TY/GunCus/blob/master/src/main/java/de/cas_ual_ty/guncus/util/RandomTradeBuilder.java
 * @author CAS-ual-TY
 *
 */
public class RandomTradeBuilder
{
    /** 0-4 = Arms Dealer Levels ; 5 = Wanderer ; 6 = Wanderer Rare */
    private static final ArrayList<ArrayList<RandomTradeBuilder>> TRADES_LIST = new ArrayList<>();
    
    static
    {
        for(int i = 0; i <= 6; ++i)
        {
            RandomTradeBuilder.TRADES_LIST.add(new ArrayList<RandomTradeBuilder>());
        }
    }
    
    private static ArrayList<RandomTradeBuilder> getList(int i)
    {
        return RandomTradeBuilder.TRADES_LIST.get(i);
    }
    
    private static void register(int i, RandomTradeBuilder tradeBuilder)
    {
        RandomTradeBuilder.getList(i).add(tradeBuilder);
    }
    
    public static void forEachLevel(BiConsumer<Integer, RandomTradeBuilder> consumer)
    {
        ArrayList<RandomTradeBuilder> list;
        
        for(int i = 1; i <= 5; ++i)
        {
            list = RandomTradeBuilder.TRADES_LIST.get(i - 1);
            
            for(RandomTradeBuilder tradeBuilder : list)
            {
                consumer.accept(i, tradeBuilder);
            }
        }
    }
    
    public static void forEachLevel(Consumer<RandomTradeBuilder> consumer)
    {
        RandomTradeBuilder.forEachLevel((level, tradeBuilder) -> consumer.accept(tradeBuilder));
    }
    
    public static void forEachWanderer(Consumer<RandomTradeBuilder> consumer)
    {
        ArrayList<RandomTradeBuilder> list = RandomTradeBuilder.TRADES_LIST.get(5);
        
        for(RandomTradeBuilder tradeBuilder : list)
        {
            consumer.accept(tradeBuilder);
        }
    }
    
    public static void forEachWandererRare(Consumer<RandomTradeBuilder> consumer)
    {
        ArrayList<RandomTradeBuilder> list = RandomTradeBuilder.TRADES_LIST.get(6);
        
        for(RandomTradeBuilder tradeBuilder : list)
        {
            consumer.accept(tradeBuilder);
        }
    }
    
    protected Function<Random, ItemStack> price;
    protected Function<Random, ItemStack> price2;
    protected Function<Random, ItemStack> forSale;
    
    protected final int maxTrades;
    protected final int xp;
    protected final float priceMult;
    
    protected boolean rare;
    
    public RandomTradeBuilder(int maxTrades, int xp, float priceMult)
    {
        this.price = null;
        this.price2 = (random) -> ItemStack.EMPTY;
        this.forSale = null;
        this.maxTrades = maxTrades;
        this.xp = xp;
        this.priceMult = priceMult;
        this.rare = false;
    }
    
    public RandomTradeBuilder setPrice(Function<Random, ItemStack> price)
    {
        this.price = price;
        return this;
    }
    
    public RandomTradeBuilder setPrice(Item item, int min, int max)
    {
        return this.setPrice(RandomTradeBuilder.createFunction(item, min, max));
    }
    
    public RandomTradeBuilder setPrice2(Function<Random, ItemStack> price2)
    {
        this.price2 = price2;
        return this;
    }
    
    public RandomTradeBuilder setPrice2(Item item, int min, int max)
    {
        return this.setPrice2(RandomTradeBuilder.createFunction(item, min, max));
    }
    
    public RandomTradeBuilder setForSale(Function<Random, ItemStack> forSale)
    {
        this.forSale = forSale;
        return this;
    }
    
    public RandomTradeBuilder setForSale(Item item, int min, int max)
    {
        return this.setForSale(RandomTradeBuilder.createFunction(item, min, max));
    }
    
    public RandomTradeBuilder setEmeraldPrice(int emeralds)
    {
        return this.setPrice((random) -> new ItemStack(Items.EMERALD, emeralds));
    }
    
    public RandomTradeBuilder setEmeraldPriceFor(int emeralds, Item item, int amt)
    {
        this.setEmeraldPrice(emeralds);
        return this.setForSale((random) -> new ItemStack(item, amt));
    }
    
    public RandomTradeBuilder setEmeraldPriceFor(int emeralds, Item item)
    {
        return this.setEmeraldPriceFor(emeralds, item, 1);
    }
    
    public RandomTradeBuilder setEmeraldPrice(int min, int max)
    {
        return this.setPrice(Items.EMERALD, min, max);
    }
    
    public RandomTradeBuilder setEmeraldPriceFor(int min, int max, Item item, int amt)
    {
        this.setEmeraldPrice(min, max);
        return this.setForSale((random) -> new ItemStack(item, amt));
    }
    
    public RandomTradeBuilder setEmeraldPriceFor(int min, int max, Item item)
    {
        return this.setEmeraldPriceFor(min, max, item, 1);
    }
    
    public RandomTradeBuilder setRare()
    {
        this.rare = true;
        return this;
    }
    
    public boolean canBuild()
    {
        return this.price != null && this.forSale != null;
    }
    
    public ITrade build()
    {
        return (entity, random) -> !this.canBuild() ? null : new MerchantOffer(this.price.apply(random), this.price2.apply(random), this.forSale.apply(random), this.maxTrades, this.xp, this.priceMult);
    }
    
    public static Function<Random, ItemStack> createFunction(Item item, int min, int max)
    {
        return (random) -> new ItemStack(item, random.nextInt(max) + min);
    }
    
    // --- registering stuff ---
    
    protected RandomTradeBuilder register(int index)
    {
        RandomTradeBuilder.register(index, this);
        return this;
    }
    
    /**
     * @param level 1-5
     */
    public RandomTradeBuilder registerLevel(int level)
    {
        return this.register(level - 1);
    }
    
    public RandomTradeBuilder registerWanderer(boolean rare)
    {
        return this.register(rare ? 6 : 5);
    }
}

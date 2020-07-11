package xyz.marstonconnell.randomloot.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableSet;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.util.SoundEvent;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class RLUtils {
	public static Set<BlockState> getAllStates(Block block) {
	      return ImmutableSet.copyOf(block.getStateContainer().getValidStates());
	   }
	
	 private static Method blockStatesInjector;
	    
	    static
	    {
	        try
	        {
	        	blockStatesInjector = ObfuscationReflectionHelper.findMethod(PointOfInterestType.class, "func_221052_a", PointOfInterestType.class);
	        }
	        catch (SecurityException e)
	        {
	            e.printStackTrace();
	        }
	    }
	    
	    public static void fixPOITypeBlockStates(PointOfInterestType poiType)
	    {
	        try
	        {
	            blockStatesInjector.invoke(null, poiType);
	        }
	        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
	        {
	            e.printStackTrace();
	        }
	    }
	
	
	public static VillagerProfession villagerProfession(String p1, PointOfInterestType p2, @Nullable SoundEvent p5)
    {
        try
        {
            Constructor<VillagerProfession> c = VillagerProfession.class.getDeclaredConstructor(String.class, PointOfInterestType.class, ImmutableSet.class, ImmutableSet.class, SoundEvent.class);
            c.setAccessible(true);
            return c.newInstance(p1, p2, ImmutableSet.of(), ImmutableSet.of(), p5);
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }
        catch (SecurityException e)
        {
            e.printStackTrace();
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }
        
        return null;
    }
	
	public static PointOfInterestType pointOfInterestType(String p1, Set<BlockState> p2, int p3)
    {
        try
        {
            //          Constructor<PointOfInterestType> c = (Constructor<PointOfInterestType>)PointOfInterestType.class.getDeclaredConstructors()[1];
            Constructor<PointOfInterestType> c = PointOfInterestType.class.getDeclaredConstructor(String.class, Set.class, Integer.TYPE, Integer.TYPE);
            c.setAccessible(true);
            return c.newInstance(p1, p2, p3, 1);
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }
        catch (SecurityException e)
        {
            e.printStackTrace();
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }
        
        return null;
    }
}

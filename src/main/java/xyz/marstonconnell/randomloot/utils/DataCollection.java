package xyz.marstonconnell.randomloot.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class DataCollection {
	public static void uploadTool(ItemStack stack) {
		CompoundNBT nbt = stack.getTag(); 
		try {
			URL add = new URL("https://marstonconnell.xyz/randomloot/api/publishtool?key=RANDOMLOOT_SECRET_KEY&tool=" + nbt);
			add.openConnection();
			System.out.println("Uploaded tool.");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}

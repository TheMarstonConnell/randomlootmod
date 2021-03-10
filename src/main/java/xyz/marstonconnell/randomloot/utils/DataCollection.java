package xyz.marstonconnell.randomloot.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class DataCollection {
	
	public static void goOffline(int count) {
		
		if(!Config.DATA_COLLECT.get()) {
			return;
		}
		
		try {
			URI v = new URI("http", "marstonconnell.xyz", "/randomloot/api/disconnect", "key=RANDOMLOOT_SECRET_KEY&count=" + count, null);
	
			System.out.println(v.toString());
			URL add = new URL(v.toString());
			HttpURLConnection uc = (HttpURLConnection) add.openConnection();
			uc.setRequestMethod("GET");
			uc.setConnectTimeout(5000);
			uc.setReadTimeout(5000);
			uc.connect();
			
			int status = uc.getResponseCode();
			
			System.out.println("Returned with: " + status);
			
			uc.disconnect();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	public static void goOnline() {
		if(!Config.DATA_COLLECT.get()) {
			return;
		}
		
		try {
			URI v = new URI("http", "marstonconnell.xyz", "/randomloot/api/connect", "key=RANDOMLOOT_SECRET_KEY", null);
	
			System.out.println(v.toString());
			URL add = new URL(v.toString());
			HttpURLConnection uc = (HttpURLConnection) add.openConnection();
			uc.setRequestMethod("GET");
			uc.setConnectTimeout(5000);
			uc.setReadTimeout(5000);
			uc.connect();
			
			int status = uc.getResponseCode();
			
			System.out.println("Returned with: " + status);
			
			uc.disconnect();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	public static void uploadTool(ItemStack stack) {
		if(!Config.DATA_COLLECT.get()) {
			return;
		}
		
		NBTToJSON nbt = new NBTToJSON();
		nbt.merge(stack.getTag());
		
		try {
			URI v = new URI("http", "marstonconnell.xyz", "/randomloot/api/publishtool", "key=RANDOMLOOT_SECRET_KEY&tool=" + stack.getItem().getRegistryName().getPath() + ":" + nbt, null);
	
			System.out.println(v.toString());
			URL add = new URL(v.toString());
			HttpURLConnection uc = (HttpURLConnection) add.openConnection();
			uc.setRequestMethod("GET");
			uc.setConnectTimeout(5000);
			uc.setReadTimeout(5000);
			uc.connect();
			
			int status = uc.getResponseCode();
			
			System.out.println("Returned with: " + status);
			
			uc.disconnect();
			
			System.out.println("Uploaded tool.");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
	}
}

package xyz.marstonconnell.randomloot.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import net.minecraft.item.ItemStack;

public class DataCollection{
	
	public static void makeRequest(URI v) {
		Runnable task = () -> {
			if(!Config.DATA_COLLECT.get()) {
				return;
			}
			
			try {
				
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
			}
			
			
		};

		Thread thread = new Thread(task);
		thread.start();

		System.out.println("Done!");
	}
	
	public static void goOffline(int count) {
		if(true) {
			return;
		}
		
		try {
			URI v = new URI("http", "marstonconnell.xyz", "/randomloot/api/disconnect", "key=RANDOMLOOT_SECRET_KEY&count=" + count, null);
			makeRequest(v);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void goOnline() {
		
			try {
				URI v = new URI("http", "marstonconnell.xyz", "/randomloot/api/connect", "key=RANDOMLOOT_SECRET_KEY", null);
				makeRequest(v);

			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
	
	}
	
	public static void uploadTool(ItemStack stack) {
		
		if(true) {
			return;
		}
		
			try {
				URI v = new URI("http", "marstonconnell.xyz", "/randomloot/api/publishtool", "key=RANDOMLOOT_SECRET_KEY&tool=" + stack.getItem().getRegistryName().getPath() + ":" + stack.getTag(), null);
				makeRequest(v);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}

		
	}
}

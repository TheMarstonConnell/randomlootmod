package xyz.marstonconnell.randomloot.utils;

import java.util.Collection;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.INBTType;

public class NBTToJSON extends CompoundNBT{

//	@Override
//	public String toString() {
//		StringBuilder stringbuilder = new StringBuilder("{");
//	      Collection<String> collection = getTagMap().keySet();
//	      
//	      for(String s : collection) {
//	         if (stringbuilder.length() != 1) {
//	            stringbuilder.append(',');
//	         }
//
//	         
//	         INBT newNBT = getTagMap().get(s);
//	         
//	         if(newNBT.getType().equals(CompoundNBT.TYPE)) {
//	        	 NBTToJSON n = new NBTToJSON();
//	        	 n.merge((CompoundNBT) newNBT);
//	        	 
//		         stringbuilder.append("\"" + handleEscape(s) + "\"").append(':').append(n);
//		         continue;
//	         }
//	         
//	         stringbuilder.append("\"" + handleEscape(s) + "\"").append(':').append(newNBT);
//	      }
//
//	      return stringbuilder.append('}').toString();
//	}	
}

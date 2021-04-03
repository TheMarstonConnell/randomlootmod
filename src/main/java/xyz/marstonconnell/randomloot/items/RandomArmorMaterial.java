package xyz.marstonconnell.randomloot.items;

import java.util.function.Supplier;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.marstonconnell.randomloot.RandomLootMod;
import xyz.marstonconnell.randomloot.tools.BaseArmorMaterial;

public class RandomArmorMaterial extends BaseArmorMaterial {

	private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
	   private final String name;
	   private final int maxDamageFactor;
	   private final int[] damageReductionAmountArray;
	   private final int enchantability;
	   private final SoundEvent soundEvent;
	   private final float toughness;
	   private final float knockbackResistance;
	   private final LazyValue<Ingredient> repairMaterial;

	public RandomArmorMaterial(String name, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability, SoundEvent soundEvent, float toughness, float knockbackResistance, Supplier<Ingredient> repairMaterial) {
		 this.name = name;
	      this.maxDamageFactor = maxDamageFactor;
	      this.damageReductionAmountArray = damageReductionAmountArray;
	      this.enchantability = enchantability;
	      this.soundEvent = soundEvent;
	      this.toughness = toughness;
	      this.knockbackResistance = knockbackResistance;
	      this.repairMaterial = new LazyValue<>(repairMaterial);
	      
	}
	
	public int getDurability(EquipmentSlotType slotIn) {
	      return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
	   }

	   public int getDamageReductionAmount(EquipmentSlotType slotIn) {
	      return this.damageReductionAmountArray[slotIn.getIndex()];
	   }

	   public int getEnchantability() {
	      return this.enchantability;
	   }

	   public SoundEvent getSoundEvent() {
	      return this.soundEvent;
	   }

	   public Ingredient getRepairMaterial() {
	      return this.repairMaterial.getValue();
	   }

	   public String getName() {
	      return RandomLootMod.MODID + ":" + this.name;
	   }

	   public float getToughness() {
	      return this.toughness;
	   }

	   /**
	    * Gets the percentage of knockback resistance provided by armor of the material. 
	    */
	   public float getKnockbackResistance() {
	      return this.knockbackResistance;
	   }

}

package xyz.marstonconnell.randomloot.items;

import net.minecraft.inventory.EquipmentSlotType;
import xyz.marstonconnell.randomloot.RandomLootMod;
import xyz.marstonconnell.randomloot.tools.BaseArmorMaterial;

public class TitaniumMaterial extends BaseArmorMaterial {

	@Override
	public int getDamageReductionAmount(EquipmentSlotType slotIn) {
		int[] dmgs = new int[] { 4, 7, 8, 4 };
		return dmgs[slotIn.getIndex()];
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return RandomLootMod.MODID + ":titanium";
	}

	@Override
	public float getToughness() {
		// TODO Auto-generated method stub
		return 0.0f;
	}

	@Override
	public float getKnockbackResistance() {
		// TODO Auto-generated method stub
		return 0;
	}

}
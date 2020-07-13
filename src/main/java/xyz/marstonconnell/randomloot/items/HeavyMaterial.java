package xyz.marstonconnell.randomloot.items;

import net.minecraft.inventory.EquipmentSlotType;
import xyz.marstonconnell.randomloot.RandomLootMod;
import xyz.marstonconnell.randomloot.tools.BaseArmorMaterial;

public class HeavyMaterial extends BaseArmorMaterial {

	@Override
	public int getDamageReductionAmount(EquipmentSlotType slotIn) {
		int[] dmgs = new int[] { 3, 6, 8, 3 };
		return dmgs[slotIn.getIndex()];
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return RandomLootMod.MODID + ":heavy";
	}

	@Override
	public float getToughness() {
		// TODO Auto-generated method stub
		return 2.0f;
	}

}

package xyz.marstonconnell.randomloot.items;

import java.util.Random;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.particles.ParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.marstonconnell.randomloot.RandomLootMod;
import xyz.marstonconnell.randomloot.init.RLItems;
import xyz.marstonconnell.randomloot.utils.handlers.NetworkHandler;

public class CaseItem extends Item {
		int rarity = 0; // 0 = basic, 1 = gold, 2 = epic
	public CaseItem(String name, int rarity) {
		super(new Properties().group(ItemGroup.MISC));
		this.rarity = rarity;
		this.setRegistryName(new ResourceLocation(RandomLootMod.MODID, name));

	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

		if (handIn == Hand.MAIN_HAND) {
			Random rand = new Random();

			if (worldIn.isRemote) {

				NetworkHandler.getNewItem(this.rarity);

			}

			for (int countparticles = 0; countparticles <= 25; ++countparticles) {
				worldIn.addParticle(ParticleTypes.CLOUD, playerIn.getPosX(), playerIn.getPosY() + 2, playerIn.getPosZ(),
						0.1D * getNegOrPos(), 0.1D * getNegOrPos(), 0.1D * getNegOrPos());
			}
			worldIn.playSound(playerIn, new BlockPos(playerIn.getPositionVec()), SoundEvents.BLOCK_WOOL_BREAK,
					SoundCategory.PLAYERS, 1.0F, 1.0F);

		}
		return super.onItemRightClick(worldIn, playerIn, handIn);

	}

	public int getNegOrPos() {
		Random rand = new Random();

		switch (rand.nextInt(3)) {
		case 0:
			return -1;
		case 1:
			return 1;
		case 2:
			return 0;
		}
		return 1;

	}

}

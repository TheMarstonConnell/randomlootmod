package xyz.marstonconnell.randomloot.entity;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import xyz.marstonconnell.randomloot.utils.Registration;

public class MovingLightTileEntity extends TileEntity implements net.minecraft.tileentity.ITickableTileEntity {
	private static final int MAX_DEATH_TIMER = 4; // number of ticks a light source persists

	private Entity holder;
	private boolean shouldDie = false;
	private int deathTimer = MAX_DEATH_TIMER;

	
	public MovingLightTileEntity() {
		super(Registration.MOVING_LIGHT_TE.get());
		this.deathTimer = MAX_DEATH_TIMER;
		this.shouldDie = false;
	}

	@Override
	public void tick() {

//		System.out.println("TICK!");
		
		if (shouldDie) {
			// // DEBUG
//			System.out.println("Should die = "+shouldDie+" with deathTimer = "+deathTimer);
			if (deathTimer > 0) {
				deathTimer--;
				return;
			} else {
				world.setBlockState(getPos(), Blocks.AIR.getDefaultState());
				world.removeTileEntity(getPos());
				return;
			}
		}

		if (holder == null || !holder.isAlive()) {
			shouldDie = true;
			world.setBlockState(getPos(), Blocks.AIR.getDefaultState());
			world.removeTileEntity(getPos());
			return;
		}
		
		Block theLightBlock = Registration.MOVING_LIGHT.get();
		if (holder == null || !holder.isAlive())
        {
			theLightBlock = null;
        }
		
		
        if (theLightBlock == null)
        {
            shouldDie = true;
			world.setBlockState(getPos(), Blocks.AIR.getDefaultState());
            world.removeTileEntity(getPos());
            return;
        }

        /*
         * check if entityLiving has moved away from the tile entity or no longer holding light emitting item set block to air
         */
        double distanceSquared = getDistanceSq(holder.getPosition(), this.getPos());
        
//    	System.out.println(distanceSquared);

        
        if (distanceSquared > 5.0D){
            this.shouldDie = true;

            return;
        }
    }


	private void setHolder(Entity t) {
		holder = t;

	}

	public void init(Entity holderIn) {
		setHolder(holderIn);
	}
	
	@Override
    public void setPos(BlockPos posIn)
    {
        pos = posIn.toImmutable();
    }
	
	
	
	private static double getDistanceSq(BlockPos parPos1, BlockPos parPos2)
    {
        return ((parPos1.getX() - parPos2.getX()) * (parPos1.getX() - parPos2.getX())
                + (parPos1.getY() - parPos2.getY()) * (parPos1.getY() - parPos2.getY())
                + (parPos1.getZ() - parPos2.getZ()) * (parPos1.getZ() - parPos2.getZ()));
    }

}

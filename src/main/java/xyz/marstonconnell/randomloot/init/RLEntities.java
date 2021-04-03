package xyz.marstonconnell.randomloot.init;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import xyz.marstonconnell.randomloot.RandomLootMod;
import xyz.marstonconnell.randomloot.entity.ThrowableWeaponEntity;

public class RLEntities {
    public static final List<EntityType<? extends Entity>> ENTITIES = Lists.newArrayList();

//	public static final EntityType<ThrowableWeaponEntity> THROWABLE_WEAPON = registerEntity(EntityType.Builder.create(ThrowableWeaponEntity::new, EntityClassification.AMBIENT).size(0.49F, 0.49F), "throwable_weapon");

    
    
	private static final EntityType registerEntity(EntityType.Builder builder, String entityName){
        ResourceLocation nameLoc = new ResourceLocation(RandomLootMod.MODID, entityName);
        return (EntityType) builder.build(entityName).setRegistryName(nameLoc);
    }
}

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

	public static final EntityType<ThrowableWeaponEntity> THROWABLE_WEAPON = createEntity("throwable_item", ThrowableWeaponEntity::new, 0.4F, 0.95F);

    
    
    private static <T extends ThrowableWeaponEntity> EntityType<T> createEntity(String name, EntityType.IFactory<T> factory, float width, float height) {
        ResourceLocation location = new ResourceLocation(RandomLootMod.MODID, name);
        EntityType<T> entity = EntityType.Builder.create(factory, EntityClassification.AMBIENT).size(width, height).setTrackingRange(64).setUpdateInterval(1).build(location.toString());
        entity.setRegistryName(location);
        ENTITIES.add(entity);

        return entity;
    }
}

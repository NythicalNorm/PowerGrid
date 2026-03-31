package org.patryk3211.powergrid.utility;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class RaycastingUtils {
    @Nullable
    public static Vec3 complexRaycast(Entity entity, Vec3 min, Vec3 max, double distance) {
        assert entity instanceof IComplexRaycast;
        IComplexRaycast checker = (IComplexRaycast) entity;

        AABB entityBB = entity.getBoundingBox().inflate(entity.getPickRadius());
        Optional<Vec3> potentialHit = entityBB.clip(min, max);
        if(entityBB.contains(min)) {
            // Casting entity inside of potential hit entity
            return checker.raycast(min, max);
        } else if(potentialHit.isPresent()) {
            if(min.distanceToSqr(potentialHit.get()) < distance) {
                // Ray hits bounding box of potential hit entity
                return checker.raycast(min, max);
            }
        }
        return null;
    }
}

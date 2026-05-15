package io.github.techtastic.cc_sable.util;

import dan200.computercraft.api.lua.IComputerSystem;
import dev.ryanhcode.sable.Sable;
import dev.ryanhcode.sable.api.physics.force.ForceGroup;
import dev.ryanhcode.sable.api.physics.force.ForceGroups;
import dev.ryanhcode.sable.api.physics.force.QueuedForceGroup;
import dev.ryanhcode.sable.companion.math.Pose3d;
import dev.ryanhcode.sable.physics.config.dimension_physics.DimensionPhysicsData;
import dev.ryanhcode.sable.sublevel.ServerSubLevel;
import dev.ryanhcode.sable.sublevel.system.SubLevelPhysicsSystem;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.jspecify.annotations.Nullable;

import java.util.*;

/**
 * Track "force requests" for sublevels because Sable be like "waaah no you cant request forces mid-tick"
 */
public class Physicker {
    private static Map<ResourceLocation, Object> LOCKS = new HashMap();

    public static void onPostPhysicsTick(SubLevelPhysicsSystem activeSystem, double v) {
        Object lock = getLock(activeSystem);
        synchronized (lock) {
            lock.notifyAll();
        }
    }

    private static synchronized Object getLock(SubLevelPhysicsSystem activeSystem) {
        ResourceLocation key = activeSystem.getLevel().dimension().location();
        Object lock = LOCKS.get(key);
        if(lock == null) lock = new Object();
        LOCKS.put(key, lock);
        return lock;
    }


    public static Object requestForces(IComputerSystem system, ServerSubLevel sublevel) {
        sublevel.enableIndividualQueuedForcesTracking(true);
        try {
            Object lock = getLock(SubLevelPhysicsSystem.get(sublevel.getLevel()));
            synchronized (lock) {
                lock.wait(100);
            }
            return createValueMap(sublevel);
        } catch (InterruptedException e) {
            return null;
        }
    }

    private static Map<String, Map<Integer, Map<String, Object>>> createValueMap(ServerSubLevel level) {
        Map<String, Map<Integer, Map<String, Object>>> returnValue = new HashMap<>();
        Map<ForceGroup, QueuedForceGroup> forceGroups = level.getQueuedForceGroups();

        {
            final Vector3dc centerOfMass = level.getMassTracker().getCenterOfMass();
            final Pose3d pose = level.logicalPose();
            final Vector3d localGravity = pose.transformNormalInverse(DimensionPhysicsData.getGravity(level.getLevel())).mul(level.getMassTracker().getMass());

            Map<Integer, Map<String, Object>> force = new HashMap<>();
            Map<String, Object> gravityMap = new HashMap<>();
            gravityMap.put("position", CCSableUtils.toLua(centerOfMass));
            gravityMap.put("force", CCSableUtils.toLua(localGravity));

            force.put(1, gravityMap);
            returnValue.put(Sable.sablePath("gravity").toString() /* thanks veil (?) for making life difficult @todo: hardcoded because veil is being annoying */, force);
        }

        if (forceGroups == null || forceGroups.isEmpty())
            return returnValue;

        SubLevelPhysicsSystem physicsSystem = SubLevelPhysicsSystem.get(level.getLevel());
        final double timeStep = 1.0 / 20.0 / physicsSystem.getConfig().substepsPerTick; // see DiagramEntity of aeronautics

        for (Map.Entry<ForceGroup, QueuedForceGroup> set : forceGroups.entrySet()) {
            Map<Integer, Map<String, Object>> force = new HashMap<>();

            List<QueuedForceGroup.PointForce> pointForces = set.getValue().getRecordedPointForces();
            if (pointForces.isEmpty())
                continue;


            int index = 1; // not a big fan of for loops
            for (QueuedForceGroup.PointForce pointForce : pointForces) {

                Map<String, Object> pointForceLua = new HashMap<>();

                pointForceLua.put("position", CCSableUtils.toLua(pointForce.point()));
                pointForceLua.put("force", CCSableUtils.toLua(new Vector3d(pointForce.force()).div(timeStep)));

                force.put(index, pointForceLua);
                index++;
            }

            ResourceLocation key = ForceGroups.REGISTRY.getKey(set.getKey());
            returnValue.put(key.toString(), force);
        }

        return returnValue;
    }
}

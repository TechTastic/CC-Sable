package io.github.techtastic.cc_sable.apis;

import dan200.computercraft.api.lua.*;
import dev.ryanhcode.sable.physics.config.dimension_physics.DimensionPhysicsData;
import io.github.techtastic.cc_sable.util.CCSableUtils;
import org.joml.Vector3d;
import org.jspecify.annotations.NonNull;

import java.util.Map;

public class AerodynamicsAPI implements ILuaAPI {
    private final IComputerSystem system;

    public AerodynamicsAPI(IComputerSystem system) {
        this.system = system;
    }

    @Override
    public String @NonNull [] getNames() {
        return new String[] {"aero", "aerodynamics"};
    }

    @LuaFunction(mainThread = true)
    public final double getAirPressure(double x, double y, double z) {
        return DimensionPhysicsData.getAirPressure(this.system.getLevel(), new Vector3d(x, y, z));
    }

    @LuaFunction(mainThread = true)
    public final Map<String, Double> getGravity() {
        return CCSableUtils.toLua(DimensionPhysicsData.getGravity(this.system.getLevel()));
    }

    @LuaFunction(mainThread = true)
    public final Map<String, Double> getMagneticNorth() {
        return CCSableUtils.toLua(DimensionPhysicsData.getMagneticNorth(this.system.getLevel()).get(new Vector3d()));
    }

    @LuaFunction(mainThread = true)
    public final double getUniversalDrag() {
        return DimensionPhysicsData.getUniversalDrag(this.system.getLevel());
    }

    @LuaFunction(mainThread = true)
    public final Map<String, Object> getRaw() {
        return CCSableUtils.toLua(DimensionPhysicsData.of(this.system.getLevel()));
    }

    @LuaFunction(mainThread = true)
    public final Map<String, Object> getDefault() {
        return CCSableUtils.toLua(DimensionPhysicsData.getDefault(this.system.getLevel()));
    }
}

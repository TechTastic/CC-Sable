package io.github.techtastic.cc_sable.apis;

import dan200.computercraft.api.lua.IComputerSystem;
import dan200.computercraft.api.lua.ILuaAPI;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dev.ryanhcode.sable.Sable;
import dev.ryanhcode.sable.api.SubLevelHelper;
import dev.ryanhcode.sable.api.sublevel.SubLevelContainer;
import dev.ryanhcode.sable.companion.SableCompanion;
import dev.ryanhcode.sable.companion.SubLevelAccess;
import dev.ryanhcode.sable.physics.config.dimension_physics.DimensionPhysicsData;
import dev.ryanhcode.sable.sublevel.ServerSubLevel;
import dev.ryanhcode.sable.sublevel.SubLevel;
import io.github.techtastic.cc_sable.util.CCSableUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.jspecify.annotations.NonNull;

import java.util.Map;

public class SubLevelAPI implements ILuaAPI {
    private final IComputerSystem system;

    public SubLevelAPI(IComputerSystem system) {
        this.system = system;
    }

    private ServerSubLevel getSublevel() throws LuaException {
        SubLevel access = Sable.HELPER.getContaining(this.system.getLevel(), this.system.getPosition());
        if (!(access instanceof ServerSubLevel server)) throw new LuaException("This computer is not on a Sub-Level!");
        return server;
    }

    @Override
    public final String @NonNull [] getNames() {
        return new String[] {"sublevel"};
    }

    @LuaFunction
    public final boolean isInPlotGrid() {
        return Sable.HELPER.isInPlotGrid(this.system.getLevel(), this.system.getPosition());
    }

    @LuaFunction
    public final String getUniqueId() throws LuaException {
        return getSublevel().getUniqueId().toString();
    }

    @LuaFunction
    public final String getName() throws LuaException {
        return getSublevel().getName();
    }

    @LuaFunction
    public final void setName(String newName) throws LuaException {
        getSublevel().setName(newName);
    }

    @LuaFunction
    public final Map<String, Object> getLogicalPose() throws LuaException {
        return CCSableUtils.toLua(getSublevel().logicalPose());
    }

    @LuaFunction
    public final Map<String, Object> getLastPose() throws LuaException {
        return CCSableUtils.toLua(getSublevel().lastPose());
    }

    @LuaFunction
    public final Map<String, Double> getVelocity() {
        Vector3d pos = new Vector3d(this.system.getPosition().getX(), this.system.getPosition().getY(), this.system.getPosition().getZ());
        return CCSableUtils.toLua(SableCompanion.INSTANCE.getVelocity(this.system.getLevel(), pos));
    }

    @LuaFunction
    public final Map<String, Double> getLinearVelocity() throws LuaException {
        return CCSableUtils.toLua(getSublevel().latestLinearVelocity);
    }

    @LuaFunction
    public final Map<String, Double> getAngularVelocity() throws LuaException {
        return CCSableUtils.toLua(getSublevel().latestAngularVelocity);
    }

    @LuaFunction
    public final Map<String, Double> getCenterOfMass() throws LuaException {
        Vector3dc com = getSublevel().getMassTracker().getCenterOfMass();
        if (com == null) return null;
        return CCSableUtils.toLua(com);
    }

    @LuaFunction
    public final double getMass() throws LuaException {
        return getSublevel().getMassTracker().getMass();
    }

    @LuaFunction
    public final double getInverseMass() throws LuaException {
        return getSublevel().getMassTracker().getInverseMass();
    }

    @LuaFunction
    public final Map<Double, Map<Double, Double>> getInertiaTensor() throws LuaException {
        return CCSableUtils.toLua(getSublevel().getMassTracker().getInertiaTensor());
    }

    @LuaFunction
    public final Map<Double, Map<Double, Double>> getInverseInertiaTensor() throws LuaException {
        return CCSableUtils.toLua(getSublevel().getMassTracker().getInverseInertiaTensor());
    }
}

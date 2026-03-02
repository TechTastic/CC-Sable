package io.github.techtastic.cc_sable.apis;

import dan200.computercraft.api.lua.IComputerSystem;
import dan200.computercraft.api.lua.ILuaAPI;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dev.ryanhcode.sable.companion.SableCompanion;
import dev.ryanhcode.sable.companion.SubLevelAccess;
import io.github.techtastic.cc_sable.util.CCSableUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import org.joml.Vector3d;
import org.jspecify.annotations.NonNull;

import java.util.Map;

public class SubLevelAPI implements ILuaAPI {
    private final IComputerSystem system;

    public SubLevelAPI(IComputerSystem system) {
        this.system = system;
    }

    private SubLevelAccess getSublevel() throws LuaException {
        SubLevelAccess access = SableCompanion.INSTANCE.getContaining(this.system.getLevel(), this.system.getPosition());
        if (access == null) throw new LuaException("This computer is not on a Sub-Level!");
        return access;
    }

    @Override
    public final String @NonNull [] getNames() {
        return new String[] {"sublevel"};
    }

    @LuaFunction
    public final boolean isInPlotGrid() {
        return SableCompanion.INSTANCE.isInPlotGrid(this.system.getLevel(), this.system.getPosition());
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
}

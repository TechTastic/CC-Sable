package io.github.techtastic.cc_sable.apis;

import dan200.computercraft.api.lua.IComputerSystem;
import dan200.computercraft.api.lua.ILuaAPI;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dev.ryanhcode.sable.companion.SableCompanion;
import dev.ryanhcode.sable.companion.SubLevelAccess;
import io.github.techtastic.cc_sable.util.CCSableUtils;
import org.jspecify.annotations.NonNull;

import java.util.Map;

public class SubLevelAPI implements ILuaAPI {
    private IComputerSystem system;

    public SubLevelAPI(IComputerSystem system) {
        this.system = system;
    }

    private SubLevelAccess getSublevel() throws LuaException {
        SubLevelAccess access = SableCompanion.INSTANCE.getContaining(this.system.getLevel(), this.system.getPosition());
        if (access == null) throw new LuaException("This computer is not on a Sub-Level!");
        return access;
    }

    @Override
    public String @NonNull [] getNames() {
        return new String[] {"sublevel"};
    }

    @LuaFunction
    public String getUniqueId() throws LuaException {
        return getSublevel().getUniqueId().toString();
    }

    @LuaFunction
    public String getName() throws LuaException {
        return getSublevel().getName();
    }

    @LuaFunction
    public Map<String, Object> getLogicalPose() throws LuaException {
        return CCSableUtils.toLua(getSublevel().logicalPose());
    }

    @LuaFunction
    public Map<String, Object> getLastPose() throws LuaException {
        return CCSableUtils.toLua(getSublevel().lastPose());
    }
}

package io.github.techtastic.cc_sable.util;

import dan200.computercraft.api.lua.LuaFunction;
import dev.ryanhcode.sable.physics.config.dimension_physics.BezierResourceFunction;

import java.util.List;
import java.util.Map;

public class LuaBezierResourceFunction {
    private final BezierResourceFunction function;

    public LuaBezierResourceFunction(BezierResourceFunction function) {
        this.function = function;
    }

    @LuaFunction(mainThread = true)
    public final List<Map<String, Double>> getPoints() {
        return this.function.getPoints().stream().map(point -> Map.of(
                "altitude", point.altitude(),
                "value", point.value(),
                "slope", point.slope()
        )).toList();
    }

    @LuaFunction(mainThread = true)
    public final double evaluateFunction(double altitude) {
        return this.function.evaluateFunction(altitude);
    }
}

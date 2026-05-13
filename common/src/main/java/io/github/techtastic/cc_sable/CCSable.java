package io.github.techtastic.cc_sable;

import dan200.computercraft.api.ComputerCraftAPI;
import dan200.computercraft.api.detail.VanillaDetailRegistries;
import dev.ryanhcode.sable.physics.config.block_properties.PhysicsBlockPropertyHelper;
import dev.ryanhcode.sable.physics.floating_block.FloatingBlockMaterial;
import io.github.techtastic.cc_sable.apis.AerodynamicsAPI;
import io.github.techtastic.cc_sable.apis.SubLevelAPI;

import java.util.Map;

public final class CCSable {
    public static final String MOD_ID = "cc_sable";

    public static void init() {
        ComputerCraftAPI.registerAPIFactory(SubLevelAPI::new);
        ComputerCraftAPI.registerAPIFactory(AerodynamicsAPI::new);

        VanillaDetailRegistries.BLOCK_IN_WORLD.addProvider((data, object) -> data.put("sable", Map.of(
                "mass", PhysicsBlockPropertyHelper.getMass(object.level(), object.pos(), object.state()),
                "friction", PhysicsBlockPropertyHelper.getFriction(object.state()),
                "restitution", PhysicsBlockPropertyHelper.getRestitution(object.state()),
                "volume", PhysicsBlockPropertyHelper.getVolume(object.state()),
                "floatingScale", PhysicsBlockPropertyHelper.getFloatingScale(object.state()),
                "floatingMaterial", (PhysicsBlockPropertyHelper.getFloatingMaterial(object.state()) instanceof FloatingBlockMaterial(
                        boolean preventSelfLift, boolean scaleWithPressure, boolean scaleWithGravity,
                        double liftStrength, double transitionSpeed, double slowVerticalFriction,
                        double fastVerticalFriction, double slowHorizontalFriction, double fastHorizontalFriction
                )) ? Map.of(
                        "fastHorizontalFriction", fastHorizontalFriction,
                        "fastVerticalFriction", fastVerticalFriction,
                        "slowHorizontalFriction", slowHorizontalFriction,
                        "slowVerticalFriction", slowVerticalFriction,
                        "liftStrength", liftStrength,
                        "transitionSpeed", transitionSpeed,
                        "preventSelfLift", preventSelfLift,
                        "scaleWithGravity", scaleWithGravity,
                        "scaleWithPressure", scaleWithPressure
                ) : Map.of()
        )));
    }
}

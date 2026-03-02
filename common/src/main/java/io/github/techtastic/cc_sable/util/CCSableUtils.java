package io.github.techtastic.cc_sable.util;

import dev.ryanhcode.sable.companion.math.Pose3dc;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

import java.util.HashMap;
import java.util.Map;

public class CCSableUtils {
    public static Map<String, Object> toLua(Pose3dc pose) {
        return Map.of(
                "position", toLua(pose.position()),
                "orientation", toLua(pose.orientation()),
                "scale", toLua(pose.scale()),
                "rotationPoint", toLua(pose.rotationPoint())
        );
    }

    public static Map<String, Double> toLua(Vector3dc vector) {
        return Map.of(
                "x", vector.x(),
                "y", vector.y(),
                "z", vector.z()
        );
    }

    public static Map<String, Double> toLua(Quaterniondc quaternion) {
        return Map.of(
                "x", quaternion.x(),
                "y", quaternion.y(),
                "z", quaternion.z(),
                "w", quaternion.z()
        );
    }
}

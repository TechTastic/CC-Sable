package io.github.techtastic.cc_sable.util;

import dev.ryanhcode.sable.companion.math.Pose3dc;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3dc;
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

    public static Map<Double, Map<Double, Double>> toLua(Matrix3dc matrix) {
        Map<Double, Map<Double, Double>> result = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            Map<Double, Double> row = new HashMap<>();
            for (int j = 0; j < 3; j++) {
                row.put((double) j + 1, matrix.get(i, j));
            }
            result.put((double) i + 1, row);
        }
        return result;
    }
}

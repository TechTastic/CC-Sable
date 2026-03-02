package io.github.techtastic.cc_sable.neoforge;

import net.neoforged.fml.common.Mod;

import io.github.techtastic.cc_sable.CCSable;

@Mod(CCSable.MOD_ID)
public final class CCSableNeoForge {
    public CCSableNeoForge() {
        // Run our common setup.
        CCSable.init();
    }
}

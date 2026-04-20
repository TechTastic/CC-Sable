package io.github.techtastic.cc_sable;

import dan200.computercraft.api.ComputerCraftAPI;
import io.github.techtastic.cc_sable.apis.AerodynamicsAPI;
import io.github.techtastic.cc_sable.apis.SubLevelAPI;

public final class CCSable {
    public static final String MOD_ID = "cc_sable";

    public static void init() {
        ComputerCraftAPI.registerAPIFactory(SubLevelAPI::new);
        ComputerCraftAPI.registerAPIFactory(AerodynamicsAPI::new);
    }
}

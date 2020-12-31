package com.scouter.nomorelakes;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;

public class Configs {

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final Configs CONFIGS = new Configs(BUILDER);
    public static final ForgeConfigSpec spec = BUILDER.build();

    private static boolean loaded = false;
    private static List<Runnable> loadActions = new ArrayList<>();

    public static void setLoaded() {
        if (!loaded)
            loadActions.forEach(Runnable::run);
        loaded = true;
    }

    //IDE says these are unused, but we can't be too sure with Forge...
    public static boolean isLoaded() {return loaded;}

    public static void onLoad(Runnable action) {
        if (loaded) action.run();
        else loadActions.add(action);
    }

    private final ForgeConfigSpec.BooleanValue disableWaterLakes;
    private final ForgeConfigSpec.BooleanValue disableLavaLakes;
    private final ForgeConfigSpec.BooleanValue debugMode;

    public boolean disableWaterLakes() {return disableWaterLakes.get();}
    public boolean disableLavaLakes() {return disableLavaLakes.get();}
    public boolean debugMode() {return debugMode.get();}

    Configs(ForgeConfigSpec.Builder builder) {
        builder.push("Configs");

        disableWaterLakes = builder
                .comment("Disable all water lakes? (Both surface and underground)\n" +
                        "Default is true.")
                .define("disableWaterLakes", true);

        disableLavaLakes = builder
                .comment("Disable all lava lakes? (Both surface and underground, in both overworld and nether)\n" +
                        "Default is true.")
                .define("disableLavaLakes", true);

        debugMode = builder
                .comment("Spams things to console when the biomes are being loaded.\n" +
                        "Default is false.")
                .define("debugMode", false);

        builder.pop();
    }
}

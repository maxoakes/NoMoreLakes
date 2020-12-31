package com.scouter.nomorelakes;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(NoMoreLakes.MOD_ID)
public class NoMoreLakes {
    public static final String MOD_ID = "nomorelakes";

    public NoMoreLakes()
    {
        final IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        final IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Configs.spec);

        forgeBus.register(new BiomeLoadingHandler());
        forgeBus.register(this);
    }
}

package com.scouter.nomorelakes;

import net.minecraft.block.Blocks;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = NoMoreLakes.MOD_ID)
public class BiomeLoadingHandler
{
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void BiomeLoadingIntercept(final BiomeLoadingEvent event)
    {
        List<Supplier<ConfiguredFeature<?, ?>>> toRemove = new ArrayList<>();
        for (Supplier<ConfiguredFeature<?, ?>> d : event.getGeneration().getFeatures(GenerationStage.Decoration.LAKES)) {
            try {
                Feature<?> f = d.get().getConfig().func_241856_an_().map(ConfiguredFeature::getFeature).collect(Collectors.toList()).get(0);

                if (f instanceof LakesFeature)
                {
                    LakesFeature l = (LakesFeature) f;

                    BlockStateFeatureConfig bsfc = (BlockStateFeatureConfig) d.get().getConfig().func_241856_an_().map(ConfiguredFeature::getConfig).collect(Collectors.toList()).get(0);
                    if (Configs.CONFIGS.debugMode()) {
                        System.out.println("Feat: " + l.getClass() + " in biome " + event.getName());
                        System.out.println("Conf: " + bsfc.state.getBlock());
                    }
                    if (bsfc.state.getBlock() == Blocks.LAVA && Configs.CONFIGS.disableLavaLakes()) toRemove.add(d);
                    if (bsfc.state.getBlock() == Blocks.WATER && Configs.CONFIGS.disableWaterLakes()) toRemove.add(d);

                }
            }
            catch(Exception e){System.out.println("Error identifying Lake Feature to remove it from the decorations list of a biome");}
        }
        for (Supplier<ConfiguredFeature<?, ?>> r : toRemove)
        {
            LakesFeature l = (LakesFeature) r.get().getConfig().func_241856_an_().map(ConfiguredFeature::getFeature).collect(Collectors.toList()).get(0);
            BlockStateFeatureConfig bsfc = (BlockStateFeatureConfig) r.get().getConfig().func_241856_an_().map(ConfiguredFeature::getConfig).collect(Collectors.toList()).get(0);
            if (Configs.CONFIGS.debugMode()) System.out.println("Removed "+r.get().getConfig().func_241856_an_().map(ConfiguredFeature::getFeature).collect(Collectors.toList()).get(0)+" for "+bsfc.state.getBlock());
            event.getGeneration().getFeatures(GenerationStage.Decoration.LAKES).remove(r);
        }
    }
}

package com.hazebyte.crate.cratereloaded.metric;

import com.hazebyte.crate.api.CratePlugin;
import com.hazebyte.crate.api.crate.AnimationType;
import com.hazebyte.crate.api.crate.Crate;
import com.hazebyte.crate.api.crate.CrateType;
import com.hazebyte.crate.cratereloaded.CorePlugin;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.AdvancedPie;
import org.bstats.charts.SingleLineChart;
import org.bukkit.plugin.java.JavaPlugin;

public class CrateMetrics extends Metrics {

    public CrateMetrics(JavaPlugin plugin) {
        super(plugin, 1661);

        addCharts();
    }

    private void addCharts() {
        addCustomChart(new SingleLineChart("crate_count", () -> getCrateCount(CorePlugin.getPlugin())));
        addCustomChart(new SingleLineChart("reward_chart", () -> getRewardCount(CorePlugin.getPlugin())));
        addCustomChart(new AdvancedPie("crate_type", () -> getCrateType(CorePlugin.getPlugin())));
        addCustomChart(new AdvancedPie("animation_type", () -> getAnimationTypes(CorePlugin.getPlugin())));
    }

    private int getCrateCount(CratePlugin plugin) {
        return plugin.getCrateRegistrar().getCrates().size();
    }

    private int getRewardCount(CratePlugin plugin) {
        BiFunction<Integer, Crate, Integer> accumulator =
                ((accum, crate) -> accum + crate.getRewards().size());
        BinaryOperator<Integer> combiner = (a, b) -> a + b;
        return plugin.getCrateRegistrar().getCrates().stream().reduce(0, accumulator, combiner);
    }

    private Map<String, Integer> getCrateType(CratePlugin plugin) {
        Map<String, Integer> crateTypes = new HashMap<>();
        List<CrateType> types = plugin.getCrateRegistrar().getCrates().stream()
                .map(Crate::getType)
                .collect(Collectors.toList());
        for (CrateType type : types) {
            crateTypes.put(type.name(), crateTypes.getOrDefault(type.name(), 0) + 1);
        }
        return crateTypes;
    }

    private Map<String, Integer> getAnimationTypes(CratePlugin plugin) {
        Map<String, Integer> animationTypes = new HashMap<>();
        List<AnimationType> types = plugin.getCrateRegistrar().getCrates().stream()
                .map(Crate::getAnimationType)
                .collect(Collectors.toList());
        for (AnimationType type : types) {
            animationTypes.put(type.name(), animationTypes.getOrDefault(type.name(), 0) + 1);
        }
        return animationTypes;
    }
}

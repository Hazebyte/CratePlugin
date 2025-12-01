package com.hazebyte.crate.cratereloaded.crate.animation;

import com.hazebyte.crate.api.util.Messages;
import com.hazebyte.crate.cratereloaded.CorePlugin;
import java.util.LinkedList;
import java.util.List;

public class AnimationSpeed {
    private final List<Double> weights;
    private final List<Integer> speeds;
    private final List<Double> minimums;
    private double total;
    private final double length;

    public AnimationSpeed(double length) {
        weights = new LinkedList<>();
        speeds = new LinkedList<>();
        minimums = new LinkedList<>();
        total = 0;
        this.length = length;
    }

    public void add(double weight, int speed) {
        if (total >= 1) {
            CorePlugin.getPlugin().getLogger().info(Messages.ERROR + "Incorrect Animation Weights");
            return;
        }
        weights.add(weight);
        speeds.add(speed);
        minimums.add(weight * this.length);
        total += weight;
    }

    public int getNumberOfPrizes() {
        int weightSize = weights.size();
        int minSize = minimums.size();
        if (minSize != weightSize) {
            CorePlugin.getPlugin().getLogger().severe("Different Size");
            return -1;
        }

        double numberOfPrizes = 0;
        for (int i = 0; i < minSize; i++) {
            double minimum = minimums.get(i);
            int speed = speeds.get(i);
            numberOfPrizes += (minimum / speed);
        }
        return (int) Math.ceil(numberOfPrizes);
    }

    public int getSpeed(int index) {
        return speeds.get(index);
    }

    public int getIndex(int timeLapsed) {
        int minimumSum = 0;
        for (int i = 0; i < minimums.size(); i++) {
            minimumSum += minimums.get(i);
            if (timeLapsed <= minimumSum) {
                return (i);
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        return String.valueOf(weights) + speeds;
    }
}

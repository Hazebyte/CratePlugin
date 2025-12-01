package com.hazebyte.crate.cratereloaded.crate.generator.rules;

import com.hazebyte.crate.api.crate.reward.Reward;
import java.util.function.Predicate;
import org.bukkit.entity.Player;

public class PermissionNotAllowedRule implements Predicate<Reward> {

    private Player player;

    public PermissionNotAllowedRule(Player player) {
        this.player = player;
    }

    @Override
    public boolean test(Reward reward) {
        return !reward.hasPermission(player);
    }
}

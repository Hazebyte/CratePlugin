package com.hazebyte.crate.cratereloaded.crate.generator.rules;

import com.hazebyte.crate.api.crate.reward.Reward;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class PermissionNotAllowedRuleTest {

    @Test
    public void verifyRewardNotAllowedIfPermissionExists() {
        Player player = Mockito.mock(Player.class);
        Reward reward = Mockito.mock(Reward.class);
        Mockito.when(reward.hasPermission(player)).thenReturn(true);
        PermissionNotAllowedRule rule = new PermissionNotAllowedRule(player);
        Assertions.assertFalse(rule.test(reward));
    }

    @Test
    public void verifyRewardAllowedIfPermissionDoesNotExist() {
        Player player = Mockito.mock(Player.class);
        Reward reward = Mockito.mock(Reward.class);
        Mockito.when(reward.hasPermission(player)).thenReturn(false);
        PermissionNotAllowedRule rule = new PermissionNotAllowedRule(player);
        Assertions.assertTrue(rule.test(reward));
    }
}

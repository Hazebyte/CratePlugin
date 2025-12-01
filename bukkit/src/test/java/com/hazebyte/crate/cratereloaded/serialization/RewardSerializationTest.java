package com.hazebyte.crate.cratereloaded.serialization;

import com.hazebyte.crate.BukkitTest;
import com.hazebyte.crate.api.crate.reward.Reward;
import com.hazebyte.crate.api.util.ItemBuilder;
import com.hazebyte.crate.cratereloaded.model.RewardImpl;
import java.util.Arrays;
import java.util.stream.Stream;
import org.bukkit.Material;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class RewardSerializationTest extends BukkitTest {

    @ParameterizedTest
    @MethodSource("provideForSerializeRewardToString")
    public void serializeReward_toString_returnsResult(Reward reward, String expected) {
        String result = RewardSerialization.serializeToString(reward);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expected, result);
    }

    @MethodSource("provideForSerializeReward")
    private static Stream<Arguments> provideForSerializeRewardToString() {
        Reward rewardWithAttributes = new RewardImpl();
        rewardWithAttributes.setChance(5);
        rewardWithAttributes.setDisplayItem(new ItemBuilder(Material.STONE, 1)
                .displayName("je suis omelette")
                .asItemStack());
        rewardWithAttributes.setOpenMessage(Arrays.asList("hello"));
        rewardWithAttributes.setCommands(Arrays.asList("nuke"));
        rewardWithAttributes.setBroadcastMessage(Arrays.asList("qwerty"));
        rewardWithAttributes.setItems(Arrays.asList(
                new ItemBuilder(Material.CAKE, 1).asItemStack(), new ItemBuilder(Material.DIAMOND, 1).asItemStack()));
        return Stream.of(
                Arguments.of(
                        rewardWithAttributes,
                        "msg:(hello), broadcast:(qwerty), item:(CAKE 1), item:(DIAMOND 1), chance:(5.0),"
                                + " display:(STONE 1 name:je_suis_omelette), cmd:(nuke)"),
                Arguments.of(new RewardImpl(), "chance:(0.0)"));
    }
}

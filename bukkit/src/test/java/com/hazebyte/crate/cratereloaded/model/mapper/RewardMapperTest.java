package com.hazebyte.crate.cratereloaded.model.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import com.hazebyte.crate.api.crate.reward.Reward;
import com.hazebyte.crate.cratereloaded.CorePlugin;
import com.hazebyte.crate.cratereloaded.model.RewardImpl;
import com.hazebyte.crate.cratereloaded.model.RewardV2;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RewardMapperTest {

    private RewardMapper rewardMapper = Mappers.getMapper(RewardMapper.class);

    private final ItemStack DISPLAY_ITEM = new ItemStack(Material.DIAMOND, 2);
    private final double CHANCE = 3;
    private final List<String> COMMANDS = Arrays.asList("CMD_1", "CMD_2");
    private final List<ItemStack> ITEMS = Arrays.asList(new ItemStack(Material.DIRT));
    private final List<String> PERMISSIONS = Arrays.asList("PERM");
    private final List<String> BROADCAST_MESSAGES = Arrays.asList("BROADCAST");
    private final List<String> OPEN_MESSAGES = Arrays.asList("OPEN");

    private static ServerMock server;
    private static CorePlugin plugin;

    @BeforeAll
    public static void setUp() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(CorePlugin.class);
    }

    @AfterAll
    public static void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    void fromImplementation() {
        RewardImpl impl = createImplementation();
        RewardV2 expectedReward = createRewardV2();

        RewardV2 mappedReward = rewardMapper.fromImplementation(impl);

        assertEquals(expectedReward, mappedReward);
    }

    @Test
    void fromImplementation_list() {
        List<Reward> impl = Arrays.asList(createImplementation());
        List<RewardV2> expected = Arrays.asList(createRewardV2());

        List<RewardV2> mappedReward = rewardMapper.fromImplementation(impl);

        assertEquals(expected, mappedReward);
    }

    private RewardImpl createImplementation() {
        return new RewardImpl(
                "display:(DIAMOND 2), chance:(3), cmd:(CMD_1), cmd:(CMD_2), item:(DIRT 1), broadcast:(BROADCAST), msg:(OPEN), permission:(PERM), unique:()");
    }

    private RewardV2 createRewardV2() {
        return RewardV2.builder()
                .displayItem(Optional.of(DISPLAY_ITEM))
                .chance(CHANCE)
                .items(ITEMS)
                .commands(COMMANDS)
                .broadcastMessage(BROADCAST_MESSAGES)
                .exclusivePermissions(PERMISSIONS)
                .openMessage(OPEN_MESSAGES)
                .unique(true)
                .build();
    }
}

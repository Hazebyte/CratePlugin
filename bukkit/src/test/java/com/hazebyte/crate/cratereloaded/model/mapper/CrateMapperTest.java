package com.hazebyte.crate.cratereloaded.model.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import com.hazebyte.crate.api.crate.AnimationType;
import com.hazebyte.crate.api.crate.CrateType;
import com.hazebyte.crate.api.crate.EndAnimationType;
import com.hazebyte.crate.api.crate.reward.Reward;
import com.hazebyte.crate.cratereloaded.CorePlugin;
import com.hazebyte.crate.cratereloaded.crate.animation.Animation;
import com.hazebyte.crate.cratereloaded.model.CrateImpl;
import com.hazebyte.crate.cratereloaded.model.CrateV2;
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
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CrateMapperTest {

    private final String CRATE_NAME = "crateName";
    private final CrateType CRATE_TYPE = CrateType.KEY;
    private final String DISPLAY_NAME = "displayName";
    private final ItemStack DISPLAY_ITEM = new ItemStack(Material.BOW);
    private final AnimationType ANIMATION_TYPE = AnimationType.CSGO;
    private final EndAnimationType END_ANIMATION_TYPE = EndAnimationType.BLANK;
    private final ItemStack CRATE_ITEM = new ItemStack(Material.CHEST);
    private final double COST = 1.0;
    private final boolean CAN_PURCHASE = true;
    private final String OPEN_MESSAGE_STRING = "openMessage";
    private final String BROADCAST_MESSAGE_STRING = "broadcastMessage";
    private final List<String> OPEN_MESSAGE = Arrays.asList(OPEN_MESSAGE_STRING);
    private final List<String> BROADCAST_MESSAGE = Arrays.asList(BROADCAST_MESSAGE_STRING);
    private final boolean CAN_PREVIEW = true;
    private final int PREVIEW_ROWS = 5;
    private final boolean CONFIRM_BEFORE_USE = true;
    private final ItemStack ACCEPT_BUTTON = new ItemStack(Material.STONE);
    private final ItemStack DECLINE_BUTTON = new ItemStack(Material.TRIPWIRE);
    private final int MINIMUM_REWARD = 10;
    private final int MAXIMUM_REWARD = 15;
    private final List<Reward> REWARDS = Arrays.asList(
            new RewardImpl("chance:(1), item:(diamond 1), cmd:(heal {player})"),
            new RewardImpl("chance:(2), item:(dirt 1), cmd:(test {player})"));
    private final List<RewardV2> REWARDS_V2 = Arrays.asList(
            RewardV2.builder()
                    .chance(1)
                    .items(Arrays.asList(new ItemStack(Material.DIAMOND)))
                    .displayItem(Optional.of(new ItemStack(Material.DIAMOND)))
                    .commands(Arrays.asList("heal {player}"))
                    .build(),
            RewardV2.builder()
                    .chance(2)
                    .items(Arrays.asList(new ItemStack(Material.DIRT)))
                    .displayItem(Optional.of(new ItemStack(Material.DIRT)))
                    .commands(Arrays.asList("test {player}"))
                    .build());
    private final List<Reward> CONSTANT_REWARDS = Arrays.asList();
    private final List<String> HOLOGRAPHIC_TEXT = Arrays.asList("HOLOGRAPHIC");

    @Mock
    private Animation ANIMATION;

    private final CrateMapper mapper = Mappers.getMapper(CrateMapper.class);
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
    public void fromImplementation_succeeds() {
        // arrange
        CrateImpl impl = createImpl();
        CrateV2 expectedCrate = createModel();

        // act
        CrateV2 crate = mapper.fromImplementation(impl);

        // assert
        assertNotNull(crate);
        assertEquals(expectedCrate, crate);
    }

    @Test
    public void toImplementation_succeeds() {
        // arrange
        CrateImpl expectedImpl = createImpl();
        CrateV2 crateV2 = createModel();

        // act
        CrateImpl crateImpl = mapper.toImplementation(crateV2);

        // assert
        assertNotNull(crateImpl);
        assertEquals(expectedImpl, crateImpl);
    }

    private CrateImpl createImpl() {
        CrateImpl impl = new CrateImpl(CRATE_NAME, CRATE_TYPE);
        impl.setDisplayName(DISPLAY_NAME);
        impl.setDisplayItem(DISPLAY_ITEM);
        impl.setAnimationType(ANIMATION_TYPE);
        impl.setEndAnimationType(END_ANIMATION_TYPE);
        impl.setAnimation(ANIMATION); // Goes after since setting animationType overrides
        impl.setItem(CRATE_ITEM);
        impl.setCost(COST);
        impl.setBuyable(CAN_PURCHASE);
        impl.setOpenMessage(OPEN_MESSAGE);
        impl.setBroadcastMessage(BROADCAST_MESSAGE);
        impl.setPreviewable(CAN_PREVIEW);
        impl.setPreviewRows(PREVIEW_ROWS);
        impl.setConfirmationToggle(CONFIRM_BEFORE_USE);
        impl.setAcceptButton(ACCEPT_BUTTON);
        impl.setDeclineButton(DECLINE_BUTTON);
        impl.setMinimumRewards(MINIMUM_REWARD);
        impl.setMaximumRewards(MAXIMUM_REWARD);
        impl.setRewards(REWARDS);
        impl.setHolographicText(HOLOGRAPHIC_TEXT);
        return impl;
    }

    private CrateV2 createModel() {
        return CrateV2.builder()
                .crateName(CRATE_NAME)
                .type(CRATE_TYPE)
                .uuid(CRATE_NAME + ":" + CRATE_TYPE.name())
                .item(CRATE_ITEM)
                .animation(ANIMATION)
                .animationType(ANIMATION_TYPE)
                .endAnimationType(END_ANIMATION_TYPE)
                .rewards(REWARDS_V2)
                .minimumRewards(MINIMUM_REWARD)
                .maximumRewards(MAXIMUM_REWARD)
                .holographicText(HOLOGRAPHIC_TEXT)
                .openMessage(Arrays.asList(OPEN_MESSAGE_STRING))
                .broadcastMessage(Arrays.asList(BROADCAST_MESSAGE_STRING))
                .confirmBeforeUse(CONFIRM_BEFORE_USE)
                .displayName(Optional.of(DISPLAY_NAME))
                .displayItem(Optional.of(DISPLAY_ITEM))
                .previewRows(PREVIEW_ROWS)
                .acceptButton(ACCEPT_BUTTON)
                .declineButton(DECLINE_BUTTON)
                .forSale(CAN_PURCHASE)
                .salePrice(COST)
                .build();
    }
}

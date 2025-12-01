package com.hazebyte.crate.cratereloaded.util;

import static com.hazebyte.crate.test.ItemStackMockData.ONE_INVENTORY_OF_STONE;
import static com.hazebyte.crate.test.ItemStackMockData.ONE_INVENTORY_OF_STONE_PLUS_ONE_EXTRA;
import static com.hazebyte.crate.test.ItemStackMockData.ONE_STONE;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.hazebyte.crate.BukkitTest;
import com.hazebyte.crate.api.util.ItemBuilder;
import com.hazebyte.crate.test.InventoryMockData;
import com.hazebyte.crate.test.ItemStackMockData;
import com.hazebyte.crate.test.PlayerMockData;
import java.util.Optional;
import java.util.stream.Stream;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ItemCalculatorTest extends BukkitTest {

    private PlayerMock playerMock;

    @BeforeEach
    public void setup() {
        playerMock = server.addPlayer();
    }

    @ParameterizedTest
    @MethodSource("provideArgsToGetNumberOfSlotsRequired")
    public void getNumberOfSlotsRequired(ItemStack item, int expectedNumberOfSlots) {
        int slots = ItemCalculatorUtil.getNumberOfSlotsRequired(item);
        Assertions.assertEquals(expectedNumberOfSlots, slots);
    }

    private static Stream<Arguments> provideArgsToGetNumberOfSlotsRequired() {
        return Stream.of(
                Arguments.of(new ItemBuilder(Material.STONE, 1).asItemStack(), 1),
                Arguments.of(new ItemBuilder(Material.STONE, 63).asItemStack(), 1),
                Arguments.of(new ItemBuilder(Material.STONE, 64).asItemStack(), 1),
                Arguments.of(new ItemBuilder(Material.STONE, 65).asItemStack(), 2),
                Arguments.of(new ItemBuilder(Material.STONE, 128).asItemStack(), 2),
                Arguments.of(new ItemBuilder(Material.STONE, 64 * 20).asItemStack(), 20),
                Arguments.of(new ItemBuilder(Material.CAKE, 1).asItemStack(), 1),
                Arguments.of(new ItemBuilder(Material.CAKE, 64).asItemStack(), 1));
    }

    @ParameterizedTest
    @MethodSource("validateLeftover_forPutItemsIntoInventory")
    public void validateLeftover_forPutItemsIntoInventory(
            ItemStack item, ItemStack expectedLeftover, PlayerMock playerMock) {
        Optional<ItemStack> leftover = ItemCalculatorUtil.putItemsIntoInventory(playerMock, item);

        Assertions.assertEquals(expectedLeftover != null, leftover.isPresent());
        if (expectedLeftover != null) {
            Assertions.assertEquals(expectedLeftover, leftover.get());
        }
    }

    private static Stream<Arguments> validateLeftover_forPutItemsIntoInventory() {
        return Stream.of(
                Arguments.of(ONE_STONE, null, server.addPlayer()),
                Arguments.of(ONE_INVENTORY_OF_STONE, null, server.addPlayer()),
                Arguments.of(ONE_INVENTORY_OF_STONE_PLUS_ONE_EXTRA, ONE_STONE, server.addPlayer()),
                Arguments.of(ONE_STONE, ONE_STONE, PlayerMockData.createPlayerWithFullInventory(server)));
    }

    @ParameterizedTest
    @MethodSource("validateInventory_forPutItemsIntoInventory")
    public void validateInventory_forPutItemsIntoInventory(
            ItemStack item, ItemStack[] expectedContents, PlayerMock playerMock) {
        ItemCalculatorUtil.putItemsIntoInventory(playerMock, item);
        ItemStack[] contents = playerMock.getInventory().getContents();
        Assertions.assertArrayEquals(expectedContents, contents);
    }

    private static Stream<Arguments> validateInventory_forPutItemsIntoInventory() {
        return Stream.of(
                Arguments.of(ONE_STONE, InventoryMockData.ONE_STONE_INVENTORY, server.addPlayer()),
                Arguments.of(ONE_INVENTORY_OF_STONE, InventoryMockData.FULL_STONE_INVENTORY, server.addPlayer()),
                Arguments.of(
                        ONE_INVENTORY_OF_STONE_PLUS_ONE_EXTRA,
                        InventoryMockData.FULL_STONE_INVENTORY,
                        server.addPlayer()),
                Arguments.of(
                        ItemStackMockData.ONE_MINECART, InventoryMockData.ONE_MINECART_INVENTORY, server.addPlayer())
                // mock tests do not seem to appear to allow stacking but spigot does.
                //        Arguments.of(ItemStackMockData.ONE_INVENTORY_OF_MINECART,
                // InventoryMockData.FULL_MINECART_INVENTORY, server.addPlayer())
                );
    }
}

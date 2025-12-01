package com.hazebyte.crate.cratereloaded.util.item;

import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.SIMPLE_BYPASS;

import com.hazebyte.crate.BukkitTest;
import com.hazebyte.crate.api.util.ItemBuilder;
import com.hazebyte.crate.cratereloaded.error.ValidationException;
import java.util.List;
import java.util.stream.Stream;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ItemParserTest extends BukkitTest {

    @BeforeEach
    public void setup() {
        SIMPLE_BYPASS = true;
    }

    @ParameterizedTest
    @MethodSource("getItemStringAndExpectedItemStack")
    public void parse_returnsCorrectItem(String itemString, ItemStack itemStack) {
        ItemStack parsedItem = ItemParser.parse(itemString);
        Assertions.assertTrue(itemStack.equals(parsedItem));
    }

    static Stream<Arguments> getItemStringAndExpectedItemStack() {
        return Stream.of(
                Arguments.of(
                        "chest 1", new ItemBuilder(Material.CHEST).amount(1).asItemStack()),
                Arguments.of(
                        "chest{old_nbt} 1",
                        new ItemBuilder(Material.CHEST).amount(1).asItemStack()),
                Arguments.of(
                        "chest 1 name:Name",
                        new ItemBuilder(Material.CHEST)
                                .amount(1)
                                .displayName("Name")
                                .asItemStack()),
                Arguments.of(
                        "chest 1 lore:Lore",
                        new ItemBuilder(Material.CHEST).amount(1).lore("Lore").asItemStack()),
                Arguments.of(
                        "chest 1 custommodeldata:1",
                        new ItemBuilder(Material.CHEST)
                                .amount(1)
                                .setCustomModelData(1)
                                .asItemStack()));
    }

    @ParameterizedTest
    @MethodSource("getInvalidString")
    public void parse_throwsValidationException(String invalidString) {
        Assertions.assertThrows(ValidationException.class, () -> ItemParser.parse(invalidString));
    }

    static Stream<Arguments> getInvalidString() {
        return Stream.of(
                Arguments.of("chest"), // Missing amount
                Arguments.of("chest abc"), // Amount is string
                Arguments.of("nil 1") // Invalid material
                );
    }

    @Test
    public void parse_returnsEmptyLine() {
        String itemString = "chest 1 lore:|Lore";
        ItemStack parsedItem = ItemParser.parse(itemString);

        Assertions.assertTrue(parsedItem.hasItemMeta());

        List<String> lore = parsedItem.getItemMeta().getLore();
        Assertions.assertEquals(2, lore.size());
        Assertions.assertEquals("", lore.get(0));
        Assertions.assertEquals("Lore", lore.get(1));
    }
}

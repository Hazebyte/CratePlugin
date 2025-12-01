package com.hazebyte.crate.test;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.hazebyte.crate.api.util.ItemBuilder;
import org.bukkit.Material;

public class PlayerMockData {

    public static PlayerMock createPlayerWithFullInventory(ServerMock serverMock) {
        PlayerMock playerMock = serverMock.addPlayer();

        for (int i = 0; i < playerMock.getInventory().getSize(); i++) {
            playerMock.getInventory().setItem(i, new ItemBuilder(Material.DIAMOND).asItemStack());
        }
        return playerMock;
    }
}

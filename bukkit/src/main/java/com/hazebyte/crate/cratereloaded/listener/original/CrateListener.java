package com.hazebyte.crate.cratereloaded.listener.original;

import com.hazebyte.crate.api.ServerVersion;
import com.hazebyte.crate.api.crate.BlockCrateRegistrar;
import com.hazebyte.crate.api.crate.Crate;
import com.hazebyte.crate.api.crate.CrateAction;
import com.hazebyte.crate.api.crate.CrateRegistrar;
import com.hazebyte.crate.cratereloaded.CorePlugin;
import com.hazebyte.crate.cratereloaded.util.PlayerUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class CrateListener implements Listener {

    protected CrateRegistrar chandler = CorePlugin.getPlugin().getCrateRegistrar();
    protected BlockCrateRegistrar bhandler = CorePlugin.getPlugin().getBlockCrateRegistrar();

    private final Map<UUID, Long> throttle = new HashMap<>();

    /**
     * Performs a common check between different types of crates. This includes checking if the player
     * has been throttled, actions on pressure plates, and off hand actions.
     */
    public boolean check(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        // The player is stepping on pressure plate, etc. Ignore the event.
        if (event.getAction() == Action.PHYSICAL) {
            return false;
        }

        // 1.8 to 1.9 has some changes where it introduces off handing.
        // 1.9 and further needs to skip the check if it is not directly from the hand.
        return !CorePlugin.getPlugin().getServerVersion().gte(ServerVersion.v1_9_R1)
                || PlayerUtil.getHand(event) == EquipmentSlot.HAND;
    }

    public static CrateAction toCrateAction(Crate crate, Action action) {
        switch (action) {
            case LEFT_CLICK_AIR:
            case LEFT_CLICK_BLOCK: {
                switch (crate.getType()) {
                    case MYSTERY:
                        return CrateAction.OPEN;
                    case KEY:
                        return CrateAction.PREVIEW;
                }
            }
            case RIGHT_CLICK_AIR:
            case RIGHT_CLICK_BLOCK: {
                return CrateAction.OPEN;
            }
            default:
                return null;
        }
    }
}

package com.hazebyte.crate.cratereloaded.claim;

import com.hazebyte.crate.api.CrateAPI;
import com.hazebyte.crate.api.claim.Claim;
import com.hazebyte.crate.api.util.Messenger;
import com.hazebyte.crate.cratereloaded.util.format.CustomFormat;
import java.util.Collection;
import java.util.function.Consumer;
import org.bukkit.entity.Player;

/**
 * This consumer tells the player how many claims they have left. If the player has no claim, this
 * consumer is a no-op.
 */
public class ClaimMessageConsumer implements Consumer<Collection<Claim>> {

    private final String CLAIM_HOLD_MESSAGE_KEY = "core.claim_hold";

    private Player player;

    public ClaimMessageConsumer(Player player) {
        this.player = player;
    }

    @Override
    public void accept(Collection<Claim> claims) {
        if (claims.size() <= 0) {
            return;
        }

        String format = CrateAPI.getMessage(CLAIM_HOLD_MESSAGE_KEY);
        String message = CustomFormat.format(format, player, claims.size());

        Messenger.tell(player, message);
    }
}

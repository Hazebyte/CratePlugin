package com.hazebyte.crate.cratereloaded.serialization;

import com.hazebyte.crate.api.crate.Crate;
import com.hazebyte.crate.api.crate.reward.Reward;
import com.hazebyte.crate.cratereloaded.util.item.ItemParser;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CrateSerialization {

    public CrateSerialization() {}

    public Map<String, Object> serialize(Crate crate) {
        Map<String, Object> serializedMap = new LinkedHashMap<>();
        serializedMap.put("type", crate.getType().name());
        serializedMap.put("item", ItemParser.serialize(crate.getItem()));
        serializedMap.put("animation", crate.getAnimationType().name());
        serializedMap.put("end-animation", crate.getEndAnimationType().name());
        serializedMap.put("display-name", crate.getDisplayName().replace("§", "&"));
        serializedMap.put("holographic", crate.getHolographicText());

        Map<String, Object> preview = new LinkedHashMap<>();
        preview.put("enabled", crate.isPreviewable());
        preview.put("rows", crate.getPreviewRows());
        serializedMap.put("preview", preview);

        Map<String, Object> buy = new LinkedHashMap<>();
        buy.put("enabled", crate.isBuyable());
        buy.put("cost", crate.getCost());
        serializedMap.put("buy", buy);

        Map<String, Object> confirm = new LinkedHashMap<>();
        confirm.put("enabled", crate.hasConfirmationToggle());
        confirm.put("accept-button", ItemParser.serialize(crate.getAcceptButton()));
        confirm.put("decline-button", ItemParser.serialize(crate.getDeclineButton()));
        serializedMap.put("confirmation", confirm);

        Map<String, Object> messages = new LinkedHashMap<>();
        messages.put("open", crate.getOpenMessage());
        messages.put("broadcast", crate.getBroadcastMessage());
        serializedMap.put("message", messages);

        // TODO: Serialize Effects

        Map<String, Object> rewards = new LinkedHashMap<>();
        rewards.put("minimum-rewards", crate.getMinimumRewards());
        rewards.put("maximum-rewards", crate.getMaximumRewards());
        List<String> rewardAsStrings = new LinkedList<>();
        for (Reward reward : crate.getRewards()) {
            rewardAsStrings.add(reward.serialize());
        }
        rewards.put("rewards", rewardAsStrings);
        serializedMap.put("reward", rewards);

        return serializedMap;
    }
}

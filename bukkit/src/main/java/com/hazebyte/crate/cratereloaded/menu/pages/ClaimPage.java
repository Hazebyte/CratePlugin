package com.hazebyte.crate.cratereloaded.menu.pages;

import com.hazebyte.crate.api.claim.Claim;
import com.hazebyte.crate.cratereloaded.CorePlugin;
import com.hazebyte.crate.cratereloaded.component.PluginSettingComponent;
import com.hazebyte.crate.cratereloaded.menu.Size;
import com.hazebyte.crate.cratereloaded.menu.buttons.ClaimButton;
import java.util.Collection;
import java.util.Objects;

public class ClaimPage extends PaginationPage {

    private final Collection<Claim> claim;

    public ClaimPage(Collection<Claim> claim, Size size, PluginSettingComponent settings) {
        super(CorePlugin.getPlugin(), settings.getClaimMenuName(), size, settings);
        this.claim = claim;
        this.addItems();
    }

    @Override
    public void addItems() {
        claim.forEach(current -> {
            if (Objects.isNull(current)
                    || Objects.isNull(current.getRewards())
                    || current.getRewards().size() == 0) {
                return;
            }
            ClaimButton button = new ClaimButton(current, settings);
            addToQueue(button);
        });
        super.addItems();
    }
}

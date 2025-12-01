package com.hazebyte.crate.cratereloaded.menu.pages;

import com.hazebyte.crate.api.crate.Crate;
import com.hazebyte.crate.cratereloaded.CorePlugin;
import com.hazebyte.crate.cratereloaded.component.PluginSettingComponent;
import com.hazebyte.crate.cratereloaded.menu.Button;
import com.hazebyte.crate.cratereloaded.menu.Menu;
import com.hazebyte.crate.cratereloaded.menu.Size;
import com.hazebyte.crate.cratereloaded.menu.buttons.CloseMenuButton;
import com.hazebyte.crate.cratereloaded.menu.buttons.OpenCrateButton;
import com.hazebyte.crate.cratereloaded.util.InventoryConstants;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class ConfirmationPage extends Menu implements Itemable {

    private final Crate crate;
    private final Location location;
    private final ItemStack item;
    private final boolean requiresCheck;
    private final PluginSettingComponent settings;

    public ConfirmationPage(Crate crate, Location location, ItemStack item, boolean requiresCheck, PluginSettingComponent settings) {
        super(CorePlugin.getPlugin(), settings.getConfirmationMenuName(), Size.FIVE_LINE);
        this.crate = crate;
        this.location = location;
        this.item = item;
        this.requiresCheck = requiresCheck;
        this.settings = settings;
        addItems();
    }

    @Override
    public void addItems() {
        OpenCrateButton acceptButton = new OpenCrateButton(crate.getAcceptButton());
        acceptButton.setAttributes(crate, location, item, requiresCheck);
        CloseMenuButton closeButton = new CloseMenuButton(crate.getDeclineButton());

        setItem(InventoryConstants.CENTER_SLOT_THREE_ROWS, new Button(crate.getDisplayItem()));
        setItem(InventoryConstants.SLOT_29, acceptButton);
        setItem(InventoryConstants.SLOT_33, closeButton);
    }
}

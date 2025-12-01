package com.hazebyte.crate.cratereloaded.menu.pages;

import com.hazebyte.crate.cratereloaded.CorePlugin;
import com.hazebyte.crate.cratereloaded.component.PluginSettingComponent;
import com.hazebyte.crate.cratereloaded.menu.Button;
import com.hazebyte.crate.cratereloaded.menu.Grid;
import com.hazebyte.crate.cratereloaded.menu.Menu;
import com.hazebyte.crate.cratereloaded.menu.Size;
import com.hazebyte.crate.cratereloaded.menu.buttons.CloseMenuButton;
import com.hazebyte.crate.cratereloaded.menu.buttons.PageButton;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.plugin.java.JavaPlugin;

public class PaginationPage extends Menu implements Itemable {

    private final int index;
    private final List<Button> queue;
    protected final PluginSettingComponent settings;
    protected boolean hasNavButtons;

    public PaginationPage(JavaPlugin plugin, String name, Size size, PluginSettingComponent settings) {
        super(plugin, name, size);
        this.settings = settings;
        queue = new ArrayList<>();
        index = 0;
        this.hasNavButtons = settings.isMenuInteractionEnabled();
    }

    private PaginationPage(PaginationPage page, int index, List<Button> queue) {
        super(page.plugin, page.name, page.size);
        this.settings = page.settings;
        this.index = index;
        this.queue = queue;
        this.hasNavButtons = settings.isMenuInteractionEnabled();
    }

    public void addToQueue(Button button) {
        queue.add(button);
    }

    public void addToQueue(List<Button> buttons) {
        queue.addAll(buttons);
    }

    public List<Button> getQueue() {
        return queue;
    }

    @Override
    public void addItems() {
        int MAX = this.size.getSize();
        if (this.hasNavButtons) {
            MAX = MAX - Grid.ROWS;
            addButtons();
        }

        for (int slot = 0; slot < MAX && this.index + slot < queue.size(); slot++) {
            setItem(slot, queue.get(this.index + slot));
        }
    }

    public void addButtons() {
        final int SIZE = this.size.getSize();
        final int NEXT = SIZE - 4, CLOSE = SIZE - 5, PREVIOUS = SIZE - 6;

        this.setItem(
                CLOSE, new CloseMenuButton(settings.getPreviewCloseButton()));

        if (super.hasParent()) {
            this.setItem(
                    PREVIOUS,
                    new PageButton(
                            plugin, settings.getPreviewBackButton(), super.getParent()));
        }
        if (super.hasChild()) {
            this.setItem(
                    PREVIOUS,
                    new PageButton(
                            plugin, settings.getPreviewBackButton(), super.getParent()));
        }

        if (this.hasChild()) {
            int index = this.index + SIZE - Grid.ROWS;
            PaginationPage page = new PaginationPage(this, index, queue);
            PageButton button =
                    new PageButton(plugin, settings.getPreviewNextButton(), page);
            this.setItem(NEXT, button);

            page.setParent(this);
            page.addItems();
        }
    }

    @Override
    public boolean hasChild() {
        return (index + this.size.getSize() - Grid.ROWS) < queue.size();
    }
}

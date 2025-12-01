package com.hazebyte.crate.cratereloaded.crate.animation.scroller;

import com.hazebyte.crate.api.crate.Crate;
import com.hazebyte.crate.api.util.ItemBuilder;
import com.hazebyte.crate.cratereloaded.component.PluginSettingComponent;
import com.hazebyte.crate.cratereloaded.menu.Size;
import com.hazebyte.util.Mat;
import java.util.ArrayList;
import java.util.Arrays;

public class Heart extends Rectangle {

    public Heart(Crate crate, PluginSettingComponent settings) {
        super(crate, settings);
    }

    public Heart(Crate crate, int length, PluginSettingComponent settings) {
        super(crate, length, settings);
    }

    @Override
    public void setDefault() {
        super.setDefault();
        this.size = Size.SIX_LINE;
        this.spinner = ItemBuilder.of(Mat.GREEN_STAINED_GLASS_PANE.toItemStack())
                .displayName(" ")
                .asItemStack();
        numberOfPrizes = 14;

        defineScrollPath();
        //        Messenger.debug(String.format("%s A: %d, B: %d, C: %d, D: %d, Displays: %d",
        // crate.getCrateName(), minA, minB, minC, minD, numberOfPrizes));
    }

    @Override
    protected void defineScrollPath() {
        if (this.scrollPath == null) {
            this.scrollPath = new ArrayList<>();
        } else {
            this.scrollPath.clear();
        }

        Integer[] numberPath = {13, 5, 6, 16, 25, 33, 41, 49, 39, 29, 19, 10, 2, 3};
        this.scrollPath.addAll(Arrays.asList(numberPath));
    }
}

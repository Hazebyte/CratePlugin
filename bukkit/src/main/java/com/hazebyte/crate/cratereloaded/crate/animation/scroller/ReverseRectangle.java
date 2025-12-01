package com.hazebyte.crate.cratereloaded.crate.animation.scroller;

import com.hazebyte.crate.api.crate.Crate;
import com.hazebyte.crate.cratereloaded.component.PluginSettingComponent;
import java.util.ArrayList;
import java.util.Arrays;

public class ReverseRectangle extends Rectangle {

    public ReverseRectangle(Crate crate, PluginSettingComponent settings) {
        super(crate, settings);
    }

    public ReverseRectangle(Crate crate, int length, PluginSettingComponent settings) {
        super(crate, length, settings);
    }

    @Override
    protected void defineScrollPath() {
        if (this.scrollPath == null) {
            this.scrollPath = new ArrayList<>();
        } else {
            this.scrollPath.clear();
        }

        Integer[] numberPath = {13, 12, 11, 10, 19, 28, 37, 38, 39, 40, 41, 42, 43, 34, 25, 16, 15, 14};
        this.scrollPath.addAll(Arrays.asList(numberPath));
    }
}

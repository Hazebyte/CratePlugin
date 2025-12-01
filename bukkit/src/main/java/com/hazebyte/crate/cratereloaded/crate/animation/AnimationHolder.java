/*
 * This file is part of AmpMenus.
 *
 * Copyright (c) 2014 <http://github.com/ampayne2/AmpMenus/>
 *
 * AmpMenus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AmpMenus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with AmpMenus.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.hazebyte.crate.cratereloaded.crate.animation;

import com.hazebyte.crate.api.crate.reward.Reward;
import com.hazebyte.crate.cratereloaded.menu.BaseHolder;
import com.hazebyte.crate.cratereloaded.menu.Menu;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/** Allows you to set the {@link Menu} that created the Inventory as the Inventory's holder. */
public class AnimationHolder extends BaseHolder {
    private final Player player;
    private final Animation menu;
    private final List<Reward> rewards;
    private final List<Reward> constant;

    public AnimationHolder(Animation menu, Player player, List<Reward> rewards, List<Reward> constant) {
        super(Bukkit.createInventory(player, 9));
        this.menu = menu;
        this.player = player;
        this.rewards = rewards;
        this.constant = constant;
    }

    /**
     * Gets the {@link Animation} holding the Inventory.
     *
     * @return The {@link Animation} holding the Inventory.
     */
    public Animation getMenu() {
        return menu;
    }

    public List<Reward> getRewards() {
        return rewards;
    }

    public List<Reward> getConstantRewards() {
        return constant;
    }
}

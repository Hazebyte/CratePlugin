package com.hazebyte.crate.cratereloaded.provider.effect.nil;

import com.hazebyte.crate.cratereloaded.provider.effect.EffectWrapper;
import java.util.Optional;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class NilWrapper implements EffectWrapper {
    @Override
    public void start() {}

    @Override
    public void stop() {}

    @Override
    public void setLocation(Location location) {}

    @Override
    public Optional<Location> getLocation() {
        return Optional.empty();
    }

    @Override
    public Optional getEffect() {
        return Optional.empty();
    }

    @Override
    public void setTargetPlayer(Player player) {}

    @Override
    public Player getTargetPlayer() {
        return null;
    }

    @Override
    public boolean isPersistent() {
        return false;
    }
}

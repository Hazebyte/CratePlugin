package com.hazebyte.crate.cratereloaded.provider.effect;

import java.util.Optional;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * A wrapper that represents an instance of an effect.
 *
 * @param <T> The built-in instance of an effect.
 */
public interface EffectWrapper<T> {

    void start();

    void stop();

    void setLocation(Location location);

    Optional<Location> getLocation();

    Optional<T> getEffect();

    void setTargetPlayer(Player player);

    Player getTargetPlayer();

    boolean isPersistent();
}

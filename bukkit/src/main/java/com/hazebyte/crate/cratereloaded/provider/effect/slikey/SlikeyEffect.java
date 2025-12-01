package com.hazebyte.crate.cratereloaded.provider.effect.slikey;

import com.hazebyte.crate.cratereloaded.provider.effect.EffectWrapper;
import de.slikey.effectlib.Effect;
import de.slikey.effectlib.EffectType;
import java.util.Objects;
import java.util.Optional;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * A SlikeyEffect represents one unit of effect. e.g. There is an instance of SlikeyEffect for every
 * open and persistent effect that is run. In other words, everytime a crate is opened, a new
 * SlikeyEffect is created.
 */
public class SlikeyEffect implements EffectWrapper<Effect> {

    private Effect effect;

    public SlikeyEffect(Effect effect) {
        Objects.requireNonNull(effect);
        this.effect = effect;
    }

    @Override
    public void start() {
        effect.start();
    }

    @Override
    public void stop() {
        effect.cancel();
    }

    @Override
    public void setLocation(Location location) {
        effect.setLocation(location);
    }

    @Override
    public Optional<Location> getLocation() {
        return Optional.of(effect.getLocation());
    }

    @Override
    public Optional<Effect> getEffect() {
        return Optional.of(effect);
    }

    @Override
    public void setTargetPlayer(Player player) {
        effect.setTargetEntity(player);
    }

    @Override
    public Player getTargetPlayer() {
        return effect.getTargetPlayer();
    }

    @Override
    public boolean isPersistent() {
        return effect.type == EffectType.REPEATING && effect.iterations == -1;
    }
}

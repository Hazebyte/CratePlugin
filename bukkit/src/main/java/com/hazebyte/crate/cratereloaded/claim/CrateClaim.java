package com.hazebyte.crate.cratereloaded.claim;

import com.hazebyte.crate.api.claim.Claim;
import com.hazebyte.crate.api.crate.reward.Reward;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.OfflinePlayer;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class CrateClaim implements Claim, Cloneable {

    @NonNull
    @Builder.Default
    @Getter
    protected UUID id = UUID.randomUUID();

    @NonNull
    protected List<Reward> rewards;

    @NonNull
    protected OfflinePlayer owner;

    @NonNull
    @Builder.Default
    protected Long timestamp = System.currentTimeMillis();

    @NonNull
    @Builder.Default
    protected Function<Claim, Boolean> executor = claim -> true;

    @Override
    public boolean execute() {
        return this.executor.apply(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrateClaim that = (CrateClaim) o;
        return timestamp.equals(that.timestamp) && Objects.equals(id, that.id) && Objects.equals(owner, that.owner);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, owner, timestamp);
        return result;
    }

    /**
     * Returns a shallow copy of the CrateClaim. The returned CrateClaim will have a different memory
     * address than the original CrateClaim.
     *
     * <p>If claim1 and claim2 have the same uuid, timestamp, and uuid but claim2 was created via
     * clone. `claim1 != claim2` but `claim1.equals(claim2)`
     *
     * @return a shallow copy of the CrateClaim.
     */
    @Override
    protected CrateClaim clone() {
        return this.toBuilder().build();
    }
}

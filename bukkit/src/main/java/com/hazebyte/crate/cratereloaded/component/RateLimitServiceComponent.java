package com.hazebyte.crate.cratereloaded.component;

import java.util.UUID;

public interface RateLimitServiceComponent {

    boolean isRateLimited(UUID id);

    void request(UUID id);

    long getMillisTilRateIsLifted(UUID id);
}

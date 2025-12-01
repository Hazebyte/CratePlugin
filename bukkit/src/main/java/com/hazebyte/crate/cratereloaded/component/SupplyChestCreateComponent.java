package com.hazebyte.crate.cratereloaded.component;

import com.hazebyte.crate.cratereloaded.component.model.CrateOpenRequest;
import com.hazebyte.crate.cratereloaded.component.model.CrateOpenResponse;

public interface SupplyChestCreateComponent {

    CrateOpenResponse createChest(CrateOpenRequest crateOpenRequest);
}

package com.hazebyte.crate.cratereloaded.component;

import com.hazebyte.crate.api.crate.Crate;
import com.hazebyte.crate.api.effect.Category;
import com.hazebyte.crate.cratereloaded.model.CrateImpl;
import com.hazebyte.crate.cratereloaded.model.CrateV2;
import com.hazebyte.crate.cratereloaded.provider.effect.EffectWrapper;
import java.util.List;

/**
 * Component responsible for resolving effect configurations into executable effect wrappers.
 * This component bridges the gap between crate data (effect configuration) and effect execution
 * (EffectWrapper instances).
 *
 * <p>Effect configuration is stored as Map&lt;Category, List&lt;String&gt;&gt; in both CrateV2 and
 * CrateImpl, where the List&lt;String&gt; contains effect keys that map to ConfigurationSections
 * registered with EffectServiceComponent.
 */
public interface EffectResolverComponent {

    /**
     * Retrieves effect wrappers for a crate category.
     * This method handles both CrateV2 and CrateImpl instances.
     *
     * @param crate The crate (CrateV2 or CrateImpl)
     * @param category The effect category to retrieve
     * @return List of effect wrappers ready for execution
     */
    List<EffectWrapper> getEffects(Crate crate, Category category);

    /**
     * Retrieves effects specifically for CrateV2 (preferred).
     * Uses the crate's effectCategoryToId map to resolve effect configurations.
     *
     * @param crate The CrateV2 instance
     * @param category The effect category to retrieve
     * @return List of effect wrappers ready for execution
     */
    List<EffectWrapper> getEffectsV2(CrateV2 crate, Category category);

    /**
     * Retrieves effects for CrateImpl (legacy support).
     * Delegates to CrateImpl's existing getEffects() method for backward compatibility.
     *
     * @param crate The CrateImpl instance
     * @param category The effect category to retrieve
     * @return List of effect wrappers ready for execution
     */
    List<EffectWrapper> getEffectsLegacy(CrateImpl crate, Category category);
}

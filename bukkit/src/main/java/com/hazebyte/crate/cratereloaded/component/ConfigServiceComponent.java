package com.hazebyte.crate.cratereloaded.component;

import com.hazebyte.crate.cratereloaded.model.Config;
import java.io.File;
import java.util.Collection;

public interface ConfigServiceComponent {

    Config addConfig(File file);

    Config getConfigWithName(String fileName);

    Collection<Config> getConfigsStartingWith(String searchIndex);
}

package com.hazebyte.crate.cratereloaded.parser;

import com.hazebyte.crate.api.crate.Crate;
import com.hazebyte.crate.cratereloaded.model.Storage;
import java.util.List;

public interface CrateParser<T extends Storage> {

    List<Crate> parse(T storage);
}

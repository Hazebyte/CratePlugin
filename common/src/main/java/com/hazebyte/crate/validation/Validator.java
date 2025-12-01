package com.hazebyte.crate.validation;

import com.hazebyte.crate.Error;

public interface Validator<T> {

    Error validate(T serialized);
}

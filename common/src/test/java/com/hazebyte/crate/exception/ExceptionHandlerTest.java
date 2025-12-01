package com.hazebyte.crate.exception;

import com.hazebyte.TestLogger;
import com.hazebyte.crate.exception.items.InvalidInputException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ExceptionHandlerTest {

    @Test
    public void handledErrorIsOnlyLogged() {
        ExceptionHandler handler = new ExceptionHandler(TestLogger.getLogger());
        ExceptionHandler mockExceptionHandler = Mockito.spy(handler);

        mockExceptionHandler.handle(new InvalidInputException("handled"));
    }
}

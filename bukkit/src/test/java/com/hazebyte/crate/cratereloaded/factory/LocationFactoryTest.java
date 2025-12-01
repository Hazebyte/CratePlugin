package com.hazebyte.crate.cratereloaded.factory;

import com.hazebyte.crate.BukkitTest;
import com.hazebyte.crate.Error;
import com.hazebyte.crate.cratereloaded.util.LocationFactory;
import com.hazebyte.crate.exception.items.ValidationException;
import java.util.List;
import java.util.stream.Stream;
import org.bukkit.Location;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

public class LocationFactoryTest extends BukkitTest {

    @ParameterizedTest
    @MethodSource("provideArgsForValidInput")
    public void succeedsOnValidInput(String serialized) throws ValidationException {
        Location location = LocationFactory.createLocation(serialized);
        Assertions.assertNotNull(location);
    }

    @ParameterizedTest
    @MethodSource("provideArgsForInvalidInput")
    @NullSource
    public void failsOnInvalidInput(String serialized) {
        try {
            LocationFactory.createLocation(serialized);
            Assertions.fail("Expected ValidationException to be thrown");
        } catch (ValidationException ex) {
            List<Error> errors = ex.getErrors();
            Assertions.assertTrue(errors.size() >= 1, "Expected at least one validation error");
        }
    }

    private static Stream<Arguments> provideArgsForValidInput() {
        return Stream.of(
                Arguments.of("world=testWorld,x=1,y=1,z=1"),
                Arguments.of("world=123,x=1.5,y=2.5,z=2.5"),
                Arguments.of("world=123,x=1.5,y=2.5,z=2.5,crate=123"));
    }

    private static Stream<Arguments> provideArgsForInvalidInput() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("world=,x=,y=,z="),
                Arguments.of("world=,x=,y=,z=,crate="),
                Arguments.of("world="),
                Arguments.of(",,,"));
    }
}

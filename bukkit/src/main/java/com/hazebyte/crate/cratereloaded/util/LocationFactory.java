package com.hazebyte.crate.cratereloaded.util;

import com.hazebyte.crate.Error;
import com.hazebyte.crate.exception.items.ValidationException;
import com.hazebyte.crate.validation.Validator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationFactory {

    private static final String DELIMETER = ",";

    private static class FormatValidation implements Validator<String> {

        @Override
        public Error validate(String serialized) {
            if (serialized == null || serialized.isEmpty()) {
                return Error.NULL_OR_EMPTY_ERROR.apply("location");
            }
            return null;
        }
    }

    private static class ValueValidation implements Validator<String> {

        @Override
        public Error validate(String serialized) {
            String[] parts = serialized.split(DELIMETER, -1);
            for (String part : parts) {
                if (part.isEmpty()) {
                    return Error.FORMAT_ERROR.apply(serialized);
                }
                String[] pair = part.split("=", -1);
                if (pair.length != 2) {
                    return Error.FORMAT_ERROR.apply(serialized);
                }
                String key = pair[0];
                String value = pair[1];
                if (value == null || value.isEmpty()) {
                    return Error.NULL_OR_EMPTY_ERROR.apply(String.format("location.%s", key));
                }
            }
            return null;
        }
    }

    private static List<Validator<String>> validatorList = Arrays.asList(new FormatValidation(), new ValueValidation());

    public static List<Error> validate(String serialized) {
        List<Error> errors = new ArrayList<>();
        for (Validator<String> validator : validatorList) {
            Error error = validator.validate(serialized);
            if (error != null) {
                errors.add(error);
                break; // we return a list but the list of errors heres are prerequisites to each other.
            }
        }
        return errors;
    }

    public static Location createLocation(String serialized) throws ValidationException {
        List<Error> errors = validate(serialized);
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        String worldName = "";
        double x = 0, y = 0, z = 0;

        for (String part : serialized.split(DELIMETER)) {
            String[] pair = part.split("=");

            String attribute = pair[0];
            String value = ((pair.length > 1) ? pair[1] : "");

            switch (attribute) {
                case "world":
                    worldName = value;
                    break;
                case "x":
                    x = Double.parseDouble(value);
                    break;
                case "y":
                    y = Double.parseDouble(value);
                    break;
                case "z":
                    z = Double.parseDouble(value);
                    break;
            }
        }

        return new Location(Bukkit.getWorld(worldName), x, y, z);
    }
}

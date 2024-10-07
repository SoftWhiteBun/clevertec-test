package ru.clevertec.exception;

import java.util.UUID;

public class CakeNotFoundException extends RuntimeException {
    private CakeNotFoundException(String message) {
        super(message);
    }

    public static CakeNotFoundException byCakeId(UUID cakeId) {
        return new CakeNotFoundException(
                String.format("Cake not found by id %s", cakeId)
        );
    }
}
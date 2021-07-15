package pl.shalpuk.scooterService.util;

import java.util.UUID;

public class IdGenerator {

    public static UUID generateId() {
        return UUID.randomUUID();
    }
}

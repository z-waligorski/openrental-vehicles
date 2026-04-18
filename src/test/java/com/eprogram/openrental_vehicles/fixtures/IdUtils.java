package com.eprogram.openrental_vehicles.fixtures;

import java.util.UUID;

public class IdUtils {

    public static final String ID_STRING_A = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa";
    public static final String ID_STRING_B = "bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb";

    public static UUID getUUID(String idString) {
        return UUID.fromString(idString);
    }
}

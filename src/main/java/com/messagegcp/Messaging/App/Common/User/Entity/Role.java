package com.messagegcp.Messaging.App.Common.User.Entity;

import java.util.Arrays;
import java.util.Optional;

public enum Role {
    ADMIN,
    USER;

    public static boolean contains(String role) {
        for (Role r : Role.values()) {
            if (r.name().equals(role)) {
                return true;
            }
        }
        return false;
    }
    
    public static Optional<Role> fromString(String role) {
        return Arrays.stream(Role.values())
                     .filter(r -> r.name().equalsIgnoreCase(role))
                     .findFirst();
    }
}

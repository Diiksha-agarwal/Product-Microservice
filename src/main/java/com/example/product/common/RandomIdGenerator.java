package com.example.product.common;

import java.util.UUID;

public class RandomIdGenerator {
    public static String generateRandomId() {
        return UUID.randomUUID().toString();
    }
}

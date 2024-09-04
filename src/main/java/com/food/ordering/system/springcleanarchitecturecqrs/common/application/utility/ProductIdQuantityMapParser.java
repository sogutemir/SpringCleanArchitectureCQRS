package com.food.ordering.system.springcleanarchitecturecqrs.common.application.utility;


import java.util.Map;
import java.util.stream.Collectors;

public class ProductIdQuantityMapParser {

    public static Map<Long, Integer> parse(Map<String, String> productIdQuantityMap) {
        return productIdQuantityMap.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> Long.parseLong(e.getKey().replaceAll("[^0-9]", "")),
                        e -> Integer.parseInt(e.getValue())
                ));
    }
}
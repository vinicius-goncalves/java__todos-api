package me.vg.todo_crud.utils;

import jakarta.servlet.http.HttpServletRequest;

import java.util.*;
import java.util.stream.Collectors;

public class Utils {

    public static <T> boolean isBoolean(T target) {
        if(target instanceof String s) {
            return s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false");
        }

        return target instanceof Boolean;
    }

    public static Set<Map.Entry<String, String>> getRequestParams(HttpServletRequest request) {
        List<String> keysToExclude = new ArrayList<>(Arrays.asList("sortBy", "orderBy"));

        return request.getParameterMap().entrySet().stream()
                .filter(e -> !keysToExclude.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue()[0]))
                .entrySet();

    }
}

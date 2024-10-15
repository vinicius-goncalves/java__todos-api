package me.vg.todo_crud.utils;

import org.springframework.data.domain.Sort;

public class Utils {

    public static <T> boolean isBoolean(T target) {
        if(target instanceof String s) {
            return s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false");
        }

        return target instanceof Boolean;
    }

    public static Sort createSort(String sortBy, String orderBy) {
        Sort sort = Sort.by(sortBy.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, orderBy);
        return sort;
    }
}

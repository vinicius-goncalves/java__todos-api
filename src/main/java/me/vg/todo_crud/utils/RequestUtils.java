package me.vg.todo_crud.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestUtils {

    public static Map<String, String> getRequestParams(HttpServletRequest request) {
        List<String> keysToExclude = new ArrayList<>(Arrays.asList("sortBy", "orderBy"));

        return request.getParameterMap().entrySet().stream()
                .filter(e -> !keysToExclude.contains(e.getKey()))
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()[0]));
    }

    public static List<Criteria> createCriteriaListFromParamsRequest(Map<String, String> paramsRequest) {
        List<Criteria> criteriaList = new ArrayList<>();

        for(Map.Entry<String, String> paramsEntry : paramsRequest.entrySet()) {
            String key = paramsEntry.getKey(), value = paramsEntry.getValue();

            if(Utils.isBoolean(value)) {
                criteriaList.add(Criteria.where(key).is(Boolean.parseBoolean(value)));
                continue;
            }

            criteriaList.add(Criteria.where(key).regex(value, "i"));
        }

        return criteriaList;
    }

    public static Query createMongoQueryUsingCriteria(List<Criteria> criteriaList) {
        return new Query().addCriteria(new Criteria().andOperator(criteriaList));
    }

    public static Query createMongoQueryUsingCriteria(List<Criteria> criteriaList, Sort sort) {
        return new Query().with(sort).addCriteria(new Criteria().andOperator(criteriaList));
    }
}

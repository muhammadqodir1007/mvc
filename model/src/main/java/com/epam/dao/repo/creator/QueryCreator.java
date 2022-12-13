package com.epam.dao.repo.creator;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

import static com.epam.dao.repo.creator.FilterParameters.*;

@Component
public class QueryCreator {

    public String createUpdateQuery(Map<String, String> fields, String tableName) {
        StringJoiner joiner = new StringJoiner(",");
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            if (entry.getValue() != null && !entry.getKey().equals("id")) {
                joiner.add(entry.getKey() + "=" + '\'' + entry.getValue() + '\'');
            }
        }
        String query = "UPDATE " + tableName + " SET " + joiner + " WHERE id=" + fields.get("id");
        return query;
    }

    public String createGetQuery(Map<String, String> fields, String tableName) {
        StringBuilder query = new StringBuilder("SELECT * FROM " + tableName);
        if (Objects.equals(tableName, "gift_certificates")) {
            query.append(" gc LEFT JOIN gift_certificates_tags gct ON gc.id=gct.gift_certificate_id LEFT JOIN tags t ON gct.tag_id=t.id");
        }
        if (fields.get(TAG_NAME) != null) {

            addParameter(query, TAG_NAME, fields.get(TAG_NAME));
        }
        if (fields.get(NAME) != null) {
            addParameter(query, NAME, fields.get(NAME));
        }
        if (fields.get(PART_OF_TAG_NAME) != null) {
            addPartParameter(query, TAG_NAME, fields.get(PART_OF_TAG_NAME));
        }
        if (fields.get(PART_OF_NAME) != null) {
            addPartParameter(query, NAME, fields.get(PART_OF_NAME));
        }
        if (fields.get(PART_OF_DESCRIPTION) != null) {
            addPartParameter(query, DESCRIPTION, fields.get(PART_OF_DESCRIPTION));
        }
        if (fields.get(SORT_BY_TAG_NAME) != null) {
            addSortParameter(query, TAG_NAME, fields.get(SORT_BY_TAG_NAME));
        }
        if (fields.get(SORT_BY_NAME) != null) {
            addSortParameter(query, NAME, fields.get(SORT_BY_NAME));
        }
        if (fields.get(SORT_BY_CREATE_DATE) != null) {
            addSortParameter(query, CREATE_DATE, fields.get(SORT_BY_CREATE_DATE));
        }

        return query.toString();
    }

    private void addParameter(StringBuilder query, String partParameter, String value) {
        if (query.toString().contains("WHERE")) {
            query.append(" AND ");
        } else {
            query.append(" WHERE ");
        }
        query.append(partParameter).append("='").append(value).append('\'');
    }

    private void addPartParameter(StringBuilder query, String partParameter, String value) {
        if (query.toString().contains("WHERE")) {
            query.append(" AND ");
        } else {
            query.append(" WHERE ");
        }
        query.append(partParameter).append(" LIKE '%").append(value).append("%'");
    }

    private void addSortParameter(StringBuilder query, String sortParameter, String value) {
        if (query.toString().contains("ORDER BY")) {
            query.append(", ");
        } else {
            query.append(" ORDER BY ");
        }
        query.append(sortParameter).append(" ").append(value);
    }
}

package com.epam.dao.repo.creator;

import org.springframework.stereotype.Component;

import java.util.HashMap;
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
        return "UPDATE " + tableName + " SET " + joiner + " WHERE id=" + fields.get("id");
    }

    public String createGetQuery(Map<String, String> fields, String tableName) {
        StringBuilder builder = new StringBuilder("SELECT * FROM " + tableName);
        if (Objects.equals(tableName, "gift_certificates")) {
            builder.append(" gc LEFT JOIN gift_certificates_tags gct ON gc.id=gct.gift_certificate_id LEFT JOIN tags t ON gct.tag_id=t.id");
        }
        Map<String, QueryMaker> map = new HashMap<>();
        map.put(tag_name.toString(), (value) -> builder.append(" WHERE ").append(tag_name).append("='").append(value).append('\''));
        map.put(name.toString(), value -> builder.append(" WHERE ").append(name).append("='").append(value).append('\''));
        map.put(partOfTagName.toString(), value -> builder.append(" WHERE ").append(tag_name).append(" LIKE '%").append(value).append("%'"));
        map.put(partOfName.toString(), value -> builder.append(" WHERE ").append(name).append(" LIKE '%").append(value).append("%'"));
        map.put(partOfDescription.toString(), value -> builder.append(" WHERE ").append(description).append(" LIKE '%").append(value).append("%'"));
        map.put(sortByCreateDate.toString(), value -> builder.append(" ORDER BY ").append(create_date).append(" ").append(value));
        map.put(sortByName.toString(), value -> builder.append(" ORDER BY ").append(name).append(" ").append(value));
        Map.Entry<String, String> next = fields.entrySet().iterator().next();
        String key = next.getKey();
        String value = next.getValue();
        map.get(key).addParameter(value);
        return builder.toString();

    }
}

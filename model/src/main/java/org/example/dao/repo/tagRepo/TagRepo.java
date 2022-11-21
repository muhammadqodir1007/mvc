package org.example.dao.repo.tagRepo;

import org.example.entity.Tag;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TagRepo {

    private final JdbcTemplate jdbcTemplate;
    private final TagRowMapper tagRowMapper;

    public TagRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        tagRowMapper = new TagRowMapper();
    }


    public List<Tag> getAll() {

        List<Tag> tagList = jdbcTemplate.query(TagQueries.getAll, new ResultSetExtractor<List<Tag>>() {
            @Override
            public List<Tag> extractData(ResultSet rs) throws SQLException, DataAccessException {

                List<Tag> list = new ArrayList<>();
                while (rs.next()) {

                    Tag tag = new Tag();
                    tag.setId(rs.getInt("id"));
                    tag.setName(rs.getString("tag_name"));
                    list.add(tag);
                }
                return list;
            }
        });

        return tagList;
    }

    public int insert(Tag tag) {
        return jdbcTemplate.update(TagQueries.insert, tag.getName());

    }

    public Tag getById(int id) {
        List<Tag> tags = jdbcTemplate.query(TagQueries.getById, tagRowMapper, id);
        return tags.get(0);

    }

    public int delete(int id) {

        return jdbcTemplate.update(TagQueries.delete, id);


    }

    public Tag getByName(String name) {

        List<Tag> tags = jdbcTemplate.query(TagQueries.getByName, new Object[]{name}, tagRowMapper);
        return tags.get(0);



    }

    public boolean existByName(String name) {

        Integer integer = jdbcTemplate.queryForObject(TagQueries.existByName, new Object[]{name}, Integer.class);

        return integer > 0;

    }


}

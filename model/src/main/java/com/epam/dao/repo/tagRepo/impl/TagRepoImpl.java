package com.epam.dao.repo.tagRepo.impl;

import com.epam.dao.repo.tagRepo.TagQueries;
import com.epam.dao.repo.tagRepo.TagRepo;
import com.epam.dao.repo.tagRepo.TagRowMapper;
import com.epam.entity.Tag;
import com.epam.exceptions.DaoException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.exceptions.ExceptionDaoMessageCodes.*;

@Repository
public class TagRepoImpl implements TagRepo {


    protected final ResultSetExtractor<List<Tag>> rowMapper;

    private final JdbcTemplate jdbcTemplate;
    private final TagRowMapper tagRowMapper;

    public TagRepoImpl(ResultSetExtractor<List<Tag>> rowMapper, JdbcTemplate jdbcTemplate) {
        this.rowMapper = rowMapper;
        this.jdbcTemplate = jdbcTemplate;
        tagRowMapper = new TagRowMapper();
    }

    @Override
    public List<Tag> getAll() throws DaoException {

        try {
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
        } catch (DataAccessException d) {
            throw new DaoException(NO_ENTITY);
        }
    }

    @Override
    public int insert(Tag tag) throws DaoException {
        try {
            return jdbcTemplate.update(TagQueries.insert, tag.getName());
        } catch (DataAccessException d) {
            throw new DaoException(SAVING_ERROR);
        }

    }

    @Override
    public Tag getById(int id) throws DaoException {
        try {
            List<Tag> tags = jdbcTemplate.query(TagQueries.getById, tagRowMapper, id);
            return tags.get(0);
        } catch (DataAccessException d) {
            throw new DaoException(NO_ENTITY_WITH_ID);
        }

    }

    @Override
    public int delete(int id) throws DaoException {
        try {
            Integer exist = jdbcTemplate.queryForObject(TagQueries.existInGcT, Integer.class, id);
            if (exist != 0) {
                jdbcTemplate.update(TagQueries.deleteFromGct, id);
                return jdbcTemplate.update(TagQueries.delete, id);
            }
            return jdbcTemplate.update(TagQueries.delete, id);
        } catch (DataAccessException d) {
            throw new DaoException(NO_ENTITY_WITH_ID);
        }


    }

    @Override
    public boolean existByName(String name) {

        Integer integer = jdbcTemplate.queryForObject(TagQueries.existByName, Integer.class, name);

        return integer > 0;


    }

    protected List<Tag> executeQuery(String query, Object... params) {
        return jdbcTemplate.query(query, rowMapper, params);
    }

    @Override
    public List<Tag> getByName(String name) throws DaoException {
        try {
            return executeQuery(TagQueries.getByName, name);
        } catch (DataAccessException e) {
            throw new DaoException(NO_ENTITY_WITH_NAME);
        }
    }
}

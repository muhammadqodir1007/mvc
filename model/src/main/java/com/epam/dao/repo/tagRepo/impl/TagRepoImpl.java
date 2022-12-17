package com.epam.dao.repo.tagRepo.impl;

import com.epam.dao.repo.tagRepo.TagRepo;
import com.epam.dao.repo.tagRepo.TagRowMapper;
import com.epam.entity.Tag;
import com.epam.exceptions.DaoException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.epam.dao.repo.tagRepo.TagQueries.*;

@Repository
@AllArgsConstructor
public class TagRepoImpl implements TagRepo {

    private final JdbcTemplate jdbcTemplate;
    private final TagRowMapper tagRowMapper;


    @Override
    public List<Tag> getAll() throws DaoException {
        try {
            return jdbcTemplate.query(GET_ALL, tagRowMapper);
        } catch (DataAccessException d) {
            throw new DaoException(d.getMessage());
        }
    }

    @Override
    public void insert(Tag tag) throws DaoException {
        try {
            jdbcTemplate.update(INSERT, tag.getName());
        } catch (DataAccessException d) {
            throw new DaoException(d.getMessage());
        }

    }

    @Override
    public Tag getById(int id) throws DaoException {
        try {
            return jdbcTemplate.query(GET_BY_ID, ps -> ps.setInt(1, id), rs -> {
                Tag tag = new Tag();
                while (rs.next()) {
                    tag = new Tag(rs.getInt(1), rs.getString(2));
                }
                return tag;
            });
        } catch (DataAccessException d) {
            throw new DaoException(d.getMessage());
        }

    }

    /**
     * @param id checked exist or not from gift_certificates_tags ,if exist remove the relation ,
     *           after that tag removed from tags table
     */

    @Override
    public void delete(int id) throws DaoException {
        try {
            Integer exist = jdbcTemplate.queryForObject(EXIST_IN_GCT, Integer.class, id);
            assert exist != null;
            if (exist != 0) {
                jdbcTemplate.update(DELETE_FROM_GCT, id);
                jdbcTemplate.update(DELETE, id);
            }
            jdbcTemplate.update(DELETE, id);
        } catch (DataAccessException d) {
            throw new DaoException(d.getMessage());
        }


    }

    /**
     * @return boolean , after checking exist or not
     */

    @Override
    public boolean existByName(String name) throws DaoException {
        try {
            Integer integer = jdbcTemplate.queryForObject(EXIST_BY_NAME, Integer.class, name);
            assert integer != null;
            return integer > 0;
        } catch (DataAccessException d) {
            throw new DaoException(d.getMessage());
        }
    }


    @Override
    public Tag getByName(String name) throws DaoException {
        try {
            return jdbcTemplate.query(GET_BY_NAME, ps -> ps.setString(1, name), rs -> {
                Tag tag = new Tag();
                while (rs.next()) {
                    tag = new Tag(rs.getInt(1), rs.getString(2));
                }
                return tag;
            });
        } catch (DataAccessException d) {
            throw new DaoException(d.getMessage());
        }

    }
}

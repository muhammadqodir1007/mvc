package com.epam.dao.repo.giftRepo.impl;

import com.epam.dao.config.ForDate;
import com.epam.dao.repo.creator.QueryCreator;
import com.epam.dao.repo.giftRepo.GiftQueries;
import com.epam.dao.repo.giftRepo.GiftRepo;
import com.epam.dao.repo.tagRepo.TagRowMapper;
import com.epam.dao.repo.giftRepo.GiftCertificateFieldExtractor;
import com.epam.dao.repo.giftRepo.GiftCertificateRowMapper;
import com.epam.dao.repo.tagRepo.impl.TagRepoImpl;
import com.epam.entity.GiftCertificate;
import com.epam.entity.Tag;
import com.epam.exceptions.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.epam.exceptions.ExceptionDaoMessageCodes.*;

@Repository
public class GiftRepoImpl implements GiftRepo {

    private static final String TABLE_NAME = "gift_certificates";
    protected final JdbcTemplate jdbcTemplate;

    private final GiftCertificateRowMapper giftCertificateRowMapper;
    private final TagRowMapper tagRowMapper;
    private final QueryCreator queryCreator;
    private final TagRepoImpl tagRepo;
    private final ResultSetExtractor<List<GiftCertificate>> rowMapper;
    private final GiftCertificateFieldExtractor giftCertificateFieldExtractor;

    @Autowired
    public GiftRepoImpl(JdbcTemplate jdbcTemplate, GiftCertificateRowMapper giftCertificateRowMapper, TagRowMapper tagRowMapper, QueryCreator queryCreator, TagRepoImpl tagRepo, ResultSetExtractor<List<GiftCertificate>> rowMapper, GiftCertificateFieldExtractor giftCertificateFieldExtractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.giftCertificateRowMapper = giftCertificateRowMapper;
        this.tagRowMapper = tagRowMapper;
        this.queryCreator = queryCreator;
        this.tagRepo = tagRepo;
        this.rowMapper = rowMapper;
        this.giftCertificateFieldExtractor = giftCertificateFieldExtractor;
    }

    private static final String ADD_TAGS_QUERY = "INSERT INTO gift_certificates_tags (gift_certificate_id,tag_id) VALUES(?,?)";

    @Override
    public List<GiftCertificate> list() throws DaoException {

        try {
            return jdbcTemplate.query(GiftQueries.getAll, giftCertificateRowMapper);
        } catch (DataAccessException dataAccessException) {
            throw new DaoException(NO_ENTITY);
        }

    }

    @Override
    public void addGift(GiftCertificate gift) throws DaoException {
        try {
            int update = jdbcTemplate.update(GiftQueries.insert, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1, gift.getName());
                    ps.setString(2, gift.getDescription());
                    ps.setDouble(3, gift.getPrice());
                    ps.setInt(4, gift.getDuration());
                    ps.setDate(5, ForDate.getDate());
                    ps.setDate(6, ForDate.getDate());
                    boolean execute = ps.execute();
                }
            });
        } catch (DataAccessException dataAccessException) {
            throw new DaoException(SAVING_ERROR);
        }
    }

    @Override
    public int delete(int id) throws DaoException {
        try {
            Integer i = jdbcTemplate.queryForObject(GiftQueries.existInGct, Integer.class, id);
            System.out.println(i);
            if (i != 0) {
                jdbcTemplate.update(GiftQueries.deleteFromGct, id);
                return jdbcTemplate.update(GiftQueries.delete, id);
            } else {
                return jdbcTemplate.update(GiftQueries.delete, id);
            }

        } catch (DataAccessException d) {
            throw new DaoException(NO_ENTITY_WITH_ID);
        }

    }

    @Override
    public GiftCertificate getById(int id) throws DaoException {
        try {
            return jdbcTemplate.queryForObject(GiftQueries.getGiftById, new BeanPropertyRowMapper<>(GiftCertificate.class), id);
        } catch (DataAccessException d) {

            throw new DaoException(NO_ENTITY_WITH_ID);
        }
    }

    @Override
    public List<Tag> getAssociatedTags(int id) throws DaoException {

        try {
            return jdbcTemplate.query(GiftQueries.getAssociatedTags, tagRowMapper, id);
        } catch (DataAccessException d) {
            throw new DaoException(NO_ENTITY);
        }
    }

    @Override
    public void addAssociatedTags(int giftId, int tagId) throws DaoException {
        try {
            int update = jdbcTemplate.update(GiftQueries.addAssociatedTags, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1, giftId);
                    ps.setInt(2, tagId);
                }
            });
        } catch (DataAccessException d) {
            throw new DaoException(SAVING_ERROR);
        }
    }

    @Override
    public void deleteTagsAssociation(long certificateId, List<Tag> tags) throws DaoException {
        try {
            List<Long> tagIds = getTagsIds(tags);
            tagIds.forEach(tagId -> {
                executeUpdateQuery(GiftQueries.removeTagsAssociation, certificateId, tagId);
            });
        } catch (DataAccessException e) {
            throw new DaoException(NO_ENTITY_WITH_ID);
        }
    }

    @Override
    public List<GiftCertificate> getWithFilters(Map<String, String> fields) throws DaoException {
        try {
            String query = queryCreator.createGetQuery(fields, TABLE_NAME);
            return jdbcTemplate.query(query, rowMapper);
        } catch (DataAccessException e) {
            throw new DaoException(NO_ENTITY_WITH_PARAMETERS);
        }
    }

    @Override
    @Transactional
    public void update(int id, GiftCertificate item) throws DaoException {
        try {
            Map<String, String> fields = giftCertificateFieldExtractor.extract(item);
            String updateQuery = queryCreator.createUpdateQuery(fields, TABLE_NAME);
            executeUpdateQuery(updateQuery);
            updateTags(item, item.getId());
        } catch (DataAccessException e) {
            throw new DaoException(NO_ENTITY_WITH_ID);
        }
    }

    private void executeUpdateQuery(String query, Object... params) {
        jdbcTemplate.update(query, params);
    }

    private void updateTags(GiftCertificate item, long giftCertificateId) {
        if (item.getTags() == null) {
            return;
        }
        List<Long> tagIds = getTagsIds(item.getTags());
        for (Long id : tagIds) {
            executeUpdateQuery(ADD_TAGS_QUERY, giftCertificateId, id);
        }
    }

    private List<Long> getTagsIds(List<Tag> tags) {
        List<Long> tagIds = new ArrayList<>();
        tags.forEach(tag -> {
            String tagName = tag.getName();
            Tag tagWithId = null;
            try {
                tagWithId = tagRepo.getByName(tagName).get(0);
            } catch (DaoException e) {
                e.printStackTrace();
            }

            assert tagWithId != null;
            tagIds.add((long) tagWithId.getId());
        });
        return tagIds;

    }


}




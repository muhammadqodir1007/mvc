package org.example.dao.repo.giftRepo;

import org.example.dao.config.ForDate;

import org.example.dao.repo.tagRepo.TagRowMapper;
import org.example.entity.GiftCertificate;
import org.example.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class GiftRepo {
    protected final JdbcTemplate jdbcTemplate;

    protected final GiftCertificateRowMapper giftCertificateRowMapper;


    protected final TagRowMapper tagRowMapper;

    @Autowired
    public GiftRepo(JdbcTemplate jdbcTemplate,GiftCertificateRowMapper giftCertificateRowMapper ,TagRowMapper tagRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.giftCertificateRowMapper = giftCertificateRowMapper;
        this.tagRowMapper = tagRowMapper;
    }




    public List<GiftCertificate> list() {

        List<GiftCertificate> list = jdbcTemplate.query(GiftQueries.getAll, giftCertificateRowMapper);
        return list;

    }


    public void addGift(GiftCertificate gift) {
        jdbcTemplate.update(GiftQueries.insert, new PreparedStatementSetter() {
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


    }

    public int update(int id, GiftCertificate giftCertificate) {


        return jdbcTemplate.update(GiftQueries.update, giftCertificate.getName(), giftCertificate.getDescription(), giftCertificate.getPrice(), ForDate.getDate(), id);


    }

    public int delete(int id) {

        return jdbcTemplate.update(GiftQueries.delete, id);

    }

    public GiftCertificate getById(int id) {
        GiftCertificate giftCertificate = jdbcTemplate.queryForObject(GiftQueries.getGiftById, new Object[]{id}, new BeanPropertyRowMapper<>(GiftCertificate.class));


        return giftCertificate;


    }

    public List<Tag> getAssociatedTags(int id) {

        List<Tag> tags = jdbcTemplate.query(GiftQueries.getAssociatedQuery, new Object[]{id}, tagRowMapper);
        return tags;
    }

    public void addAssociatedTags(int giftId, int tagId) {
        jdbcTemplate.update(GiftQueries.addAssociatedQueries, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, giftId);
                ps.setInt(2, tagId);
            }
        });

    }

    public int deleteAssociated(int giftId, int tagId) {

        return jdbcTemplate.update(GiftQueries.deleteTagsAssociated, giftId, tagId);

    }

    public boolean existById(int id) {

        Integer integer = jdbcTemplate.queryForObject(GiftQueries.existById, new Object[]{id}, Integer.class);
        return integer > 0;


    }


}

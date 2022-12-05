package org.example.dao.repo.giftRepo;

import org.example.entity.GiftCertificate;
import org.example.entity.Tag;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Component
public class GiftCertificateRowMapper implements ResultSetExtractor<List<GiftCertificate>> {


    @Override
    public List<GiftCertificate> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        rs.next();
        while (!rs.isAfterLast()) {
            GiftCertificate giftCertificate = new GiftCertificate();
            giftCertificate.setId(rs.getInt("id"));
            giftCertificate.setName(rs.getString("name"));
            giftCertificate.setDescription(rs.getString("description"));
            giftCertificate.setPrice(rs.getInt("price"));
            giftCertificate.setDuration(rs.getInt("duration"));
            giftCertificate.setCreateDate(rs.getDate("create_date").toString());
            giftCertificate.setLastUpdateDate(rs.getDate("last_update_date").toString());

            List<Tag> tags = new ArrayList<>();
            while (!rs.isAfterLast() && rs.getInt("id") == giftCertificate.getId()) {
                int tagId = rs.getInt("tag_id");
                String tagName = rs.getString("tag_name");
                tags.add(new Tag(tagId, tagName));
                rs.next();
            }
            giftCertificate.setTags(tags);
            giftCertificates.add(giftCertificate);
        }
        return giftCertificates;
    }
}

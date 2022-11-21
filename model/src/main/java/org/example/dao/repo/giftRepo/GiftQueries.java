package org.example.dao.repo.giftRepo;

public interface GiftQueries {

    String getAll="select * from gift_certificates gc " +
            "left join gift_certificates_tags gct on gc.id=gct.gift_certificate_id " +
            "left join tags t on t.id = gct.tag_id";
    String insert="INSERT INTO public.\"gift_certificates\" (name, description, price, duration, create_date, last_update_date) VALUES ( ?,?,?,?,?,?)";
    String update="update public.\"gift_certificates\" set name=? ,description=?, price=? ,last_update_date=?  where id=? ";

    String delete="delete from public.\"gift_certificates\" where id=?";
    String deleteTagsAssociated = "DELETE FROM gift_certificates_tags WHERE gift_certificate_id=? AND tag_id=?";
    String existById = "select count (*) from public.\"gift_certificates\"  where id=?";
    String getGiftById = "select * from public.\"gift_certificates\"  where id=?";

    String getById="select * from gift_certificates gc " +
            "left join gift_certificates_tags gct on gc.id=gct.gift_certificate_id " +
            "left join tags t on t.id = gct.tag_id where gc.id=?";

     String addAssociatedQueries = "INSERT INTO gift_certificates_tags (gift_certificate_id, tag_id) VALUES(?, ?)";
   String getAssociatedQuery = "SELECT * FROM tags t INNER JOIN gift_certificates_tags gct ON " +
            "t.id = gct.tag_id WHERE gct.gift_certificate_id=?";




}

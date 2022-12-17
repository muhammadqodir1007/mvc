package com.epam.dao.repo.giftRepo;

public class GiftQueries {

    public static final String GET_ALL = "select * from gift_certificates gc " +
            "left join gift_certificates_tags gct on gc.id=gct.gift_certificate_id " +
            "left join tags t on t.id = gct.tag_id";
    public static final String INSERT = "INSERT INTO public.\"gift_certificates\" (name, description, price, duration, create_date, last_update_date) VALUES ( ?,?,?,?,?,?)";

    public static final String DELETE = "delete from public.\"gift_certificates\" where id=?";
    public static final String GET_GIFT_BY_ID = "select * from public.\"gift_certificates\"  where id=?";

    public static final String ADD_ASSOCIATED_TAGS = "INSERT INTO gift_certificates_tags (gift_certificate_id, tag_id) VALUES(?, ?)";
    public static final String GET_ASSOCIATED_TAGS = "SELECT * FROM tags t INNER JOIN gift_certificates_tags gct ON " +
            "t.id = gct.tag_id WHERE gct.gift_certificate_id=?";

    public static final String REMOVE_TAGS_ASSOCIATION = "DELETE FROM gift_certificates_tags WHERE gift_certificate_id=? AND tag_id=?";


    public static final String DELETE_FROM_GCT = "delete from gift_certificates_tags g where  g.gift_certificate_id=?";

    public static final String EXIST_IN_GCT = "select count(*) from gift_certificates_tags gct where gct.gift_certificate_id=?";


}

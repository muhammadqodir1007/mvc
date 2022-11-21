package org.example.service;

import org.example.dao.repo.tagRepo.TagRepo;
import org.example.entity.Tag;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {


    final TagRepo tagRepo;

    public TagService(TagRepo tagRepo) {
        this.tagRepo = tagRepo;
    }


    public List<Tag> getAll() throws ClassNotFoundException {

        try {
            return tagRepo.getAll();

        } catch (Exception e) {
            throw new ClassNotFoundException();
        }
    }

    public int insert(Tag tag) {
        try {
            return tagRepo.insert(tag);
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public Tag getOne(int id) throws FieldNotFoundException {
        try {
            return tagRepo.getById(id);
        } catch (Exception e) {
            throw new FieldNotFoundException("tag not found");
        }
    }
    public int delete(int i) throws FieldNotFoundException {
        try{
          return   tagRepo.delete(i);

        }catch (Exception e){
            throw new FieldNotFoundException("tag not found with this id");
        }





    }


//
//    private static final String ADD_TAGS_ASSOCIATION_QUERY = "INSERT INTO gift_certificates_tags (gift_certificate_id, tag_id) VALUES(?, ?)";
//    private static final String GET_ASSOCIATED_TAGS_QUERY = "SELECT * FROM tags t INNER JOIN gift_certificates_tags gct ON t.id = gct.tag_id WHERE gct.gift_certificate_id=?";
//    private static final String REMOVE_TAGS_ASSOCIATION_QUERY = "DELETE FROM gift_certificates_tags WHERE gift_certificate_id=? AND tag_id=?";
//    private static final String ADD_TAGS_QUERY = "INSERT INTO gift_certificates_tags (gift_certificate_id,tag_id) VALUES(?,?)";
//
//
//    GiftService giftService = new GiftService();
//
//
//   JdbcTemplate jdbcTemplate=new JdbcTemplate();
//    PostgreSqlConfig config = new PostgreSqlConfig();
//
//
//
//
//    public TagService() {
//    }
//
//
//    public List<Tag> getAll() {
//        DataSource dataSource = config.postgresqlDataSource();
//        jdbcTemplate.setDataSource(dataSource);
//        List<Tag> list = jdbcTemplate.query("select * from public.\"tags\"", new ResultSetExtractor<List<Tag>>() {
//            @Override
//            public List<Tag> extractData(ResultSet rs) throws SQLException, DataAccessException {
//
//
//                List<Tag> tags = new ArrayList<>();
//
//                while (rs.next()) {
//                    Tag tag = new Tag();
//                    tag.setId(rs.getInt("id"));
//                    tag.setName(rs.getString("tag_name"));
//                    tags.add(tag);
//                }
//                return tags;
//            }
//        });
//        return list;
//    }
//
//    public Tag findById(int id) {
//        DataSource dataSource = config.postgresqlDataSource();
//        jdbcTemplate.setDataSource(dataSource);
//        Tag tag = jdbcTemplate.queryForObject("select * from public.\"tags\"  where id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Tag.class));
//        return tag;
//
//    }
//
////    public Tag findByName(String name) {
////        DataSource dataSource = config.postgresqlDataSource();
////        jdbcTemplate.setDataSource(dataSource);
////        Tag tag = jdbcTemplate.queryForObject("select * from public.\"tags\"  where tag_name=?", new Object[]{name}, new TagRowMapper());
////        return tag;
////
////    }
//
////    public boolean existByName(String name) {
////        DataSource dataSource = config.postgresqlDataSource();
////        jdbcTemplate.setDataSource(dataSource);
////        Integer integer = jdbcTemplate.queryForObject("select count (*) from public.\"tags\"  where tag_name=?", new Object[]{name}, Integer.class);
////
////        return integer > 0;
////
////
////    }
//
//
//    public int delete(int id) {
//        DataSource dataSource = config.postgresqlDataSource();
//        jdbcTemplate.setDataSource(dataSource);
//        return jdbcTemplate.update("delete from public.\"tags\"  where id=?", id);
//
//    }
//
//    public int update(int id, String Tag) {
//        DataSource dataSource = config.postgresqlDataSource();
//        jdbcTemplate.setDataSource(dataSource);
//        int update = jdbcTemplate.update("update public.\"tags\" set tag_name=? where id=? ", Tag, id);
//        return update;
//
//    }
//
//    public void insert(Tag tag) {
//        DataSource dataSource = config.postgresqlDataSource();
//        jdbcTemplate.setDataSource(dataSource);
//        TagService tagService = new TagService();
//        boolean exist = tagService.existByName(tag.getName());
//        if (exist) return;
//
//        jdbcTemplate.update("INSERT INTO public.tags (\n" +
//                "tag_name) VALUES (?)", tag.getName());
//
//    }
//
//
//    public boolean addAssociatedTags(int gift_id, String tag) {
//        DataSource dataSource = config.postgresqlDataSource();
//        jdbcTemplate.setDataSource(dataSource);
//        boolean exist = giftService.existById(gift_id);
//        if (!exist) return false;
//        TagService tagService = new TagService();
//        boolean b = tagService.existByName(tag);
//        int update = 0;
//        if (b) {
//
//            Tag byName = tagService.findByName(tag);
//            update = jdbcTemplate.update("INSERT INTO public.gift_certificates_tags (\n" +
//                    "gift_certificate_id,tag_id) VALUES (?,?)", gift_id, byName.getId());
//
//        } else {
//            tagService.insert(new Tag(tag));
//            Tag tag1 = tagService.findByName(tag);
//            update = jdbcTemplate.update("INSERT INTO public.gift_certificates_tags (\n" +
//                    "gift_certificate_id,tag_id) VALUES (?,?)", gift_id, tag1.getId());
//        }
//        return update != 0;
//    }
//
//
//    public List<Tag> getAssociatedTags() {
//
//
//        DataSource dataSource = config.postgresqlDataSource();
//        jdbcTemplate.setDataSource(dataSource);
//        List<Tag> associatedTags = jdbcTemplate.query("select t.id ,\n" +
//                "t.tag_name from tags t inner join gift_certificates_tags g on t.id=g.tag_id", new ResultSetExtractor<List<Tag>>() {
//            @Override
//            public List<Tag> extractData(ResultSet rs) throws SQLException, DataAccessException {
//                List<Tag> tags = new ArrayList<>();
//                while (rs.next()) {
//                    Tag tag = new Tag(rs.getInt("id"), rs.getString("tag_name"));
//                    tags.add(tag);
//                }
//
//                return tags;
//            }
//        });
//        return associatedTags;
//
//
//    }
//
//    private boolean existById(int id) {
//        DataSource dataSource = config.postgresqlDataSource();
//        jdbcTemplate.setDataSource(dataSource);
//        Integer integer = jdbcTemplate.queryForObject("select count (*) from public.\"gift_certificates_tags\"  where tag_id=?", new Object[]{id}, Integer.class);
//
//        return integer > 0;
//    }
//
//
//    public boolean deleteAssociatedTags(int id) {
//        DataSource dataSource = config.postgresqlDataSource();
//        jdbcTemplate.setDataSource(dataSource);
//        TagService tagService = new TagService();
//        boolean exist = tagService.existById(id);
//        if (!exist) return false;
//
//        int update = jdbcTemplate.update("delete from gift_certificates_tags where tag_id=?", id);
//        jdbcTemplate.update("delete from tags where id=?", id);
//
//
//        return update != 0;
//
//    }
//
//
//    public static void main(String[] args) {
//
//        TagService tag = new TagService();
//        System.out.println(tag.existById(7));
//
//
//    }


}

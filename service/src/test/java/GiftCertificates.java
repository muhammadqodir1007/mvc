import com.epam.entity.GiftCertificate;
import com.epam.entity.Tag;

import java.sql.Date;
import java.util.Collections;
import java.util.List;

class GiftCertificates {


    static final Tag tag2 = new Tag(1, "tag2");
    static final GiftCertificate giftCertificate1 = new GiftCertificate(1, "name1", "description1", 56.9, 23, new Date(1669126392015L).toString(), new Date(1669126392015L).toString(), Collections.singletonList(new Tag(0, null)));
    static final GiftCertificate giftCertificate2 = new GiftCertificate(2, "name2", "description2", 75, 33, new Date(1669126392015L).toString(), new Date(1669126392015L).toString(), List.of(tag2));
    static final GiftCertificate giftCertificate3 = new GiftCertificate(3, "name3", "description3", 56, 67, new Date(1669126392015L).toString(), new Date(1669126392015L).toString(), Collections.singletonList(new Tag(0, null)));
    static final GiftCertificate giftCertificate4 = new GiftCertificate(6, "name4", "description4", 75, 33, new Date(1669126392015L).toString(), new Date(1669126392015L).toString(), Collections.singletonList(new Tag(0, null)));

}

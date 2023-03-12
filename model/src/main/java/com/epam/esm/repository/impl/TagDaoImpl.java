package com.epam.esm.repository.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.GiftCertificateDao;
import com.epam.esm.repository.TagDao;
import com.epam.esm.repository.pagination.PaginationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class TagDaoImpl extends PaginationDao<Tag> implements TagDao {
    @PersistenceContext
    EntityManager entityManager;
    final
    GiftCertificateDao giftDao;

    @Autowired
    public TagDaoImpl(GiftCertificateDao giftDao) {
        super(Tag.class);
        this.giftDao = giftDao;
    }

    private static final String SELECT_TOP_USED_TAG_WITH_HIGHEST_COST_OF_ORDERS = "select t from GiftCertificate g " +
            "join g.tags t " +
            "where g.id in (select g2.id from Order o join o.giftCertificates g2 " +
            "where o.user.id = :userId) group by t.id order by count(t.id) desc";

    @Override
    public Optional<Tag> findById(long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return entityManager.createQuery("select t from Tag t where t.name = :name", Tag.class).setParameter("name", name)
                .getResultList().stream().findFirst();
    }

    @Override
    @Transactional
    public Tag insert(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    @Override
    @Transactional
    public boolean remove(Tag tag) {
        deleteRemovedTag(tag.getId());
        entityManager.remove(tag);
        return entityManager.find(Tag.class, tag.getId()) == null;
    }

    @Override
    public void deleteRemovedTag(long id) {
        entityManager.createNativeQuery("DELETE FROM gift_certificates_tags WHERE tag_id=:tagId")
                .setParameter("tagId", id).executeUpdate();
    }

    @Override
    public Optional<Tag> findTopUsedWithHighestCostOfOrder(long userId) {
        return entityManager.createQuery(SELECT_TOP_USED_TAG_WITH_HIGHEST_COST_OF_ORDERS, Tag.class)
                .setParameter("userId", userId).getResultList().stream().findFirst();

    }
}

package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;

import java.util.Optional;

public interface GiftCertificateDao extends BasicDao<GiftCertificate> {
    GiftCertificate update(GiftCertificate giftCertificate);

    void deleteFromTag(long id);

    void deleteFromOrder(long id);

    Optional<GiftCertificate> findByName(String name);

}

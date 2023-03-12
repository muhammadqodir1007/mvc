package com.epam.esm.repository;

import com.epam.esm.entity.Tag;

import java.util.Optional;

public interface TagDao extends BasicDao<Tag> {
    Optional<Tag> findByName(String name);

    void deleteRemovedTag(long id);

    Optional<Tag> findTopUsedWithHighestCostOfOrder(long userId);
}


package com.golovkin.chat.data.dao;

import com.golovkin.chat.data.utils.EntityManagerProvider;

// +
abstract class AbstractDao<T> {
    public void persist(T object) {
        var entityManager = EntityManagerProvider.createEntityManager();
        var transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.persist(object);
        transaction.commit();

        entityManager.close();
    }

    public void update(T object) {
        var entityManager = EntityManagerProvider.createEntityManager();
        var transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.merge(object);
        transaction.commit();

        entityManager.close();
    }

    public void delete(T object) {
        var entityManager = EntityManagerProvider.createEntityManager();
        var transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.remove(object);
        transaction.commit();

        entityManager.close();
    }
}

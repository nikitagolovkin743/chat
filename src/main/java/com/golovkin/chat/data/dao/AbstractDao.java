package com.golovkin.chat.data.dao;

import com.golovkin.chat.data.utils.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

// +
abstract class AbstractDao<T> {
    public void persist(T object) {
        EntityManager entityManager = EntityManagerProvider.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.persist(object);
        transaction.commit();

        entityManager.close();
    }

    public void update(T object) {
        EntityManager entityManager = EntityManagerProvider.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.merge(object);
        transaction.commit();

        entityManager.close();
    }

    public void delete(T object) {
        EntityManager entityManager = EntityManagerProvider.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.remove(object);
        transaction.commit();

        entityManager.close();
    }
}

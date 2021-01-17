package com.golovkin.chat.data.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerProvider {
    private final static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ChatPU");

    private EntityManagerProvider() {
    }

    public static EntityManager createEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}

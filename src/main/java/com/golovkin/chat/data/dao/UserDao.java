package com.golovkin.chat.data.dao;

import com.golovkin.chat.data.entities.User;
import com.golovkin.chat.data.utils.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

// +
public class UserDao extends AbstractDao<User> {
    public static final UserDao instance = new UserDao();

    @SuppressWarnings("unchecked")
    public static List<User> findAllUsersExceptId(int id) {
        EntityManager entityManager = EntityManagerProvider.createEntityManager();

        Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.id <> :id");
        query.setParameter("id", id);

        List<User> foundUsers = (List<User>) query.getResultList();

        entityManager.close();

        return foundUsers;
    }

    @SuppressWarnings("unchecked")
    public User findById(int id) {
        EntityManager entityManager = EntityManagerProvider.createEntityManager();

        Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.id = :id");
        query.setParameter("id", id);

        List<User> foundUsers = (List<User>) query.getResultList();

        entityManager.close();

        return foundUsers.size() == 0 ? null : foundUsers.get(0);
    }

    @SuppressWarnings("unchecked")
    public User findByName(String username) {
        EntityManager entityManager = EntityManagerProvider.createEntityManager();

        Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.name = :name");
        query.setParameter("name", username);

        List<User> foundUsers = (List<User>) query.getResultList();

        entityManager.close();

        return foundUsers.size() == 0 ? null : foundUsers.get(0);
    }

    @SuppressWarnings("unchecked")
    public User findByCredentials(String username, String password) {
        EntityManager entityManager = EntityManagerProvider.createEntityManager();

        Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.name = :name AND u.password = :password");
        query.setParameter("name", username);
        query.setParameter("password", password);

        List<User> foundUsers = (List<User>) query.getResultList();

        entityManager.close();

        return foundUsers.size() == 0 ? null : foundUsers.get(0);
    }

}

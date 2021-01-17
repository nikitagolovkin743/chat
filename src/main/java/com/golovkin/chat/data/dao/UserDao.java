package com.golovkin.chat.data.dao;

import com.golovkin.chat.data.entities.User;
import com.golovkin.chat.data.utils.EntityManagerProvider;

import java.util.List;

// +
public class UserDao extends AbstractDao<User> {
    public static final UserDao instance = new UserDao();

    @SuppressWarnings("unchecked")
    public static List<User> findAllUsersExceptId(int id) {
        var entityManager = EntityManagerProvider.createEntityManager();

        var query = entityManager.createQuery("SELECT u FROM User u WHERE u.id <> :id");
        query.setParameter("id", id);

        var foundUsers = (List<User>) query.getResultList();

        entityManager.close();

        return foundUsers;
    }

    @SuppressWarnings("unchecked")
    public User findById(int id) {
        var entityManager = EntityManagerProvider.createEntityManager();

        var query = entityManager.createQuery("SELECT u FROM User u WHERE u.id = :id");
        query.setParameter("id", id);

        var foundUsers = (List<User>) query.getResultList();

        entityManager.close();

        return foundUsers.size() == 0 ? null : foundUsers.get(0);
    }

    @SuppressWarnings("unchecked")
    public User findByName(String username) {
        var entityManager = EntityManagerProvider.createEntityManager();

        var query = entityManager.createQuery("SELECT u FROM User u WHERE u.name = :name");
        query.setParameter("name", username);

        var foundUsers = (List<User>) query.getResultList();

        entityManager.close();

        return foundUsers.size() == 0 ? null : foundUsers.get(0);
    }

    @SuppressWarnings("unchecked")
    public User findByCredentials(String username, String password) {
        var entityManager = EntityManagerProvider.createEntityManager();

        var query = entityManager.createQuery("SELECT u FROM User u WHERE u.name = :name AND u.password = :password");
        query.setParameter("name", username);
        query.setParameter("password", password);

        var foundUsers = (List<User>) query.getResultList();

        entityManager.close();

        return foundUsers.size() == 0 ? null : foundUsers.get(0);
    }

}

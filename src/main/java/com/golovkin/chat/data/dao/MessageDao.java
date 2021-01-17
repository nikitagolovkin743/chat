package com.golovkin.chat.data.dao;

import com.golovkin.chat.data.entities.Message;
import com.golovkin.chat.data.entities.User;
import com.golovkin.chat.data.utils.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

// +
public class MessageDao extends AbstractDao<Message> {
    public static final MessageDao instance = new MessageDao();

    @SuppressWarnings("unchecked")
    public List<Message> findMessagesBetweenUsers(User sender, User receiver) {
        EntityManager entityManager = EntityManagerProvider.createEntityManager();

        Query query = entityManager.createQuery("SELECT m FROM Message m WHERE (m.sender = :sender AND m.receiver = :receiver) OR (m.sender = :receiver AND m.receiver = :sender) ORDER BY m.timestamp");
        query.setParameter("sender", sender);
        query.setParameter("receiver", receiver);

        List<Message> messages = (List<Message>) query.getResultList();

        entityManager.close();

        return messages;
    }
}

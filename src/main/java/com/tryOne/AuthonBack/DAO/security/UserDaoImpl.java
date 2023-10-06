package com.tryOne.AuthonBack.DAO.security;

import com.tryOne.AuthonBack.entity.security.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao{

    @Autowired
    EntityManager entityManager;

    public UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(User theUSer) throws Exception {
        entityManager.persist(theUSer);
    }

    @Override
    public User getAllUsers(int id) {

        TypedQuery<User> personId = entityManager.createQuery("SELECT u FROM User u " +
                "join fetch u.roles " +
                "where u.id = :userId",User.class);

        personId.setParameter("userId",id);

        return personId.getSingleResult();
    }

    @Override
    public User getUserByName(String uName) throws Exception {

        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u " +
                "JOIN FETCH u.roles " +
                "where u.username = :userName ",User.class);

        query.setParameter("userName",uName);

        return query.getSingleResult();
    }



    @Override
    @Transactional
    public User updateUserPassword(User user,String Password) throws Exception{

        user.setPassword(Password);
        entityManager.merge(user);

        return user;
    }

}


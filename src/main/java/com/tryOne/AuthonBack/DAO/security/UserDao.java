package com.tryOne.AuthonBack.DAO.security;

import com.tryOne.AuthonBack.entity.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao {


    void save(User theUSer) throws Exception;

    User getAllUsers(int id);

    User getUserByName(String uName) throws  Exception;

    User updateUserPassword(User user, String Password) throws Exception;


}

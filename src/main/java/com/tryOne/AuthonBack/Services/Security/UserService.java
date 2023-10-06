package com.tryOne.AuthonBack.Services.Security;

import com.tryOne.AuthonBack.DAO.security.RoleDao;
import com.tryOne.AuthonBack.DAO.security.UserDao;
import com.tryOne.AuthonBack.entity.security.Role;
import com.tryOne.AuthonBack.entity.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

//auth 3.0 /3
@Service
public class UserService implements UserDetailsService {

    //password encoder
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;


    // auth 3.1
    //non db approach but a simulation of DAO
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        //not necessary ---
//        System.out.println("In the user details service");
//
//        if(username.equals("Ethan"))throw new UsernameNotFoundException("not Ethan");
//        //---
//
//        Set<Role> role = new HashSet<>();
//
//        role.add(new Role(1, "USER"));
//        return new User(1,
//                "mat",
//                encoder.encode("password"),
//                1,
//                "mat",
//                "Wick",
//                "mathew@gmail.com", role);
//    }

    // auth 3.1
    //db approach
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //not necessary ---
        System.out.println("In the user details service");

        try {
            System.out.println(userDao.getUserByName(username));

            UserDetails userDetails = userDao.getUserByName(username);

            System.out.println(username);

            return userDetails;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

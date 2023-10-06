package com.tryOne.AuthonBack.Services.Security;


import com.tryOne.AuthonBack.DAO.security.RoleDao;
import com.tryOne.AuthonBack.DAO.security.UserDao;
import com.tryOne.AuthonBack.DTO.security.LoginResponseDTO;
import com.tryOne.AuthonBack.entity.security.Role;
import com.tryOne.AuthonBack.entity.security.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
//actual be calling the db layer thats why
@Transactional
public class AuthenticationServiceSignUp {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PasswordEncoder passwordEncoder;


    //token related
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;



    //create a user dto then pass that info to the db
    public User registerUser(String userName, String password, String firstName, String lastName, String email){

        System.out.println(userName);

        String encodedPassword = "{bcrypt}"+passwordEncoder.encode(password);
        Role userRole = roleDao.findRoleByName("ROLE_USER");

        Set<Role> authorities = new HashSet<>();

        authorities.add(userRole);

        if(firstName == null || lastName == null || email == null){
            firstName = "--";
            lastName = "--";
            email = "--";
        }

        User user = new User(0, userName,encodedPassword,1,firstName,lastName,email,authorities);

        //set for login fields
        try {
            userDao.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return  user;
    }


    //token related
    //going to look for a username and password and make sure they are proper
    public LoginResponseDTO loginUser(String username, String password){
        //checks if a user is valid only then give out a token

        System.out.println("name :" +username);
        System.out.println("password: "+password);
        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username,password)
            );

            String token = tokenService.generateJwt(auth);

            return new LoginResponseDTO(userDao.getUserByName(username),token);

        }catch (AuthenticationException e){
            System.out.println("404 in service");
            System.out.println(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }

}

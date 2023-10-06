package com.tryOne.AuthonBack.controllers.rest.userRestController;

import com.tryOne.AuthonBack.DTO.security.LoginResponseDTO;
import com.tryOne.AuthonBack.DTO.security.RegistrationDto;
import com.tryOne.AuthonBack.Services.Security.AuthenticationServiceSignUp;
import com.tryOne.AuthonBack.entity.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationSignUpController {

    @Autowired
    private AuthenticationServiceSignUp authenticationServiceSignUp;

    @PostMapping("/register")
    //map endpoints
    public User newUser(@RequestBody RegistrationDto registrationDto){
        return authenticationServiceSignUp.registerUser(
                registrationDto.getUsername(),
                registrationDto.getPassword(),
                null,
                null,
                null);

    }

    //video 1:33:50
    @GetMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody RegistrationDto registrationDto){

        return authenticationServiceSignUp.loginUser(registrationDto.getUsername(), registrationDto.getPassword());

    }


}

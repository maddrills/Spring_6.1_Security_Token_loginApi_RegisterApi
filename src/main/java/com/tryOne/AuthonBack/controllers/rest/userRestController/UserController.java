package com.tryOne.AuthonBack.controllers.rest.userRestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//auth 1
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/")
    public String HelloUserController(){
        return "Hello user :)";
    }
}

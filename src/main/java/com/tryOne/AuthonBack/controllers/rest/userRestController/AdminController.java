package com.tryOne.AuthonBack.controllers.rest.userRestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//auth 1
@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/")
    public String helloAdminController(){
        return "Admin level access";
    }
}

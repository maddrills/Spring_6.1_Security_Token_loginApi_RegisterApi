package com.tryOne.AuthonBack;

import com.tryOne.AuthonBack.DAO.security.RoleDao;
import com.tryOne.AuthonBack.DAO.security.UserDao;
import com.tryOne.AuthonBack.entity.security.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Mathew F Vadakumchery
 * @see <a href = "https://www.youtube.com/watch?v=TeBt0Ike_Tk&t=4937s"> The video the code is based out of </a>
 * @see <h2 color = "#808000">the above link works.....!</h2>
 * @see <h2 color = "#800000"> Video has minor deprecations using spring 6.1</h2><h2 style="color:#87CEEB"> that i corrected </h2>
 * */

@SpringBootApplication
public class AuthonBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthonBackApplication.class, args);
	}

//	@Bean
//	CommandLineRunner runner(RoleDao roleDao, UserDao userDao, PasswordEncoder passwordEncoder){
//		return (args) -> {
//			roleDao.addARoll(new Role(1,"ROLE_PEN"));
//		};
//	}
}

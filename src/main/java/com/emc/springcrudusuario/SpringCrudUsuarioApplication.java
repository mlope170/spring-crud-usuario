package com.emc.springcrudusuario;

import com.emc.springcrudusuario.dto.UserDto;
import com.emc.springcrudusuario.entities.UserEntity;
import com.emc.springcrudusuario.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;

@SpringBootApplication
public class SpringCrudUsuarioApplication {

    public static void main(String[] args) {
		SpringApplication.run(SpringCrudUsuarioApplication.class, args);
//        ApplicationContext context = SpringApplication.run(SpringCrudUsuarioApplication.class, args);
//        UserService userService = context.getBean(UserService.class);
//            UserEntity admin = new UserEntity(
//                    "admin",
//                    "admin",
//                    "mail.dominio.com",
//                    "$2y$06$VuJeZGcxtwedOq8886lbFek9lO8EWxzB.G8z/bLwOkZaHD6lOW8Jy");
//
//        UserEntity user = new UserEntity(
//                "user",
//                "user",
//                "mail.dominio.com",
//                "$2y$06$VuJeZGcxtwedOq8886lbFek9lO8EWxzB.G8z/bLwOkZaHD6lOW8Jy");
//        userService.saveUser(user);
//        System.out.println(usuario.getRoles());
//        System.out.println(userService.existByUserName("kk"));
//        System.out.println(userService.existByUserName("Eusebio"));
    }

}


package com.emc.springcrudusuario.controllers;

import com.emc.springcrudusuario.dto.UserDetailDto;
import com.emc.springcrudusuario.dto.UserDto;
import com.emc.springcrudusuario.entities.UserEntity;
import com.emc.springcrudusuario.service.UserService;
import io.jsonwebtoken.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("crud-user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/user")
    public ResponseEntity<UserDto> userByLogin(@RequestBody UserDto user) {
        try {
            UserEntity userEntity = userService.getByUserLogin(user.getLogin()).orElseThrow(NoSuchElementException::new);
            UserDto userDto = new UserDto(userEntity.getId(), userEntity.getUsuLogin(), userEntity.getUsuNombre(), userEntity.getUsuMail(),userEntity.getUsuAvatar(), userEntity.getAuthorities());
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } catch (NoSuchElementException exception) {
            HttpHeaders headers = new HttpHeaders();
            headers.set("CRUD-response-Header",
                    "Value-Usuario no encontrado");
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/user-detail")
    public ResponseEntity<UserDetailDto> userDetailByLogin(@RequestBody UserDto user) {
        try {
            UserEntity userEntity = userService.getByUserLogin(user.getLogin()).orElseThrow(NoSuchElementException::new);
            UserDetailDto userDetailDto = new UserDetailDto(
                    userEntity.getId(),
                    userEntity.getUsuLogin(),
                    userEntity.getUsuNombre(),
                    userEntity.getUsuServicioTerritorial(),
                    userEntity.getRoles(),
                    userEntity.getUsuUsuario(),
                    userEntity.getUsuFecha(),
                    userEntity.getUsuMail(),
                    userEntity.getUsuAvatar()
            );
            return new ResponseEntity<>(userDetailDto, HttpStatus.OK);
        } catch (NoSuchElementException exception) {
            HttpHeaders headers = new HttpHeaders();
            headers.set("CRUD-response-Header",
                    "Value-Usuario no encontrado");
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> users() {
        List<UserDto> userList;
        try {
            List<UserEntity> usersEntity = userService.getUsers().orElseThrow(NoSuchElementException::new);
            userList = usersEntity.stream().map(UserEntity::entityToDto).collect(Collectors.toList());
            return new ResponseEntity<>(userList, HttpStatus.OK);
        } catch (NoSuchElementException exception) {
            HttpHeaders headers = new HttpHeaders();
            headers.set("CRUD-response-Header",
                    "Value-No hay usuarios");
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/users-detail")
    public ResponseEntity<List<UserDetailDto>> usersDetail() {
        List<UserDetailDto> userList;
        try {
            List<UserEntity> usersEntity = userService.getUsers().orElseThrow(NoSuchElementException::new);
            userList = usersEntity.stream().map(UserEntity::entityToDtoDetail).collect(Collectors.toList());
            return new ResponseEntity<>(userList, HttpStatus.OK);
        } catch (NoSuchElementException exception) {
            HttpHeaders headers = new HttpHeaders();
            headers.set("CRUD-response-Header",
                    "Value-No hay usuarios");
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> add( @RequestBody UserDetailDto newUser) {
        UserEntity userEntity;
        HttpHeaders headers = new HttpHeaders();

        if (userService.existByUserLogin(newUser.getLogin())){
            headers.set("CRUD-response-Header",
                    "Value-ese login ya existe");
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }

        if (userService.existByUserName(newUser.getName())){
            headers.set("CRUD-response-Header",
                    "Value-ese nombre ya existe");
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }

        if (userService.existByUserMail(newUser.getEmail())){
            headers.set("CRUD-response-Header",
                    "Value-ese mail ya existe");
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }

        userEntity = new UserEntity(
                newUser.getLogin(),
                newUser.getName(),
                newUser.getEmail(),
                passwordEncoder.encode("tragsa"));

        userEntity.setUsuTipo(userEntity.authToType(newUser.getType()));

        userService.saveUser(userEntity);
        return new ResponseEntity<>(userEntity, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update( @RequestBody UserDetailDto user) {
        UserEntity userEntity;
        HttpHeaders headers = new HttpHeaders();
        try {
            userEntity = userService.getByUserId(user.getId()).orElseThrow(NoSuchElementException::new);
        } catch (NoSuchElementException exception) {
            headers.set("CRUD-response-Header",
                    "Value-No existe este usuario");
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        }

        if (userService.existByUserLogin(user.getLogin()) &&
                userService.getByUserLogin(user.getLogin()).get().getId() != user.getId()) {
            headers.set("CRUD-response-Header",
                    "Value-ese login ya existe");
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }
        if (userService.existByUserName(user.getName()) &&
                userService.getByUserName(user.getName()).get().getId() != user.getId()) {
            headers.set("CRUD-response-Header",
                    "Value-ese nombre ya existe");
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }
        if (userService.existByUserMail(user.getEmail()) &&
                userService.getByUserMail(user.getEmail()).get().getId() != user.getId()) {
            headers.set("CRUD-response-Header",
                    "Value-ese mail ya existe");
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }
        userEntity.setUsuLogin(user.getLogin());
        userEntity.setUsuNombre(user.getName());
        userEntity.setUsuServicioTerritorial(user.getTerrServiceId());
        userEntity.setUsuTipo(userEntity.authToType(user.getType()));
        userEntity.setUsuMail(user.getEmail());

        userService.saveUser(userEntity);
        return new ResponseEntity<>(userEntity, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody UserDetailDto user) {
        UserEntity userEntity;
        HttpHeaders headers = new HttpHeaders();
        try {
            userEntity = userService.getByUserId(user.getId()).orElseThrow(NoSuchElementException::new);
        } catch (NoSuchElementException exception) {
            headers.set("CRUD-response-Header",
                    "Value-No existe este usuario");
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        }

        if(!userService.deleteUser(userEntity)){
            headers.set("CRUD-response-Header",
                    "Value-No se ha podido eliminar el usuario");
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }

        headers.set("CRUD-response-Header",
                "Value-Usuario eliminiado");
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }


}

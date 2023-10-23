package com.emc.springcrudusuario.service;

import com.emc.springcrudusuario.entities.UserEntity;
import com.emc.springcrudusuario.repositories.I_UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    @Autowired
    I_UserRepository userRepository;

    public Optional<List<UserEntity>> getUsers() {
        return Optional.of(userRepository.findAll());
    }

    public Optional<UserEntity> getByUserLogin(String userLogin) {
        return userRepository.findByUsuLogin(userLogin);
    }

    public Optional<UserEntity> getByUserName(String userName) {
        return userRepository.findByUsuNombre(userName);
    }

    public Optional<UserEntity> getByUserId(Long id) {
        return userRepository.findById(id);
    }

    public Optional<UserEntity> getByUserMail(String mail) {
        return userRepository.findByUsuMail(mail);
    }

    public boolean existByUserLogin(String userLogin) {
        return userRepository.existsByUsuLogin(userLogin);
    }

    public boolean existByUserName(String userName) {
        return userRepository.existsByUsuNombre(userName);
    }

    public boolean existByUserMail(String userMail) {
        return userRepository.existsByUsuMail(userMail);
    }

    public UserEntity saveUser(UserEntity user) {
        return userRepository.save(user);
    }

    public boolean deleteUser(UserEntity user) {
        try {
            userRepository.delete(user);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}

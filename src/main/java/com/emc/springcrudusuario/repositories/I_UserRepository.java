package com.emc.springcrudusuario.repositories;

import com.emc.springcrudusuario.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface I_UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsuNombre(String userName);
    Optional<UserEntity> findByUsuLogin(String userlogin);
    Optional<UserEntity> findByUsuMail(String mail);
    boolean existsByUsuLogin(String userLogin);
    boolean existsByUsuNombre(String userName);
    boolean existsByUsuMail(String userMail);


}

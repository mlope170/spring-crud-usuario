package com.emc.springcrudusuario.dto;

import com.emc.springcrudusuario.enums.E_UserAuthority;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {
    Long id;
    String login;
    String name;
    String mail;
    String avatar;
    List<E_UserAuthority> rol;
}

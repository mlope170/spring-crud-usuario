package com.emc.springcrudusuario.dto;

import com.emc.springcrudusuario.enums.E_UserAuthority;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserDetailDto {
    private Long id;
    @NotBlank
    private String login;
    @NotBlank
    private String name;
    @NotBlank
    private int terrServiceId;
    @NotBlank
    private List<E_UserAuthority> type;
    @NotBlank
    private String createdBy;
    @NotBlank
    private LocalDate alterDate;
    @Email
    private String email;
    @NotBlank
    private String avatar;
}

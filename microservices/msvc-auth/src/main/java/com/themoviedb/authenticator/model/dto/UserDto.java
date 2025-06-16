package com.themoviedb.authenticator.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    private Integer id;
    private String username;
    private String email;
    private String lastName;
    private String firstName;
    private String country;
    private String phone;
    private String address;
}

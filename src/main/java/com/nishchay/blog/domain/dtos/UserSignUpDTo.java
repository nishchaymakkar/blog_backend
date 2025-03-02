package com.nishchay.blog.domain.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserSignUpDTo {
    private String userEmail;
    private String userPassword;
    private String userName;
    private String userProfession;
}

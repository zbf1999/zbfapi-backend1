package com.zbf.zbfapibackend.model.dto.user;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UserRegisterRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -8391612898867090529L;

    private String userAccount;
    private String userPassword;
    private String checkPassword;
}

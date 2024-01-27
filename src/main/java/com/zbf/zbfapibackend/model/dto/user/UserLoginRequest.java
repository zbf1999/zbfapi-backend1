package com.zbf.zbfapibackend.model.dto.user;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UserLoginRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -6788130875311992409L;

    private String userAccount;
    private String userPassword;
}

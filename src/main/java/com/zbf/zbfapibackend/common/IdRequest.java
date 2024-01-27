package com.zbf.zbfapibackend.common;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * ID请求
 *

 */
@Data
public class IdRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    @Serial
    private static final long serialVersionUID = 1L;
}
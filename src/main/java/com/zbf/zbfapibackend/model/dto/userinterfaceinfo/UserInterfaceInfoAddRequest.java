package com.zbf.zbfapibackend.model.dto.userinterfaceinfo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 创建请求
 *

 */
@Data
public class UserInterfaceInfoAddRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -1863207741951654184L;
    /**
     * 调用用户 id
     */
    private Long userId;

    /**
     * 接口 id
     */
    private Long interfaceInfoId;

    /**
     * 总调用次数
     */
    private Integer totalNum;

    /**
     * 剩余调用次数
     */
    private Integer leftNum;

}
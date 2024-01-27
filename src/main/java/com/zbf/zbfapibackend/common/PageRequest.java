package com.zbf.zbfapibackend.common;

import com.zbf.zbfapibackend.constant.CommonConstant;
import lombok.Data;

@Data
public class PageRequest {
    
    private long current = 1;
    private long pageSize = 10;
    private String sortField;
    private String sortOrder = CommonConstant.SORT_ORDER_ASC;
}

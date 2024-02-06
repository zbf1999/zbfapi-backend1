package com.zbf.zbfapibackend.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.zbf.zbfapibackend.common.ResultUtils;
import com.zbf.zbfapibackend.model.vo.UserLoginVo;
import com.zbf.zbfapibackend.service.UserService;
import com.zbf.zbfapicommon.model.entity.User;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zbf
 */
@Component
public class TestUtils {
    
    @Resource
    private UserService userService;
    
    public void test() {
        
    }
    
    public static void main(String[] args) {
        //lambda demo
        Runnable runnable = () -> System.out.println("Hello World");
        runnable.run();
        //复杂一点的lambda demo
        Runnable runnable1 = () -> {
            System.out.println("Hello World");
            System.out.println("Hello World");
        };  
        runnable1.run();
    }
}

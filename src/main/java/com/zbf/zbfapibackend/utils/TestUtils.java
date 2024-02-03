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
        long current = 1;
        long size = 10;
        User userQuery = new User();
        
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(userQuery);
        Page<User> userPage = userService.page(new Page<>(current, size), queryWrapper);
        System.out.println(userPage);
        System.out.println(userPage.getRecords());
        Page<UserLoginVo> userLoginVoPage = new PageDTO<>(userPage.getCurrent(), userPage.getSize(), userPage.getTotal());
        List<UserLoginVo> userLoginVoList = userPage.getRecords().stream().map(user -> {
            UserLoginVo userLoginVo = new UserLoginVo();
            BeanUtils.copyProperties(user, userLoginVo);
            return userLoginVo;
        }).toList();
        userLoginVoPage.setRecords(userLoginVoList);
        System.out.println(ResultUtils.success(userLoginVoPage));
    }
    
    public static void main(String[] args) {
        TestUtils test = new TestUtils();
        test.test();
    }
}

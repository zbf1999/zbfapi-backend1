package com.zbf.zbfapibackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.zbf.zbfapibackend.common.BaseResponse;
import com.zbf.zbfapibackend.common.ErrorCode;
import com.zbf.zbfapibackend.common.ResultUtils;
import com.zbf.zbfapibackend.exception.BusinessException;
import com.zbf.zbfapibackend.model.dto.user.UserLoginRequest;
import com.zbf.zbfapibackend.model.dto.user.UserQueryRequest;
import com.zbf.zbfapibackend.model.dto.user.UserRegisterRequest;
import com.zbf.zbfapibackend.model.vo.UserLoginVo;
import com.zbf.zbfapibackend.service.UserService;
import com.zbf.zbfapicommon.model.entity.User;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;

    //region 登录相关

    /**
     * 用户注册
     */
    @PostMapping("register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return null;
        }
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        return ResultUtils.success(result);
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public BaseResponse<UserLoginVo> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserLoginVo user = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(user);
    }

    /**
     * 用户注销
     */
    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = userService.userLogout(request);
        return ResultUtils.success(result);
    }
    
    @GetMapping("/get/login")
    public BaseResponse<UserLoginVo> getLoginUser(HttpServletRequest request) {
        UserLoginVo loginUser = userService.getLoginUser(request);
        return ResultUtils.success(loginUser);
    }

    //endregion

    // region 增删改查
    @GetMapping("/list")
    public BaseResponse<List<UserLoginVo>> listUser(UserQueryRequest userQueryRequest) {
        User userQuery = new User();
        if (userQueryRequest != null) {
            BeanUtils.copyProperties(userQueryRequest, userQuery);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(userQuery);
        List<User> userList = userService.list(queryWrapper);
        List<UserLoginVo> userLoginVos = userList.stream().map(user -> {
            UserLoginVo userLoginVo = new UserLoginVo();
            BeanUtils.copyProperties(user, userLoginVo);
            return userLoginVo;
        }).toList();
        return ResultUtils.success(userLoginVos);
    }
    
    @GetMapping("/list/page")
    public BaseResponse<Page<UserLoginVo>> listUserByPage(UserQueryRequest userQueryRequest) {
        long current = 1;
        long size = 10;
        User userQuery = new User();
        if (userQueryRequest != null) {
            BeanUtils.copyProperties(userQueryRequest, userQuery);
            current = userQueryRequest.getCurrent();
            size = userQueryRequest.getPageSize();
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(userQuery);
        Page<User> userPage = userService.page(new Page<>(current, size), queryWrapper);
        Page<UserLoginVo> userLoginVoPage = new PageDTO<>(userPage.getCurrent(), userPage.getSize(), userPage.getTotal());
        List<UserLoginVo> userLoginVoList = userPage.getRecords().stream().map(user -> {
            UserLoginVo userLoginVo = new UserLoginVo();
            BeanUtils.copyProperties(user, userLoginVo);
            return userLoginVo;
        }).toList();
        userLoginVoPage.setRecords(userLoginVoList);
        return ResultUtils.success(userLoginVoPage);
    }
    
    
    // endregion
}

































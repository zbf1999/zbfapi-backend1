package com.zbf.zbfapibackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zbf.zbfapibackend.model.vo.UserLoginVo;
import com.zbf.zbfapicommon.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;


/**
* @author zbf
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2024-01-27 13:19:58
*/
public interface UserService extends IService<User> {

    /**
     *用户注册
     * @param userAccount 账号
     * @param userPassword 密码
     * @param checkPassword 检查密码
     * @return 用户id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     * @param userAccount 账号
     * @param userPassword 密码
     * @param request 请求
     * @return 用户登录信息
     */
    UserLoginVo userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户登出
     * @param request 请求
     * @return 是否登出成功
     */
    boolean userLogout(HttpServletRequest request);
    
    /**
     * 获取登录用户
     * @param request 请求
     * @return 登录用户
     */
    UserLoginVo getLoginUser(HttpServletRequest request);

    /**
     * 判断是否为管理员
     * @param request 请求
     * @return 是否为管理员
     */
    boolean isNotAdmin(HttpServletRequest request);
    
    /**
     * 判断是否为管理员
     * @param userLoginVo 登录用户
     * @return 是否为管理员
     */
    boolean isNotAdmin(UserLoginVo userLoginVo);

}

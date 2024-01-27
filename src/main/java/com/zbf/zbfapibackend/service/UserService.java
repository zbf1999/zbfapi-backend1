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

    UserLoginVo userLogin(String userAccount, String userPassword, HttpServletRequest request);

    boolean userLogout(HttpServletRequest request);
    
    UserLoginVo getLoginUser(HttpServletRequest request);

    /**
     * 是否为管理员
     */
    boolean isAdmin(HttpServletRequest request);

}

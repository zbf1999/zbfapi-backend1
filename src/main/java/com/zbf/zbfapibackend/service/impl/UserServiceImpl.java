package com.zbf.zbfapibackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zbf.zbfapibackend.mapper.UserMapper;
import com.zbf.zbfapibackend.service.UserService;
import com.zbf.zbfapicommon.model.entity.User;
import org.springframework.stereotype.Service;

/**
* @author zbf
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2024-01-27 13:19:58
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

}





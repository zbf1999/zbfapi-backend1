package com.zbf.zbfapibackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zbf.zbfapicommon.model.entity.UserInterfaceInfo;


/**
* @author zbf
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service
* @createDate 2024-01-27 13:23:44
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {


    boolean invokeCount(long interfaceInfoId, long userId);
}

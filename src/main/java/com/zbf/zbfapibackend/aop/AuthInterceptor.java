package com.zbf.zbfapibackend.aop;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.zbf.zbfapibackend.annotation.AuthCheck;
import com.zbf.zbfapibackend.common.ErrorCode;
import com.zbf.zbfapibackend.exception.BusinessException;
import com.zbf.zbfapibackend.model.vo.UserLoginVo;
import com.zbf.zbfapibackend.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class AuthInterceptor {

    @Resource
    private UserService userService;
    
    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint proceedingJoinPoint, AuthCheck authCheck) throws Throwable{
        List<String> anyRole = Arrays.stream(authCheck.anyRole()).filter(StringUtils::isNotBlank).toList();
        String mustRole = authCheck.mustRole();
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        UserLoginVo user = userService.getLoginUser(request);
        
        if (CollectionUtils.isNotEmpty(anyRole)) {
            String userRole = user.getUserRole();
            if (!anyRole.contains(userRole)) {
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "无权限");
            }
        }
        
        if (StringUtils.isNotBlank(mustRole)) {
            String userRole = user.getUserRole();
            if (!mustRole.equals(userRole)) {
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "无权限");
            }
        }
        return proceedingJoinPoint.proceed();
    }
}

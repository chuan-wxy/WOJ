package org.chuan.woj.aop;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.chuan.woj.annotation.AuthCheck;
import org.chuan.woj.common.ResultStatus;
import org.chuan.woj.common.enums.UserRoleEnum;
import org.chuan.woj.constant.UserConstant;
import org.chuan.woj.mapper.RoleMapper;
import org.chuan.woj.service.Role.RoleService;
import org.chuan.woj.service.user.UserRoleService;
import org.chuan.woj.service.user.UserService;
import org.chuan.woj.utils.JwtUtil;
import org.chuan.woj.utils.RedisUtil;
import org.chuan.woj.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

/**
 * Aop 拦截器
 *
 * @Author: chuan-wxy
 * @Date: 2024/8/19 20:05
 * @Description: 用aop时实现的认证和授权
 */
@Slf4j
@Aspect
@Component
public class Interceptor {

    @Autowired
    UserService userService;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    RoleService roleService;

    @Around("@annotation(authCheck)")
    public Object   AuthenticationInterceptor(ProceedingJoinPoint point, AuthCheck authCheck) throws Throwable {


        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        String jwt = request.getHeader("Authorization");
        // jwt = jwt.split(" ")[1];
        log.info("拦截器---->jwt令牌为："+jwt);
        // 当前登录用户
        if (jwt == null) {
            log.info("拦截器---->jwt不存在,未登录");
            return ResultUtils.error("未登录");
        }

        boolean res = redisUtil.hasKey(jwt);
        if(!res) {
            log.info("拦截器---->登陆过期，请重新登录");
            return ResultUtils.error("登陆过期，请重新登录");
        }

        Claims claims = jwtUtil.getClaimByToken(jwt);
        if(claims == null) {
            return ResultUtils.error(ResultStatus.SYSTEM_ERROR);
        }

        String userAccount = claims.getSubject();
        log.info("拦截器---->"+userAccount+"通过认证");

        List<String> roleList = roleMapper.SelectRoleByUserAccount(userAccount);

        String mustRole = authCheck.mustRole();

        // 不需要权限，放行
        if (mustRole == null || mustRole == UserConstant.DEFAULT_USER) {
            return point.proceed();
        }

        if (mustRole.equals("admin") && (!roleList.contains(UserRoleEnum.AMDIN.getText()) && !roleList.contains(UserRoleEnum.ROOR.getText()))) {
            return ResultUtils.error(ResultStatus.ACCESS_DENIED);
        }

        if (mustRole.equals("root") && (!roleList.contains(UserRoleEnum.ROOR.getText()))) {
            return ResultUtils.error(ResultStatus.ACCESS_DENIED);
        }

        if(mustRole.equals("no_submit") && roleList.contains(UserRoleEnum.NO_SUBMIT_USER.getText())) {
            return ResultUtils.error("该账号禁止提交");
        }

        if(mustRole.equals("no_discuss_user") && roleList.contains(UserRoleEnum.NO_DISCUSS_USER.getText())) {
            return ResultUtils.error("该账号禁止发帖讨论");
        }

        if(mustRole.equals("mute_user") && roleList.contains(UserRoleEnum.MUTE_USER.getText())) {
            return ResultUtils.error("该账号禁止回复");
        }

        // 通过权限校验，放行
        return point.proceed();
    }
}

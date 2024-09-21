package org.chuan.woj.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.chuan.woj.annotation.AuthCheck;
import org.chuan.woj.common.BaseResponse;
import org.chuan.woj.constant.UserConstant;
import org.chuan.woj.exception.StatusFailException;
import org.chuan.woj.pojo.dto.user.UserLoginDTO;
import org.chuan.woj.pojo.dto.user.UserLogoutDTO;
import org.chuan.woj.pojo.dto.user.UserProfileDTO;
import org.chuan.woj.pojo.dto.user.UserRegisterDTO;
import org.chuan.woj.pojo.vo.user.UserLoginVO;
import org.chuan.woj.service.email.EmailService;
import org.chuan.woj.service.user.UserService;
import org.chuan.woj.utils.RedisUtil;
import org.chuan.woj.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
@Tag(name = "UserController")
public class UserController {



    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;

    @Autowired
    RedisUtil redisUtil;

    /**
     * 获取验证码
     *
     * @return
     */
    @GetMapping("/get-register-code")
    public BaseResponse<String> getRegisterCode(@RequestParam(value = "email", required = true) String email) {
        return emailService.getCaptchaCode(email,null);
    }

    /**
     * 用户注册
     *
     * @param userRegisterDTO
     * @return
     */
    @PostMapping("/register")
    public BaseResponse<Void> register(@RequestBody UserRegisterDTO userRegisterDTO) throws StatusFailException {
        return userService.register(userRegisterDTO);
    }

    /**
     * 登录
     *
     * @return BaseResponse<UserLoginVO>
     */
    @PostMapping("/login")
    public BaseResponse<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO, HttpServletRequest request, HttpServletResponse response) {
        return userService.login(userLoginDTO, request, response);
    }

    /**
     * 修改用户信息
     */
    @PostMapping("/update-profile")
    @AuthCheck(mustRole = UserConstant.DEFAULT_USER)
    public BaseResponse<Void> updateProfile(@RequestBody UserProfileDTO userProfileDTO) {
        return userService.updateProfile(userProfileDTO);
    }

    /**
     * 注销获取验证码
     *
     * @return
     */
    @PostMapping("/get-logout-code")
    @AuthCheck(mustRole = UserConstant.DEFAULT_USER)
    public BaseResponse<Void> getLogoutCode(@RequestBody UserLogoutDTO userLogoutDTO) {
        return userService.getLogoutCode(userLogoutDTO);
    }

    /**
     * 注销账号
     */
    @PostMapping("/logout-forever")
    @AuthCheck(mustRole = UserConstant.DEFAULT_USER)
    public BaseResponse<Void> logoutForever(@RequestBody UserLogoutDTO userLogoutDTO) {
        return userService.logoutForever(userLogoutDTO);
    }

    /**
     * 退出登录
     */
    @PostMapping("/logout")
    @AuthCheck(mustRole = UserConstant.DEFAULT_USER)
    public BaseResponse<Void> logout(@RequestBody UserLogoutDTO userLogoutDTO) {
        return userService.logout(userLogoutDTO);
    }

    /**
     * 获取用户角色
     */
    @PostMapping("/get-role")
    @AuthCheck(mustRole = UserConstant.DEFAULT_USER)
    public BaseResponse<List<String>> getRole(String userAccount) {
        return userService.getUserRole(userAccount);
    }

    /**
     * 获取用户
     */
    @PostMapping("/get-loginuser")
    public BaseResponse<UserLoginVO> getLoginUser(HttpServletRequest request) throws StatusFailException {
        System.out.println("getLoginUser");
        return userService.getLoginUser(request);
    }

    /**
     * 检查JWT令牌是否过期
     *  true 过期
     */
    @GetMapping("/check-jwt")
    public BaseResponse<Boolean> checkJWT(@RequestParam(value = "JWT") String JWT) {
        return userService.checkJWT(JWT);
    }

}

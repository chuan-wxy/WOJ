package org.chuan.woj.service.user;

import cn.hutool.jwt.JWT;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.chuan.woj.common.BaseResponse;
import org.chuan.woj.exception.StatusFailException;
import org.chuan.woj.pojo.dto.user.UserLoginDTO;
import org.chuan.woj.pojo.dto.user.UserLogoutDTO;
import org.chuan.woj.pojo.dto.user.UserProfileDTO;
import org.chuan.woj.pojo.dto.user.UserRegisterDTO;
import org.chuan.woj.pojo.entity.User;
import org.chuan.woj.pojo.vo.user.UserLoginVO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 公共接口
 *
 * @Author chuan-wxy
 * @Create 2024/8/14 20:25
 */
public interface UserService extends IService<User> {

    BaseResponse<Void> register(UserRegisterDTO userAddDTO) throws StatusFailException;

    BaseResponse<UserLoginVO> login(UserLoginDTO userLoginDTO, HttpServletRequest request, HttpServletResponse response);

    BaseResponse<Void> updateProfile(UserProfileDTO userProfileDTO);

    BaseResponse<Void> logout(UserLogoutDTO userLogoutDTO);

    BaseResponse<Void> getLogoutCode(UserLogoutDTO userLogoutDTO);

    BaseResponse<Void> logoutForever(UserLogoutDTO userLogoutDTO);

    BaseResponse<List<String>> getUserRole(String userAccount);

    BaseResponse<UserLoginVO> getLoginUser(HttpServletRequest request) throws StatusFailException;

    BaseResponse<Boolean> checkJWT(String JWT);
}

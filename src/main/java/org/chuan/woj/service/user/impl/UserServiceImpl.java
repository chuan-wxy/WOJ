package org.chuan.woj.service.user.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.chuan.woj.common.BaseResponse;
import org.chuan.woj.common.enums.AccountEnum;
import org.chuan.woj.common.enums.EmailEnum;
import org.chuan.woj.exception.StatusFailException;
import org.chuan.woj.mapper.RoleMapper;
import org.chuan.woj.mapper.UserMapper;
import org.chuan.woj.mapper.UserRoleMapper;
import org.chuan.woj.pojo.dto.user.UserLoginDTO;
import org.chuan.woj.pojo.dto.user.UserLogoutDTO;
import org.chuan.woj.pojo.dto.user.UserProfileDTO;
import org.chuan.woj.pojo.dto.user.UserRegisterDTO;
import org.chuan.woj.pojo.entity.User;
import org.chuan.woj.pojo.entity.UserRole;
import org.chuan.woj.pojo.vo.user.UserLoginVO;
import org.chuan.woj.pojo.vo.user.UserVO;
import org.chuan.woj.service.user.UserService;
import org.chuan.woj.utils.EmailUtil;
import org.chuan.woj.utils.JwtUtil;
import org.chuan.woj.utils.RedisUtil;
import org.chuan.woj.utils.ResultUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author chuan-wxy
 * @Create 2024/8/14 20:25
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    public static final String SALT = "wxy";
    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRoleMapper userRoleMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    JwtUtil jwtUtil;

    /**
     * 用户注册
     *
     * @param userAddDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse<Void> register(UserRegisterDTO userAddDTO) throws StatusFailException {
        // todo 检查网站是否开启注册
        String userAccount = userAddDTO.getUserAccount();
        String userPassword = userAddDTO.getUserPassword();
        String rePassword = userAddDTO.getRePassword();
        String captcha = userAddDTO.getCaptcha();

        String redisCaptcha = (String) redisUtil.get(EmailEnum.REGISTER_KEY_PREFIX.getValue() + userAccount);

        if (!captcha.equals(redisCaptcha)) {
            log.info(userAccount + "注册时验证码有误");
            return new BaseResponse(400, "验证码错误");
        }
        if (!rePassword.equals(userPassword)) {
            log.info(userAccount + "注册时两次密码不一致");
            return new BaseResponse(400, "两次密码不一致");
        }
        if (StringUtils.isEmpty(userPassword)) {
            log.info(userAccount + "注册时验证码有误");
            return new BaseResponse(400, "密码不能为空");
        }
        if (userPassword.length() < 6 || userPassword.length() > 20) {
            return new BaseResponse(400, "密码长度应为6-20位");
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("userAccount", userAccount);
        User user = userMapper.selectOne(queryWrapper);
        if (user != null) {
            return new BaseResponse(400, "改邮箱已被注册");
        }

        String md5Password = SecureUtil.md5().digestHex((SALT + userPassword).getBytes());
        user = new User();
        BeanUtils.copyProperties(userAddDTO, user);

        String uuid = IdUtil.simpleUUID();

        user.setUuid(uuid);
        user.setUserPassword(md5Password);

        int insertResult = userMapper.insert(user);
        if(insertResult == 0) {
            throw new StatusFailException("插入用户失败");
        }

        UserRole userRole = new UserRole();

        userRole.setUid(uuid);
        userRole.setRoleid(2);

        insertResult = userRoleMapper.insert(userRole);
        if(insertResult == 0) {
            throw new StatusFailException("插入用户失败");
        }
        return ResultUtils.success();
    }

    /**
     * 用户登录
     *
     * @param userLoginDTO
     * @return
     */
    @Override
    public BaseResponse<UserLoginVO> login(UserLoginDTO userLoginDTO, HttpServletRequest request, HttpServletResponse response) {
        String userAccount = userLoginDTO.getUserAccount();
        String userPassword = userLoginDTO.getUserPassword();

        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return ResultUtils.error(400, "账户密码不能为空");
        }

        String key = AccountEnum.TRY_LOGIN_NUM + userAccount;
        Integer tryLoginCount = (Integer) redisUtil.get(key);
        if(tryLoginCount != null && tryLoginCount >= 10) {
            return new BaseResponse(400,"登录失败次数过多！您的账号有风险，半个小时内暂时无法登录！");
        }

        String md5Password = SecureUtil.md5().digestHex((SALT + userPassword).getBytes());

        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("userAccount", userAccount).eq("userPassword", md5Password);
        User user = userMapper.selectOne(queryWrapper);

        if(user!=null) {
            if (user.getStatus() != 0) {
                return new BaseResponse(400,"账号已被封禁，请联系管理员处理");
            }
            UserLoginVO userLoginVO = new UserLoginVO();
            BeanUtils.copyProperties(user,userLoginVO);
            // 登陆成功清楚异常记录
            if (tryLoginCount != null) {
                redisUtil.del(key);
            }

            String JWT = jwtUtil.generateJwt(userAccount);
            userLoginVO.setJwt(JWT);

            redisUtil.set(JWT,0,30*60);

            request.getSession().setAttribute("user_login", user);

            return ResultUtils.success(userLoginVO);
        } else {
            if(tryLoginCount == null) {
                redisUtil.set(key, 1,30*60);
            } else {
                redisUtil.set(key,tryLoginCount+1,30*60);
            }
            return ResultUtils.error(400,"账号与密码不匹配！");
        }

//
//        // 会话记录
//        sessionEntityService.save(new Session()
//                .setUid(userRolesVo.getUid())
//                .setIp(IpUtils.getUserIpAddr(request))
//                .setUserAgent(request.getHeader("User-Agent")));
//
//        // 登录成功，清除锁定限制
//        if (tryLoginCount != null) {
//            redisUtils.del(key);
//        }
//
//        // 异步检查是否异地登录
//        sessionEntityService.checkRemoteLogin(userRolesVo.getUid());
//
//        UserInfoVO userInfoVo = new UserInfoVO();
//        BeanUtil.copyProperties(userRolesVo, userInfoVo, "roles");
//        userInfoVo.setRoleList(userRolesVo.getRoles()
//                .stream()
//                .map(Role::getRole)
//                .collect(Collectors.toList()));
//        return userInfoVo;
    }

    /**
     * 修改个人信息
     *
     * @param userProfileDTO
     * @return
     */
    @Override
    public BaseResponse<Void> updateProfile(UserProfileDTO userProfileDTO) {
        String userAccount = userProfileDTO.getUserAccount();

        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("userAccount",userAccount);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            return ResultUtils.error(400,"修改失败，没有该用户");
        }

        UpdateWrapper<User> updateWrapper = new UpdateWrapper<User>();
        updateWrapper.eq("userAccount",userAccount);

        User newUser = new User();
        BeanUtils.copyProperties(userProfileDTO,newUser);
        newUser.setUpdateTime(DateTime.now());
        int row = userMapper.update(newUser,updateWrapper);
        if(row != 1) {
            log.info("{}修改个人信息失败",userAccount);
            return ResultUtils.error(400,"修改失败");
        }



        return ResultUtils.success();
    }

    /**
     * 用户退出
     *
     * @param userLogoutDTO
     * @return
     */
    @Override
    public BaseResponse<Void> logout(UserLogoutDTO userLogoutDTO) {
        log.debug("UserServiceImpl---->logout");
        String JWT = userLogoutDTO.getCode();

        if(JWT==null) {
            log.info("Jwt为空");
            return ResultUtils.error("jwt不能为空");
        }

        if(!jwtUtil.hasToken(JWT)) {
            log.info("不存在改jwt");
            return ResultUtils.error("不存在改jwt");
        }

        jwtUtil.cleanToken(JWT);
        return ResultUtils.success();
    }

    /**
     * 获取注销验证码
     * @param userLogoutDTO
     * @return
     */
    @Override
    public BaseResponse<Void> getLogoutCode(UserLogoutDTO userLogoutDTO) {
        String userAccount = userLogoutDTO.getUserAccount();
        boolean isEmail = Validator.isEmail(userAccount);
        if (!isEmail) {
            return new BaseResponse(400, "您的邮箱格式不正确！");
        }


        String lockKey = EmailEnum.REGISTER_EMAIL_LOCK.getValue() + userAccount;
        if(redisUtil.hasKey(lockKey)) {
            return new BaseResponse(400,"对不起，您的操作频率过快，请在" + redisUtil.getExpire(lockKey) + "秒后再次发送注册邮件！");
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("userAccount",userAccount);

        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            return new BaseResponse(400,"改邮箱未被注册");
        }

        String numbers = RandomUtil.randomNumbers(6);

        redisUtil.set(EmailEnum.LOGOUT_KEY_PREFIX.getValue() + userAccount, numbers, 10 * 60);//默认验证码有效10分钟
        redisUtil.set(lockKey, 0, 60);

        try {
            EmailUtil.send(userAccount,numbers,"正在修改注销账号，若非本人操作，请立即修改密码");
            return ResultUtils.success();
        }catch (Exception e){
            log.info("{}注销账户时，发送验证码失败。{}",userAccount,e.getMessage());
            return ResultUtils.error("发送验证码失败");
        }
    }

    /**
     * 用户注销
     *
     * @param userLogoutDTO
     * @return
     */

    @Override
    public BaseResponse<Void> logoutForever(UserLogoutDTO userLogoutDTO) {
        String userAccount = userLogoutDTO.getUserAccount();
        String code = userLogoutDTO.getCode();

        Integer tryLogoutNum = (Integer) redisUtil.get(AccountEnum.TRY_LOGIN_NUM.getCode() + userAccount);

        if(tryLogoutNum != null && tryLogoutNum > 3) {
            log.info("{}多次注销，异常行为被拦截",userAccount);
            return ResultUtils.error("用户异常，请30分钟后再试！");
        }

        String redisKey = (String) redisUtil.get(EmailEnum.LOGOUT_KEY_PREFIX.getValue() + userAccount);
        if(!redisKey.equals(code)) {
            if (tryLogoutNum != null) {
                redisUtil.set(AccountEnum.TRY_LOGIN_NUM + userAccount, tryLogoutNum + 1, 30 * 60);
            } else {
                redisUtil.set(AccountEnum.TRY_LOGIN_NUM + userAccount, 1, 30 * 60);
            }
            return ResultUtils.error("验证码错误");
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("userAccount",userAccount);
        int delete = userMapper.delete(queryWrapper);
        if(delete!=1){
            log.info("{}注销账号失败");
            return ResultUtils.error("注销失败");
        }
        return ResultUtils.success();
    }

    @Override
    public BaseResponse<List<String>> getUserRole(String userAccount) {
        if(userAccount.isBlank()) {
            log.info("获取角色时参数为空");
            return ResultUtils.error("参数为空");
        }

        return ResultUtils.success(roleMapper.SelectRoleByUserAccount(userAccount));
    }

    @Override
    public BaseResponse<UserLoginVO> getLoginUser(HttpServletRequest request) throws StatusFailException {

        Object userObj = request.getSession().getAttribute("user_login");

        User currentUser = (User) userObj;

        if (currentUser == null || currentUser.getUserAccount() == null) {
            throw new StatusFailException("未登录");
        }
        // 从数据库查询（追求性能的话可以注释，直接走缓存）
        String userId = currentUser.getUuid();

        currentUser = this.getById(userId);
        if (currentUser == null) {
            throw new StatusFailException("未登录");
        }
        UserLoginVO userLoginVO = new UserLoginVO();

        BeanUtils.copyProperties(currentUser,userLoginVO);
        return ResultUtils.success(userLoginVO);

    }

    /**
     *
     * @param JWT
     * @return true 过期
     */
    @Override
    public BaseResponse<Boolean> checkJWT(String JWT) {
        log.debug("UserServiceImpl---->checkJWT()");
        return ResultUtils.success(!redisUtil.hasKey(JWT));
    }

}

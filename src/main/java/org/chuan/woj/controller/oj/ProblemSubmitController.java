package org.chuan.woj.controller.oj;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.chuan.woj.common.BaseResponse;
import org.chuan.woj.exception.StatusFailException;
import org.chuan.woj.exception.StatusSystemErrorException;
import org.chuan.woj.pojo.dto.problemSubmit.ProblemSubmitAddDTO;
import org.chuan.woj.pojo.entity.User;
import org.chuan.woj.pojo.vo.problemSubmit.ProblemSubmitVO;
import org.chuan.woj.pojo.vo.user.UserLoginVO;
import org.chuan.woj.service.problem.ProblemService;
import org.chuan.woj.service.problemSubmit.ProblemSubmitService;
import org.chuan.woj.service.user.UserService;
import org.chuan.woj.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * 代码提交接口
 *
 * @Author: chuan-wxy
 * @Date: 2024/9/11 19:59
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/question_submit")
public class ProblemSubmitController {

    @Autowired
    UserService userService;

    @Autowired
    ProblemSubmitService problemSubmitService;

    @PostMapping("/")
    public BaseResponse<ProblemSubmitVO> doSubmit(@RequestBody ProblemSubmitAddDTO problemSubmitAddDTO,
                                                             HttpServletRequest request) throws StatusFailException, StatusSystemErrorException, IOException, InterruptedException {
        final UserLoginVO loginUser = userService.getLoginUser(request).getData();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", loginUser.getUserAccount());
        User user = userService.getOne(queryWrapper);

        ProblemSubmitVO problemSubmitVO = problemSubmitService.doQuestionSubmit(problemSubmitAddDTO, user);

        return ResultUtils.success(problemSubmitVO);
    }
}

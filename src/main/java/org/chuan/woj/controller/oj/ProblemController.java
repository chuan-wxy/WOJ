package org.chuan.woj.controller.oj;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.extern.slf4j.Slf4j;
import org.chuan.woj.common.BaseResponse;
import org.chuan.woj.exception.StatusFailException;
import org.chuan.woj.exception.StatusSystemErrorException;
import org.chuan.woj.pojo.dto.problem.ProblemAddDTO;
import org.chuan.woj.pojo.entity.Problem;
import org.chuan.woj.pojo.entity.Tag;
import org.chuan.woj.service.problem.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: chuan-wxy
 * @Date: 2024/8/26 15:03
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/problem")
public class ProblemController {
    @Autowired
    private ProblemService problemService;

    /**
     * 新增题目
     * @param ProblemAddDTO
     * @return
     */
    @PostMapping("/add-problem")
    public BaseResponse<String> addProblem(@RequestBody ProblemAddDTO ProblemAddDTO, @RequestBody List<Tag> tagList) throws StatusFailException, StatusSystemErrorException {
        return problemService.addProblem(ProblemAddDTO,tagList);
    }

}

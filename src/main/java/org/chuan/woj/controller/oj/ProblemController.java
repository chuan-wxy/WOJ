package org.chuan.woj.controller.oj;

import lombok.extern.slf4j.Slf4j;
import org.chuan.woj.annotation.AuthCheck;
import org.chuan.woj.common.BaseResponse;
import org.chuan.woj.constant.UserConstant;
import org.chuan.woj.exception.StatusFailException;
import org.chuan.woj.exception.StatusSystemErrorException;
import org.chuan.woj.manager.ProblemManager;
import org.chuan.woj.pojo.dto.problem.ProblemAddDTO;
import org.chuan.woj.pojo.dto.problem.TagAddDTO;
import org.chuan.woj.service.problem.ProblemService;
import org.chuan.woj.service.problem.ProblemTagService;
import org.chuan.woj.service.problem.TagService;
import org.chuan.woj.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 题目接口（包括题目标题等相关属性）
 *
 * @Author: chuan-wxy
 * @Date: 2024/8/26 15:03
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/problem")
@io.swagger.v3.oas.annotations.tags.Tag(name = "ProblemController")
public class ProblemController {
    @Autowired
    private ProblemService problemService;

    @Autowired
    private ProblemTagService problemTagService;

    @Autowired
    private TagService TagService;

    @Autowired
    private ProblemManager problemManager;

    /**
     * 新增题目标签
     * @param tagAddDTO
     * @return
     */
    @PostMapping("/add-tag")
    @AuthCheck(mustRole = UserConstant.AMDIN)
    public BaseResponse<String> addTag(@RequestBody TagAddDTO tagAddDTO) throws StatusFailException, StatusSystemErrorException {
        return problemService.addTag(tagAddDTO);
    }

    /**
     * 新增题目
     * @param ProblemAddDTO
     * @return
     */
    @PostMapping("/add-problem")
    @AuthCheck(mustRole = UserConstant.AMDIN)
    public BaseResponse<String> addProblem(@RequestBody ProblemAddDTO ProblemAddDTO, @RequestBody List<org.chuan.woj.pojo.entity.Tag> tagList) throws StatusFailException, StatusSystemErrorException {
        return problemService.addProblem(ProblemAddDTO,tagList);
    }

}

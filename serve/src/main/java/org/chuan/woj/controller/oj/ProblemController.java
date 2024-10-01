package org.chuan.woj.controller.oj;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.chuan.woj.annotation.AuthCheck;
import org.chuan.woj.common.BaseResponse;
import org.chuan.woj.constant.UserConstant;
import org.chuan.woj.exception.StatusFailException;
import org.chuan.woj.exception.StatusSystemErrorException;
import org.chuan.woj.manager.ProblemManager;
import org.chuan.woj.pojo.dto.problem.ProblemAddDTO;
import org.chuan.woj.pojo.dto.problem.ProblemUpdateDTO;
import org.chuan.woj.pojo.dto.problem.TagAddDTO;
import org.chuan.woj.pojo.vo.problem.ProblemTitleVO;
import org.chuan.woj.pojo.vo.problem.ProblemVO;
import org.chuan.woj.pojo.vo.problem.TagVO;
import org.chuan.woj.service.problem.ProblemService;
import org.chuan.woj.service.problem.ProblemTagService;
import org.chuan.woj.service.problem.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * @param tagName
     * @return
     */
    @PostMapping("/add-tag")
    @AuthCheck(mustRole = UserConstant.AMDIN)
    public BaseResponse<String> addTag(String tagName) throws StatusFailException, StatusSystemErrorException {
        return problemService.addTag(tagName);
    }

    /**
     * 新增题目
     * @param problemAddDTO
     * @return
     */
    @PostMapping("/add-problem")
    @AuthCheck(mustRole = UserConstant.AMDIN)
    public BaseResponse<String> addProblem(@RequestBody ProblemAddDTO problemAddDTO) throws StatusFailException, StatusSystemErrorException {
        return problemService.addProblem(problemAddDTO);
    }

    @GetMapping("/get-problemtitle")
    @AuthCheck(mustRole = UserConstant.DEFAULT_USER)
    public BaseResponse<Page<ProblemTitleVO>> getProblemTitle(@RequestParam(value = "size", required = false) Integer size,
                                                              @RequestParam(value = "current", required = false) Integer current) throws StatusFailException {
        return problemService.getProblemTitle(current,size);
    }

    /**
     * 获取题目详细信息
     *
     * @param id
     * @return
     * @throws StatusFailException
     */
    @GetMapping("/get-problem")
    public BaseResponse<ProblemVO> getProblem(Long id) throws StatusFailException {
        return problemService.getProblem(id);
    }

    /**
     * 用于全局搜索框
     *
     * @param current
     * @param size
     * @param text
     * @return
     * @throws StatusFailException
     */
    @GetMapping("/search-problemtitle")
    @AuthCheck(mustRole = UserConstant.DEFAULT_USER)
    public BaseResponse<Page<ProblemTitleVO>> searchProblemTitleOne(
                                                              @RequestParam(value = "current") Integer current,
                                                              @RequestParam(value = "size") Integer size,
                                                              @RequestParam(value = "text") String text) throws StatusFailException {
        return problemService.searchProblemTitle(current, size,text);
    }

    /**
     * 用于列表页面
     *
     * @param current
     * @param size
     * @param id
     * @param tags
     * @param difficulty
     * @param title
     * @return
     * @throws StatusFailException
     */
    @GetMapping("/search-problemtitlelist")
    public BaseResponse<IPage<ProblemTitleVO>> searchProblemTitleTwo(
            @RequestParam(value = "current") Integer current,
            @RequestParam(value = "size") Integer size,
            @RequestParam(value = "id",required = false) Long id,
            @RequestParam(value = "tags",required = false) String tags,
            @RequestParam(value = "difficulty",required = false) String difficulty,
            @RequestParam(value = "title",required = false) String title) throws StatusFailException {

        return problemService.searchProblemTitleTwo(current, size,id,tags,difficulty,title);
    }

    /**
     * 获取所有题目标签
     *
     * @return
     */
    @GetMapping("/get-problemtaglist")
    public BaseResponse<List<TagVO>> getProblemTagList() {
        return TagService.getProblemTagList();
    }

    @PostMapping("/update-problem")
    @AuthCheck(mustRole = UserConstant.AMDIN)
    public BaseResponse<String> updateProblem(@RequestBody ProblemUpdateDTO problemUpdateDTO) throws StatusFailException, StatusSystemErrorException {
        return problemService.updateProblem(problemUpdateDTO);
    }
}

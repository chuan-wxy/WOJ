package org.chuan.woj.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.chuan.woj.exception.StatusFailException;
import org.chuan.woj.mapper.ProblemMapper;
import org.chuan.woj.mapper.ProblemTagMapper;
import org.chuan.woj.mapper.TagMapper;
import org.chuan.woj.pojo.dto.problem.ProblemAddDTO;
import org.chuan.woj.pojo.dto.problem.TagAddDTO;
import org.chuan.woj.pojo.entity.Problem;
import org.chuan.woj.pojo.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.metrics.StartupStep;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @Author: chuan-wxy
 * @Date: 2024/8/27 11:53
 * @Description:
 */
@Slf4j
@Component
public class ProblemManager {
    @Autowired
    ProblemTagMapper problemMapper;

    @Autowired
    TagMapper tagMapper;

    public void validateSubmitInfo(ProblemAddDTO problemAddDTO) throws StatusFailException {


        if(problemAddDTO == null) {
            log.debug("ProblemServiceImpl---->addProblem()->ProblemManager.validateSubmitInfo---problemAddDTO为空");
            throw new StatusFailException("problemAddDTO为空");
        }
        String problemId = problemAddDTO.getProblemId();
        String title = problemAddDTO.getTitle();
        if(problemId.isBlank()) {
            throw new StatusFailException("problemId为空");
        }
        if(title.isBlank()) {
            throw new StatusFailException("title为空");
        }
        this.validateTagList(problemAddDTO.getTagList());
    }
    public void validateTagList(List<String> tagList) throws StatusFailException {
        if(tagList.isEmpty()) {
            log.debug("ProblemServiceImpl---->addProblem()->ProblemManager.validateTagList---tagList为空");
            throw new StatusFailException("tagList为空");
        }
        for(String tag: tagList) {
            if(tag.isBlank()) {
                throw new StatusFailException("tag名为空");
            }
            QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name",tag);
            Tag isTag = tagMapper.selectOne(queryWrapper);
            if(isTag == null) {
                throw new StatusFailException("不存在改标签");
            }
        }
    }

    public void validateTag(TagAddDTO tagAddDTO) throws StatusFailException {
        if(tagAddDTO == null) {
            log.debug("ProblemServiceImpl---->addProblem()->ProblemManager.validateTag---tag为空");
            throw new StatusFailException("tag为空");
        }
        String name = tagAddDTO.getName();
        if(name.isBlank()) {
            throw new StatusFailException("tag名为空");
        }
    }
//
//    public void getProblemTitleDTO(Integer limit, Integer currentPage) {
//        IPage<Problem> page = new Page<>(currentPage, limit);
//        problemMapper.
//    }
}


















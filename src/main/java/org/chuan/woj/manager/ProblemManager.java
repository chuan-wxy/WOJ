package org.chuan.woj.manager;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.chuan.woj.exception.StatusFailException;
import org.chuan.woj.mapper.ProblemMapper;
import org.chuan.woj.mapper.ProblemTagMapper;
import org.chuan.woj.mapper.TagMapper;
import org.chuan.woj.pojo.dto.problem.ProblemAddDTO;
import org.chuan.woj.pojo.dto.problem.TagAddDTO;
import org.chuan.woj.pojo.entity.Problem;
import org.chuan.woj.pojo.entity.Tag;
import org.chuan.woj.pojo.vo.problem.ProblemTitleVO;
import org.chuan.woj.utils.SqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.metrics.StartupStep;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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


        if (problemAddDTO == null) {
            log.debug("ProblemServiceImpl---->addProblem()->ProblemManager.validateSubmitInfo---problemAddDTO为空");
            throw new StatusFailException("problemAddDTO为空");
        }
        String problemId = problemAddDTO.getProblemId();
        String title = problemAddDTO.getTitle();
        if (problemId.isBlank()) {
            throw new StatusFailException("problemId为空");
        }
        if (title.isBlank()) {
            throw new StatusFailException("title为空");
        }
        this.validateTagList(problemAddDTO.getTagList());
    }

    public void validateTagList(List<String> tagList) throws StatusFailException {
        if (tagList.isEmpty()) {
            log.debug("ProblemServiceImpl---->addProblem()->ProblemManager.validateTagList---tagList为空");
            throw new StatusFailException("tagList为空");
        }
        for (String tag : tagList) {
            if (tag.isBlank()) {
                throw new StatusFailException("tag名为空");
            }
            QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name", tag);
            Tag isTag = tagMapper.selectOne(queryWrapper);
            if (isTag == null) {
                throw new StatusFailException(201, "不存在改标签");
            }
        }
    }

    public void validateTag(String tagName) throws StatusFailException {
        if (tagName == null) {
            log.debug("ProblemServiceImpl---->addProblem()->ProblemManager.validateTag---tag为空");
            throw new StatusFailException("tag为空");
        }
        if (tagName.isBlank()) {
            throw new StatusFailException("tag名为空");
        }
    }

    public QueryWrapper<Problem> getQueryWrapper(List<String> keyword, List<String> tags, List<String> difficulty) {
        QueryWrapper<Problem> queryWrapper = new QueryWrapper<>();
        for (String k : keyword) {
            queryWrapper.like("title", keyword);
            queryWrapper.like("description", keyword);
        }
        for (String t : tags) {
            queryWrapper.like("tagList", t);
        }
        for (String d : difficulty) {
            queryWrapper.like("difficulty", d);
        }

        // todo queryWrapper.eq( "isDelete", false);
        return queryWrapper;
    }

    public QueryWrapper<Problem> getQueryWrapperTwo(Long id, String tags, String difficulty, String title) {
        QueryWrapper<Problem> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq(id!=null,"id", id);
        queryWrapper.like(title!=null,"title", title);
        queryWrapper.like(tags!=null ,"tagList", tags);
        queryWrapper.like(difficulty!=null ,"difficulty", difficulty);

        // todo queryWrapper.eq( "isDelete", false);

        return queryWrapper;
    }

    public Page<ProblemTitleVO> getProblemTitleVOPage(IPage<Problem> problemPage) {
        List<Problem> questionList = problemPage.getRecords();
        Page<ProblemTitleVO> problemTitleVOPage = new Page<>(problemPage.getCurrent(), problemPage.getSize(), problemPage.getTotal());

        if (CollUtil.isEmpty(questionList)) {
            return problemTitleVOPage;
        }

        //填充信息
        List<ProblemTitleVO> questionTitleVOList = questionList.stream().map(question -> {
            ProblemTitleVO problemTitleVO = ProblemTitleVO.objToVo(question);
            return problemTitleVO;
        }).collect(Collectors.toList());

        problemTitleVOPage.setRecords(questionTitleVOList);

        return problemTitleVOPage;
    }
}


















package org.chuan.woj.service.problem.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.chuan.woj.common.BaseResponse;
import org.chuan.woj.exception.StatusFailException;
import org.chuan.woj.exception.StatusSystemErrorException;
import org.chuan.woj.manager.ProblemManager;
import org.chuan.woj.mapper.ProblemMapper;
import org.chuan.woj.mapper.ProblemTagMapper;
import org.chuan.woj.pojo.dto.problem.ProblemAddDTO;
import org.chuan.woj.pojo.dto.problem.TagAddDTO;
import org.chuan.woj.pojo.entity.Problem;
import org.chuan.woj.pojo.entity.ProblemTag;
import org.chuan.woj.pojo.entity.Tag;
import org.chuan.woj.pojo.vo.problem.ProblemTitleVO;
import org.chuan.woj.service.problem.ProblemService;
import org.chuan.woj.utils.ResultUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.events.Event;

import java.util.List;

/**
* @author chuan-wxy
* @description 针对表【problem】的数据库操作Service实现
* @createDate 2024-08-26 16:25:01
*/
@Service
@Slf4j
public class ProblemServiceImpl extends ServiceImpl<ProblemMapper, Problem>
    implements ProblemService{
    @Autowired
    private ProblemMapper problemMapper;

    @Autowired
    private org.chuan.woj.mapper.TagMapper tagMapper;

    @Autowired
    private ProblemTagMapper problemTagMapper;

    @Autowired
    ProblemManager problemManager;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse<String> addProblem(ProblemAddDTO problemAddDTO) throws StatusFailException, StatusSystemErrorException {

        problemManager.validateSubmitInfo(problemAddDTO);

        String author = problemAddDTO.getAuthor();

        Problem problem = new Problem();
        BeanUtils.copyProperties(problemAddDTO,problem);

        int row = problemMapper.insert(problem);
        if(row != 1) {
            return ResultUtils.error("插入失败");
        }

        QueryWrapper<Problem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("problemId",problemAddDTO.getProblemId());
        problem = problemMapper.selectOne(queryWrapper);
        long problemId = problem.getId();

        log.info("ProblemServiceImpl---->addProblem()---插入Problem失败");
        for(String tag : problemAddDTO.getTagList()) {
            ProblemTag problemTag = new ProblemTag();

            Long tid = tagMapper.seleceIdByName(tag);

            problemTag.setPid(problemId);
            problemTag.setTid(tid);

            row = problemTagMapper.insert(problemTag);
            if(row != 1) {
                log.info("ProblemServiceImpl---->addProblem()---插入ProblemTag失败");
                throw new StatusSystemErrorException("插入数据失败");
            }
        }
        return ResultUtils.success("插入成功");
    }

    @Override
    public BaseResponse<String> addTag(TagAddDTO tagAddDTO) throws StatusFailException {
        problemManager.validateTag(tagAddDTO);

        Tag tag = new Tag();
        BeanUtils.copyProperties(tagAddDTO, tag);
        int row = tagMapper.insert(tag);
        if(row != 1) {
            log.info("ProblemServiceImpl---->addTag---插入tag失败");
            throw new StatusFailException("添加失败");
        }
        return ResultUtils.success("添加成功");
    }

    @Override
    public BaseResponse<Page<ProblemTitleVO>> getProblemTitle(Integer limit, Integer currentPage, String keyword, Integer tagId, Integer difficulty) {
        if(keyword == null && tagId == null && difficulty == null) {
            // 题库页普通查询
             problemManager.getProblemTitleDTO(limit, currentPage);
            IPage<Problem> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(currentPage, limit);
        } else {
            // 条件查询
            return null;
        }
        return null;
    }
}





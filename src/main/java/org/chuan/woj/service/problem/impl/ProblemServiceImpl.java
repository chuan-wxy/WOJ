package org.chuan.woj.service.problem.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.chuan.woj.common.BaseResponse;
import org.chuan.woj.exception.StatusFailException;
import org.chuan.woj.manager.ProblemManager;
import org.chuan.woj.mapper.ProblemMapper;
import org.chuan.woj.pojo.dto.problem.ProblemAddDTO;
import org.chuan.woj.pojo.entity.Problem;
import org.chuan.woj.pojo.entity.Tag;
import org.chuan.woj.service.problem.ProblemService;
import org.chuan.woj.utils.ResultUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author lenovo
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
    ProblemManager problemManager;

    @Override
    @Annotation()
    public BaseResponse<String> addProblem(ProblemAddDTO problemAddDTO, List<Tag> tagList) throws StatusFailException {

        problemManager.validateSubmitInfo(problemAddDTO);
        problemManager.validateTagList(tagList);

        String author = problemAddDTO.getAuthor();

        Problem problem = new Problem();
        BeanUtils.copyProperties(problemAddDTO,problem);


        int row = problemMapper.insert(problem);
        if(row==1) {
            return ResultUtils.success("插入成功");
        }
        log.info("ProblemServiceImpl---->addProblem()---插入problem失败");
        return ResultUtils.error("插入失败");
    }
}





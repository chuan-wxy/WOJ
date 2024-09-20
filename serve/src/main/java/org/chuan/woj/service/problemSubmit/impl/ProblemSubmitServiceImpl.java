package org.chuan.woj.service.problemSubmit.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.chuan.woj.common.enums.ProblemSubmitLanguageEnum;
import org.chuan.woj.exception.StatusFailException;
import org.chuan.woj.exception.StatusSystemErrorException;
import org.chuan.woj.pojo.dto.problemSubmit.ProblemSubmitAddDTO;
import org.chuan.woj.pojo.entity.Problem;
import org.chuan.woj.pojo.entity.ProblemSubmit;
import org.chuan.woj.pojo.entity.User;
import org.chuan.woj.pojo.vo.problemSubmit.ProblemSubmitVO;
import org.chuan.woj.service.problem.ProblemService;
import org.chuan.woj.service.problemSubmit.JudgeService;
import org.chuan.woj.service.problemSubmit.ProblemSubmitService;
import org.chuan.woj.mapper.ProblemSubmitMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
* @author lenovo
* @description 针对表【problem_submit】的数据库操作Service实现
* @createDate 2024-09-12 12:39:38
*/
@Service
@Slf4j
public class ProblemSubmitServiceImpl extends ServiceImpl<ProblemSubmitMapper, ProblemSubmit>
    implements ProblemSubmitService{

    @Autowired
    ProblemService problemService;

    @Autowired
    @Lazy
    JudgeService judgeService;

    @Override
    public ProblemSubmitVO doQuestionSubmit(ProblemSubmitAddDTO problemSubmitAddDTO, User user) throws StatusFailException, StatusSystemErrorException, IOException, InterruptedException {
        if (problemSubmitAddDTO == null || problemSubmitAddDTO.getPid() <= 0) {
            throw new StatusFailException("请求体为空或题目id为空");
        }

        String language = problemSubmitAddDTO.getLanguage();
        long questionId = problemSubmitAddDTO.getPid();

        ProblemSubmitLanguageEnum languageEnum = ProblemSubmitLanguageEnum.getEnumByValue(language);
        if(languageEnum == null){
            throw new StatusFailException("编程语言错误");
        }

        // 判断题目是否存在，根据类别获取实体
        Problem problem = problemService.getById(questionId);
        if (problem == null) {
            throw new StatusFailException("题目不存在");
        }

        ProblemSubmit problemSubmit = new ProblemSubmit();
        BeanUtils.copyProperties(problemSubmitAddDTO,problemSubmit);

        problemSubmit.setUid(user.getUuid().trim());

        boolean save = this.save(problemSubmit);

        long questionSubmitId = problemSubmit.getId();

        if(!save) {
            log.debug("ProblemSubmitServiceImpl----doQuestionSubmit--->保存problemSubmit失败");
            throw new StatusSystemErrorException("保存失败");
        }

        ProblemSubmitVO problemSubmitVO = judgeService.doJudge(questionSubmitId);
        problemSubmitVO.setId(questionSubmitId);
        problemSubmitVO.setLanguage(language);
        problemSubmitVO.setPid(questionId);

        return problemSubmitVO;
    }
}





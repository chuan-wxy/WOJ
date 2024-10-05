package org.chuan.woj.service.problemSubmit.impl;

import cn.hutool.json.JSONUtil;
import org.chuan.woj.codesandbox.CodeSandbox;
import org.chuan.woj.codesandbox.CodeSandboxFactory;
import org.chuan.woj.codesandbox.model.ExecuteCodeRequest;
import org.chuan.woj.common.enums.ProblemSubmitStateEnum;
import org.chuan.woj.exception.StatusFailException;
import org.chuan.woj.pojo.dto.problemSubmit.JudgeInfo;
import org.chuan.woj.pojo.entity.Problem;
import org.chuan.woj.pojo.entity.ProblemSubmit;
import org.chuan.woj.pojo.vo.problem.ProblemVO;
import org.chuan.woj.pojo.vo.problemSubmit.ProblemSubmitVO;
import org.chuan.woj.service.problem.ProblemService;
import org.chuan.woj.service.problemSubmit.JudgeService;
import org.chuan.woj.service.problemSubmit.ProblemSubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Author: chuan-wxy
 * @Date: 2024/9/12 13:57
 * @Description:
 */
@Service
public class JudgeServiceImpl implements JudgeService {

    @Autowired
    ProblemSubmitService problemSubmitService;

    @Autowired
    ProblemService problemService;

    @Override
    public ProblemSubmitVO doJudge(long problemSubmitId) throws StatusFailException, IOException, InterruptedException {
        ProblemSubmit problemSubmit = problemSubmitService.getById(problemSubmitId);
        if(problemSubmit == null){
            throw new StatusFailException("提交信息不存在");
        }
        Long questionId = problemSubmit.getPid();
        Problem problem = problemService.getById(questionId);
        // 更新状态
        ProblemSubmit problemSubmitUpdate = new ProblemSubmit();
        problemSubmitUpdate.setId(problemSubmitId);
        problemSubmitUpdate.setState(ProblemSubmitStateEnum.RUNING.getValue());
        boolean update = problemSubmitService.updateById(problemSubmitUpdate);
        if(!update){
            throw new StatusFailException("题目状态更新失败");
        }
        String language = problemSubmit.getLanguage();
        String code = problemSubmit.getCode();

        CodeSandbox codeSandbox = CodeSandboxFactory.newCodeSandbox(language);
//        codeSandbox = new CodeSandboxProxy(codeSandbox);
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .build();
        //debug
        System.out.println("沙箱开始");
        JudgeInfo judgeInfo  = codeSandbox.executeCode(executeCodeRequest,problem);
        //debug
        System.out.println("判题返回结果");
        System.out.println(judgeInfo);

        problemSubmitUpdate = new ProblemSubmit();
        problemSubmitUpdate.setId(problemSubmitId);
        problemSubmitUpdate.setState(ProblemSubmitStateEnum.SUCCEED.getValue());
        problemSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = problemSubmitService.updateById(problemSubmitUpdate);
        if(!update){
            throw new StatusFailException("题目状态更新失败");
        }
        ProblemSubmit problemSubmitResult = problemSubmitService.getById(problemSubmitId);
        ProblemSubmitVO problemSubmitVO = new ProblemSubmitVO();

        problemSubmitVO.setId(problemSubmitResult.getId());
        problemSubmitVO.setLanguage(problemSubmitResult.getLanguage());
        problemSubmitVO.setCode(problemSubmitResult.getCode());
        problemSubmitVO.setJudgeInfo(JSONUtil.toBean(problemSubmitResult.getJudgeInfo(),JudgeInfo.class));
        problemSubmitVO.setState(problemSubmitResult.getState());
        problemSubmitVO.setPid(problemSubmitResult.getPid());
        problemSubmitVO.setUid(problemSubmitResult.getUid());

        return problemSubmitVO;
    }
}

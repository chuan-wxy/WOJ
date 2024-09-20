package org.chuan.woj.service.problemSubmit;

import org.chuan.woj.exception.StatusFailException;
import org.chuan.woj.pojo.vo.problem.ProblemVO;
import org.chuan.woj.pojo.vo.problemSubmit.ProblemSubmitVO;

import java.io.IOException;

/**
 * @Author: chuan-wxy
 * @Date: 2024/9/12 13:57
 * @Description:
 */
public interface JudgeService {
    ProblemSubmitVO doJudge(long problemSubmitId) throws StatusFailException, IOException, InterruptedException;
}

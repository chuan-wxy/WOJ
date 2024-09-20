package org.chuan.woj.codesandbox;

import org.chuan.woj.codesandbox.model.ExecuteCodeRequest;
import org.chuan.woj.exception.StatusFailException;
import org.chuan.woj.pojo.dto.problemSubmit.JudgeInfo;
import org.chuan.woj.pojo.entity.Problem;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 代码沙箱接口定义
 */
@Component
public interface CodeSandbox {

    /**
     * 执行代码
     * @param executeCodeRequest
     * @return
     */
    JudgeInfo executeCode(ExecuteCodeRequest executeCodeRequest, Problem problem) throws IOException, InterruptedException, StatusFailException;
}

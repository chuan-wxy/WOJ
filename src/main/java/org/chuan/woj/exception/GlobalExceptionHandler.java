package org.chuan.woj.exception;

import lombok.extern.slf4j.Slf4j;
import org.chuan.woj.common.BaseResponse;
import org.chuan.woj.common.ResultStatus;
import org.chuan.woj.utils.ResultUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常响应器
 *
 * @Author: chuan-wxy
 * @Date: 2024/8/19 18:26
 * @Description:
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public BaseResponse<?> businessExceptionHandler(Exception e) {
        log.error("全局异常响应器---->Exception:{}", e);
        return ResultUtils.error(e.getMessage());
    }
}

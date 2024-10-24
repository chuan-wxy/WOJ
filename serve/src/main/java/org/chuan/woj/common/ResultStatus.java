package org.chuan.woj.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;
import org.chuan.woj.common.enums.ProblemSubmitLanguageEnum;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 状态码枚举类
 *
 * @author chuan-wxy
 * @date 2024/08/14 04:00:47
 */

public enum ResultStatus {

    SUCCESS(0,"成功"),

    FAIL(400,"失败"),

    ACCESS_DENIED(401,"访问受限"),

    FORBIDDEN(403,"拒绝访问"),

    NOT_FOUND(404,"数据不存在"),

    SYSTEM_ERROR(500,"系统错误");

    private int code;

    private String message;

    ResultStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

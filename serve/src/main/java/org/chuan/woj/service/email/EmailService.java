package org.chuan.woj.service.email;

import org.chuan.woj.common.BaseResponse;
import org.chuan.woj.pojo.vo.CaptchaVO;

/**
 * 描述
 *
 * @Author chuan-wxy
 * @Create 2024/8/15 13:55
 */
public interface EmailService {
    BaseResponse<Void> getCaptchaCode(String email,String content);
}

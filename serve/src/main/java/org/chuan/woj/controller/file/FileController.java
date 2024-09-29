package org.chuan.woj.controller.file;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletOutputStream;
import lombok.extern.slf4j.Slf4j;
import org.chuan.woj.annotation.AuthCheck;
import org.chuan.woj.common.BaseResponse;
import org.chuan.woj.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @Author: chuan-wxy
 * @Date: 2024/8/18 9:02
 * @Description:
 */

@RestController
@RequestMapping("/file")
@Slf4j
@Tag(name = "UserController")
public class FileController {

    @Value("${path.code.judgecase-path}")
    String judgeCasePath;

    @PostMapping("/upload-avatar")
    public String uploadAvatar() {
        return null;
    }

    @PostMapping("/upload-judgelist")
    @AuthCheck(mustRole = "default_user")
    public BaseResponse<String> uploadJudgeList(MultipartFile file, String pid) {
        String originFileName = file.getOriginalFilename();
        File dir = new File(judgeCasePath + "\\" + pid);

        boolean success = dir.mkdir();

        try {
            file.transferTo(new File(judgeCasePath + "\\" + pid + "\\" + originFileName));
        } catch (Exception e) {
            return ResultUtils.error("保存失败");
        }
        return ResultUtils.success("保存成功");
    }
}

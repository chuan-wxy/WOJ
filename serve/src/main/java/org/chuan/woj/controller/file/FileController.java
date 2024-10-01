package org.chuan.woj.controller.file;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletOutputStream;
import lombok.extern.slf4j.Slf4j;
import org.chuan.woj.annotation.AuthCheck;
import org.chuan.woj.common.BaseResponse;
import org.chuan.woj.exception.StatusSystemErrorException;
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
@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${path.code.judgecase-path}")
    String judgeCasePath;

    @Value("${path.avatar.course-avatar-path}")
    String courseAvatarPath;

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

    /**
     * 上传课程头像接口
     * @param file
     * @param courseName
     * @return
     * @throws StatusSystemErrorException
     */

    @PostMapping("/upload-course-avatar")
    public BaseResponse<String> uploadCourseAvatar(MultipartFile file, String courseName) throws StatusSystemErrorException {
        String originFileName = file.getOriginalFilename();
        String dirPath = courseAvatarPath + File.separator + courseName;

        File dir = new File(dirPath);
        if (!dir.exists()) {
            boolean success = dir.mkdirs();
            if (!success) {

                log.error("无法创建目录: {}", dirPath);
                throw new StatusSystemErrorException("目录创建失败");
            }
        }

        String filePath = dirPath + File.separator + originFileName;
        File courseAvatar = new File(filePath);
        try {
            file.transferTo(courseAvatar);
            log.info("课程图片已成功保存至: {}", filePath);
            return ResultUtils.success(filePath);
        } catch (IOException e) {
            log.error("文件保存失败: {}", e.getMessage());
            return ResultUtils.error("保存失败，错误信息: " + e.getMessage());
        }
    }
}

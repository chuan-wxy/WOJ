package org.chuan.woj.controller.oj;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.chuan.woj.annotation.AuthCheck;
import org.chuan.woj.common.BaseResponse;
import org.chuan.woj.constant.UserConstant;
import org.chuan.woj.exception.StatusFailException;
import org.chuan.woj.exception.StatusSystemErrorException;
import org.chuan.woj.pojo.dto.course.CourseAddDTO;
import org.chuan.woj.pojo.entity.Course;
import org.chuan.woj.service.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: chuan-wxy
 * @Date: 2024/9/25 17:03
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    /**
     * 新增课程
     * @param courseAddDTO
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.AMDIN)
    public BaseResponse<String> addCourse(@RequestBody CourseAddDTO courseAddDTO) throws StatusFailException {
        return courseService.addCourse(courseAddDTO);
    }

    /**
     * 新增课程时，查找其上级课程
     * @param level
     * @return
     * @throws StatusFailException
     */
    @GetMapping("/get-course-by-level")
    @AuthCheck(mustRole = UserConstant.AMDIN)
    public BaseResponse<List<Course>> getCourseByLevel(Integer level) {
        return courseService.getCourseByLevel(level);
    }

    @GetMapping("/get-course")
    public BaseResponse<List<Course>> getCourse() {
        return courseService.getCourse();
    }
}

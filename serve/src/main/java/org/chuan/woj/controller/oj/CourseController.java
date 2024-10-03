package org.chuan.woj.controller.oj;

import lombok.extern.slf4j.Slf4j;
import org.chuan.woj.annotation.AuthCheck;
import org.chuan.woj.common.BaseResponse;
import org.chuan.woj.constant.UserConstant;
import org.chuan.woj.exception.StatusFailException;
import org.chuan.woj.pojo.dto.course.CourseAddDTO;
import org.chuan.woj.pojo.entity.Course;
import org.chuan.woj.service.course.CourseService;
import org.chuan.woj.utils.CourseTreeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/get-courseList")
    @AuthCheck(mustRole = UserConstant.DEFAULT_USER)
    public BaseResponse<Map<Integer , CourseTreeBuilder.Category >> getCourseList() {
        return courseService.getCourseList();
    }

    @GetMapping("/get-first")
    @AuthCheck(mustRole = UserConstant.DEFAULT_USER)
    public BaseResponse<List<Course>> getFirst() {
        return courseService.getFirst();
    }

    @GetMapping("/get-second")
    @AuthCheck(mustRole = UserConstant.DEFAULT_USER)
    public BaseResponse<Map<Integer , org.chuan.woj.utils.CourseTreeBuilder.Category>> getSecond(@RequestParam Long id) {
        return courseService.getSecond(id);
    }

}

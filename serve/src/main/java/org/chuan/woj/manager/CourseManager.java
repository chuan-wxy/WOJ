package org.chuan.woj.manager;

import lombok.extern.slf4j.Slf4j;
import org.chuan.woj.exception.StatusFailException;
import org.chuan.woj.pojo.dto.course.CourseAddDTO;
import org.chuan.woj.pojo.dto.problem.ProblemAddDTO;
import org.springframework.stereotype.Component;

/**
 * @Author: chuan-wxy
 * @Date: 2024/9/25 17:33
 * @Description:
 */
@Slf4j
@Component
public class CourseManager {
    public void validateCourse(CourseAddDTO courseAddDTO) throws StatusFailException {
        if (courseAddDTO == null) {
            log.error("CourseManager---->validateCourse()---courseAddDTO为空");
            throw new StatusFailException("courseAddDTO为空");
        }
        String name = courseAddDTO.getName();
        String description = courseAddDTO.getDescription();
        Integer level = courseAddDTO.getLevel();
        Integer pid = courseAddDTO.getPid();
        if (name.isBlank()) {
            throw new StatusFailException("name为空");
        }
        if (description.isBlank()) {
            throw new StatusFailException("description为空");
        }
        if (level == null) {
            throw new StatusFailException("level为空");
        }
        if(level <= 0 || level >= 4) {
            throw new StatusFailException("参数level非法");
        }
        if(pid != null && pid < 0) {
            throw new StatusFailException("参数pid非法");
        }
    }
}

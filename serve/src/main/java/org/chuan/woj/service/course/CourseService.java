package org.chuan.woj.service.course;


import com.baomidou.mybatisplus.extension.service.IService;
import org.chuan.woj.common.BaseResponse;
import org.chuan.woj.exception.StatusFailException;
import org.chuan.woj.pojo.dto.course.CourseAddDTO;
import org.chuan.woj.pojo.entity.Course;
import org.chuan.woj.utils.CourseTreeBuilder;

import java.util.List;
import java.util.Map;

/**
* @author lenovo
* @description 针对表【course】的数据库操作Service
* @createDate 2024-09-25 16:42:58
*/
public interface CourseService extends IService<Course> {

    BaseResponse<String> addCourse(CourseAddDTO courseAddDTO) throws StatusFailException;

    BaseResponse<List<Course>> getCourseByLevel(Integer level);

    BaseResponse<Map<Integer , org.chuan.woj.utils.CourseTreeBuilder.Category>> getCourseList();

    BaseResponse<List<Course>> getFirst();

    BaseResponse<Map<Integer , org.chuan.woj.utils.CourseTreeBuilder.Category>> getSecond(Long id);
}

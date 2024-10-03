package org.chuan.woj.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.chuan.woj.pojo.entity.Course;

import java.util.List;

/**
* @author lenovo
* @description 针对表【course】的数据库操作Mapper
* @createDate 2024-09-25 16:42:58
* @Entity generator.domain.Course
*/
public interface CourseMapper extends BaseMapper<Course> {

    List<Course> selectCourseInFirst(Long pid);

}





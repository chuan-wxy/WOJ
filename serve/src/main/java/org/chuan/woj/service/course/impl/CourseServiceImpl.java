package org.chuan.woj.service.course.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.chuan.woj.common.BaseResponse;
import org.chuan.woj.exception.StatusFailException;
import org.chuan.woj.manager.CourseManager;
import org.chuan.woj.mapper.CourseMapper;
import org.chuan.woj.pojo.dto.course.CourseAddDTO;
import org.chuan.woj.pojo.entity.Course;
import org.chuan.woj.service.course.CourseService;
import org.chuan.woj.utils.ResultUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author lenovo
* @description 针对表【course】的数据库操作Service实现
* @createDate 2024-09-25 16:42:58
*/
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course>
    implements CourseService {
    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseManager courseManager;

    @Override
    public BaseResponse<String> addCourse(CourseAddDTO courseAddDTO) throws StatusFailException {

        courseManager.validateCourse(courseAddDTO);
        // 课程查重
        String name = courseAddDTO.getName();
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",name);
        Course course = this.getOne(queryWrapper);
        if(course != null) {
            throw new StatusFailException("课程名称重复");
        }
        // 检查父节点是否存在
        Integer pid = courseAddDTO.getPid();
        QueryWrapper<Course> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id",pid);
        Course course1 = this.getOne(queryWrapper1);
        if(course1 == null) {
            throw new StatusFailException("没有改父级课程");
        }

        Course newCourse = new Course();
        BeanUtils.copyProperties(courseAddDTO,newCourse);
        boolean save = this.save(newCourse);
        if(save) {
            return ResultUtils.success("添加成功");
        } else {
            return ResultUtils.error("添加失败");
        }
    }

    @Override
    public BaseResponse<List<Course>> getCourseByLevel(Integer level) {
        if(level == null) {
            return ResultUtils.error("level不能为空");
        }
        if(level <= 0) {
            return null;
        }
        QueryWrapper queryWrapper = new QueryWrapper<Course>();
        queryWrapper.eq("level",level-1);

        List courseList = courseMapper.selectList(queryWrapper);

        return ResultUtils.success(courseList);
    }

    @Override
    public BaseResponse<List<Course>> getCourse() {
        return null;
    }
}





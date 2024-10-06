package org.chuan.woj.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.chuan.woj.pojo.entity.Activity;
import org.chuan.woj.pojo.vo.activity.ActivityContentVO;
import org.chuan.woj.pojo.vo.activity.ActivityTitleVO;

import java.util.List;

/**
* @author lenovo
* @description 针对表【activity】的数据库操作Mapper
* @createDate 2024-10-02 19:37:38
* @Entity generator.domain.Activity
*/
public interface ActivityMapper extends BaseMapper<Activity> {

    List<ActivityTitleVO> selectActivityTitleList();

    ActivityContentVO selectActivityContentVO(Integer id);
}





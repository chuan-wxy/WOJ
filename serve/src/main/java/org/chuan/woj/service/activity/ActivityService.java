package org.chuan.woj.service.activity;


import com.baomidou.mybatisplus.extension.service.IService;
import org.chuan.woj.common.BaseResponse;
import org.chuan.woj.exception.StatusFailException;
import org.chuan.woj.pojo.dto.activity.ActivityAddDTO;
import org.chuan.woj.pojo.entity.Activity;
import org.chuan.woj.pojo.vo.activity.ActivityContentVO;
import org.chuan.woj.pojo.vo.activity.ActivityTitleVO;

import java.util.List;

/**
* @author lenovo
* @description 针对表【activity】的数据库操作Service
* @createDate 2024-10-02 19:37:38
*/
public interface ActivityService extends IService<Activity> {

    BaseResponse<String> addActivity(ActivityAddDTO activityAddDTO) throws StatusFailException;

    BaseResponse<List<ActivityTitleVO>> getActivityList();

    BaseResponse<ActivityContentVO> getActivity(Integer id);
}

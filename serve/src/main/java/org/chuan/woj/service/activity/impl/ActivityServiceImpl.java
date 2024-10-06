package org.chuan.woj.service.activity.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.catalina.Manager;
import org.chuan.woj.common.BaseResponse;
import org.chuan.woj.exception.StatusFailException;
import org.chuan.woj.manager.ActivityManager;
import org.chuan.woj.manager.CourseManager;
import org.chuan.woj.mapper.ActivityMapper;
import org.chuan.woj.pojo.dto.activity.ActivityAddDTO;
import org.chuan.woj.pojo.entity.Activity;
import org.chuan.woj.pojo.vo.activity.ActivityContentVO;
import org.chuan.woj.pojo.vo.activity.ActivityTitleVO;
import org.chuan.woj.service.activity.ActivityService;
import org.chuan.woj.utils.ResultUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author lenovo
* @description 针对表【activity】的数据库操作Service实现
* @createDate 2024-10-02 19:37:38
*/
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity>
    implements ActivityService {

    @Autowired
    ActivityMapper activityMapper;

    @Autowired
    private ActivityManager activityManager;
    @Override
    public BaseResponse<String> addActivity(ActivityAddDTO activityAddDTO) throws StatusFailException {
        activityManager.activityAddvalidate(activityAddDTO);

        Activity activity = new Activity();

        BeanUtils.copyProperties(activityAddDTO,activity);

        boolean save = this.save(activity);

        if(save) {
            return ResultUtils.success("保存成功");
        } else {
            log.error("ActivityServiceImpl---->addActivity---保存活动失败");
            return ResultUtils.error("保存失败");
        }
    }

    @Override
    public BaseResponse<List<ActivityTitleVO>> getActivityList() {
        return ResultUtils.success(activityMapper.selectActivityTitleList());
    }

    @Override
    public BaseResponse<ActivityContentVO> getActivity(Integer id) {
        return ResultUtils.success(activityMapper.selectActivityContentVO(id));
    }
}





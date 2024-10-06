package org.chuan.woj.controller.oj;

import lombok.extern.slf4j.Slf4j;
import org.chuan.woj.annotation.AuthCheck;
import org.chuan.woj.common.BaseResponse;
import org.chuan.woj.constant.UserConstant;
import org.chuan.woj.exception.StatusFailException;
import org.chuan.woj.pojo.dto.activity.ActivityAddDTO;
import org.chuan.woj.pojo.vo.activity.ActivityContentVO;
import org.chuan.woj.pojo.vo.activity.ActivityTitleVO;
import org.chuan.woj.service.activity.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: chuan-wxy
 * @Date: 2024/10/2 20:26
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/activity")
public class ActivityController {
    @Autowired
    ActivityService activityService;

    /**
     * 新增活动
     * @param activityAddDTO
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.AMDIN)
    public BaseResponse<String> addActivity(@RequestBody ActivityAddDTO activityAddDTO) throws StatusFailException {
        return activityService.addActivity(activityAddDTO);
    }

    /**
     * 获取活动标题列表
     * @return
     */
    @GetMapping("/get-activity-list")
    public BaseResponse<List<ActivityTitleVO>> getActivityList() {
        return activityService.getActivityList();
    }

    /**
     * 获取单个活动文章
     * @return
     */
    @GetMapping("/get-activity")
    public BaseResponse<ActivityContentVO> getActivity(@RequestParam Integer id) {
        return activityService.getActivity(id);
    }
}

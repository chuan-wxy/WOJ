package org.chuan.woj.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.chuan.woj.exception.StatusFailException;
import org.chuan.woj.mapper.ActivityMapper;
import org.chuan.woj.mapper.AnnouncementMapper;
import org.chuan.woj.pojo.dto.activity.ActivityAddDTO;
import org.chuan.woj.pojo.dto.announcement.AnnouncementAddDTO;
import org.chuan.woj.pojo.entity.Activity;
import org.chuan.woj.pojo.entity.Announcement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: chuan-wxy
 * @Date: 2024/10/2 20:46
 * @Description:
 */
@Slf4j
@Component
public class ActivityManager {

    @Autowired
    ActivityMapper activityMapper;

    @Autowired
    AnnouncementMapper announcementMapper;
    public void activityAddvalidate(ActivityAddDTO activityAddDTO) throws StatusFailException {
        String title = activityAddDTO.getTitle();

        if(title == null || title.isBlank()) {
            throw new StatusFailException("标题不能为空");
        }

        QueryWrapper<Activity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title",title);

        Activity activity = activityMapper.selectOne(queryWrapper);

        if(activity != null) {
            throw new StatusFailException("标题重复");
        }

    }

    public void announcementAddvalidate(AnnouncementAddDTO announcementAddDTO) throws StatusFailException {
        String title = announcementAddDTO.getTitle();

        if(title == null || title.isBlank()) {
            throw new StatusFailException("标题不能为空");
        }

        QueryWrapper<Announcement> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title",title);

        Announcement announcement = announcementMapper.selectOne(queryWrapper);

        if(announcement != null) {
            throw new StatusFailException("标题重复");
        }

    }
}
